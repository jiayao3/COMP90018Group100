package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Spaceship spaceship;
	Meteor meteor;
	ArrayList<Laser> lasers;
	ArrayList<Meteor> meteors;
	int counter = 0;
	UFO ufo;
	boolean gameOver;
	BitmapFont endTitle;
	String title;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		spaceship = new Spaceship();
		lasers = new ArrayList<>();
		meteors = new ArrayList<>();
		ufo = new UFO();
		gameOver = false;
		endTitle = new BitmapFont(Gdx.files.internal("titlefont.fnt"));
		title = "";
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();
		if(!gameOver) {
			for(Meteor meteor : meteors) {
				if(!meteor.gone) {
					meteor.Draw(batch);
					meteor.hitShip(spaceship);
				}
			}
			if (spaceship.HP > 0) {
				lasers = spaceship.Draw(batch);
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
				ufo.Draw(batch);
			}
			
			if(ufo.HP <=0) {
				ufo.sprite.setPosition(1000, 1000);
				winScreen();
			}
		}
		else {
			endTitle.draw(batch, title, Gdx.graphics.getWidth()/2 - 75, Gdx.graphics.getHeight()/2);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
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
