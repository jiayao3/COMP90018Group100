package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Boss;
import com.mygdx.game.Game;
import com.mygdx.game.Laser;
import com.mygdx.game.Missile;
import com.mygdx.game.Meteor;
import com.mygdx.game.Minion;
import com.mygdx.game.Life;
import com.mygdx.game.GameUI;
import com.mygdx.game.Spaceship;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Game game;
    private Spaceship spaceship;
    private Meteor meteor;
    private Minion minion;
    private Life life;
    private ArrayList<Laser> lasers;
    private ArrayList<Missile> missiles;
    private ArrayList<Meteor> meteors;
    private ArrayList<Minion> minions;
    private int counter = 0;
    private Boss boss;
    private boolean gameOver;
    public boolean levelingUp;
    private BitmapFont endTitle;
    private String title;
    private GameUI UI;
    public static boolean isPaused = false;
    private int score = 0;
    public int level = 1;
    private float elapsedTime = 0;
    private static boolean shooting = false;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

        spaceship = new Spaceship();
        lasers = new ArrayList<>();
        missiles = new ArrayList<>();
        meteors = new ArrayList<>();
        minions = new ArrayList<>();
        life = new Life();
        boss = new Boss(level);
        levelingUp = false;
        gameOver = false;
        endTitle = new BitmapFont(Gdx.files.internal("titlefont.fnt"));
        title = "";
        UI = new GameUI(game);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        if (!isPaused) {
            if(!gameOver) {
                if(!levelingUp) {
                    scoreCount(delta);
                    if (shooting && spaceship != null) {
                        spaceship.shoot();
                    }

                    for (int i = meteors.size() - 1; i >= 0; i--) {
                        Meteor meteor = meteors.get(i);

                        if (!meteor.gone) {
                            meteor.Draw(game.batch);
                            meteor.hitShip(spaceship);
                        } else {
                            meteors.remove(i);
                        }
                    }

                    for (int i = minions.size() - 1; i >= 0; i--) {
                        Minion minion = minions.get(i);

                        if (!minion.gone) {
                            minion.Draw(game.batch);
                            minion.hitShip(spaceship);
                        } else {
                            minions.remove(i);
                        }
                    }
                    if (spaceship.HP > 0) {
                        lasers = spaceship.Draw(game.batch);
                    }

                    if (spaceship.HP <= 0) {
                        spaceship.sprite.setPosition(-1000, 1000);
                        loseScreen();
                    }

                    for (Laser laser : lasers) {
                        boss.detectHit(laser);
                        for (Meteor meteor : meteors) {
                            meteor.detectHit(laser);
                        }
                        for (Minion minion : minions) {
                            minion.detectHit(laser);
                        }
                    }

                    for (Missile missile : missiles) {
                        boss.detectHit(missile);
                        for (Meteor meteor : meteors) {
                            meteor.detectHit(missile);
                        }
                        for (Minion minion : minions) {
                            minion.detectHit(missile);
                        }
                    }

                    counter++;
                    if (counter % (90 - 15 * level) == 0) {
                        meteor = new Meteor(level);
                        meteors.add(meteor);
                        meteor.spawnMeteor();
                    }
                    if(level >= 3){
                        if (counter % 60 == 0) {
                            minion = new Minion();
                            minions.add(minion);
                            minion.spawnMinion();
                        }
                    }

                    if(level > 3){
                        if(life.gone){
                            double r = Math.random() * 2;
                            if(r <= 1.0){
                                life.spawnLife();
                            }
                        }
                        life.Draw(game.batch);
                        life.hitShip(spaceship);
                    }

                    if (boss.HP > 0) {
                        boss.Draw(game.batch);
                    }

                    if (boss.HP <= 0) {
                        boss.sprite.setPosition(1000, 1000);
                        levelUpScreen();
                    }
                    UI.render(spaceship);
                }
                else {
                    endTitle.draw(game.batch, title, Gdx.graphics.getWidth()/2 - 350, Gdx.graphics.getHeight()/2);
                    UI.checkLevelUp(this);
                }
            }
            else {
                endTitle.draw(game.batch, title, Gdx.graphics.getWidth()/2 - 75, Gdx.graphics.getHeight()/2);
            }
        } else {
            UI.renderPauseMenu(this);
        }

        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        spaceship = new Spaceship();
        lasers = new ArrayList<>();
        meteors = new ArrayList<>();
        minions = new ArrayList<>();
        boss = new Boss(level);
        levelingUp = false;
        gameOver = false;
        endTitle = new BitmapFont(Gdx.files.internal("titlefont.fnt"));
        title = "";
        UI = new GameUI(game);
        UI.dispose();
    }

    @Override
    public void dispose() {
        game.batch.dispose();
    }

    public void levelUpScreen() {
        levelingUp = true;
        title = "Leveling Up! Touch anywhere to proceed";
    }

    public void loseScreen() {
        gameOver = true;
        title = "You Lose!";
    }

    public void scoreCount(float delta) {
        elapsedTime += delta;

        if (elapsedTime >= 1.0f) {
            score += 10;
            elapsedTime -= 1.0f;
        }
        UI.renderScore(score);
    }

    public static void shoot(boolean state) {
        shooting = state;
    }

    public static void moveRight(boolean state) {

    }

    public void newScreen(){
        level += 1;
        spaceship = new Spaceship();
        lasers = new ArrayList<>();
        meteors = new ArrayList<>();
        minions = new ArrayList<>();
        boss = new Boss(level);
        levelingUp = false;
    }

}
