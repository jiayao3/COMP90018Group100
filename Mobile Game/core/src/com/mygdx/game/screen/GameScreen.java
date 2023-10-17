package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game;
import com.mygdx.game.Laser;
import com.mygdx.game.Meteor;
import com.mygdx.game.Spaceship;
import com.mygdx.game.UFO;

import java.util.ArrayList;

public class GameScreen implements Screen {
    Game game;
    Spaceship spaceship;
    Meteor meteor;
    ArrayList<Laser> lasers;
    ArrayList<Meteor> meteors;
    int counter = 0;
    UFO ufo;
    boolean gameOver;
    BitmapFont endTitle;
    String title;

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
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        if(!gameOver) {
            for(Meteor meteor : meteors) {
                if(!meteor.gone) {
                    meteor.Draw(game.batch);
                    meteor.hitShip(spaceship);
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
        }
        else {
            endTitle.draw(game.batch, title, Gdx.graphics.getWidth()/2 - 75, Gdx.graphics.getHeight()/2);
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
}
