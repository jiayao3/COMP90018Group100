package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game;
import com.mygdx.game.Laser;
import com.mygdx.game.Meteor;
import com.mygdx.game.GameUI;
import com.mygdx.game.Spaceship;
import com.mygdx.game.UFO;

import java.util.ArrayList;

public class GameScreen implements Screen {
    private final Game game;
    private Spaceship spaceship;
    private Meteor meteor;
    private ArrayList<Laser> lasers;
    private ArrayList<Meteor> meteors;
    private int counter = 0;
    private UFO ufo;
    private boolean gameOver;
    private BitmapFont endTitle;
    private String title;
    private GameUI UI;
    public static boolean isPaused = false;
    private int score = 0;
    private float elapsedTime = 0;
    private static boolean shooting = false;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

        spaceship = new Spaceship();
        lasers = new ArrayList<>();
        meteors = new ArrayList<>();
        ufo = new UFO();
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
                if (spaceship.HP > 0) {
                    lasers = spaceship.Draw(game.batch);
                }

                if (spaceship.HP <= 0) {
                    spaceship.sprite.setPosition(-1000, 1000);
                    loseScreen();
                }

                for(Laser laser: lasers) {
                    ufo.detectHit(laser);
                    for(Meteor meteor : meteors) {
                        meteor.detectHit(laser);
                    }
                }
                counter++;
                if(counter % 40 == 0) {
                    meteor = new Meteor();
                    meteors.add(meteor);
                    meteor.spawnMeteor();
                }
                if(ufo.HP >0) {
                    ufo.Draw(game.batch);
                }

                if(ufo.HP <=0) {
                    ufo.sprite.setPosition(1000, 1000);
                    winScreen();
                }
                UI.render(spaceship);
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
        ufo = new UFO();
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

    public void winScreen() {
        gameOver = true;
        title = "You Win!";
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

}
