package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Spaceship {
	public static Vector2 position;
	private Texture img;
	public static Sprite sprite;
	public Laser laser;
	ArrayList<Laser> lasers;
	public static float speed = 0;
	public int HP = 1;
	
	public Spaceship() {
		img = new Texture("spaceship.png");
		sprite = new Sprite(img);
		sprite.setScale(1);
		sprite.setSize(img.getWidth() * 3, img.getHeight() * 3);
		lasers = new ArrayList<>();
		position = new Vector2((Gdx.graphics.getWidth()-sprite.getWidth())/2, 50);
	}

	public void Update(float deltaTime) {
		position.x -= deltaTime * speed;

		if (position.x <= 0) position.x = 0;
		if (position.x >= Gdx.graphics.getWidth() - sprite.getWidth()) position.x = Gdx.graphics.getWidth() - sprite.getWidth();
	}
	
	public ArrayList<Laser> Draw(SpriteBatch batch) {
		Update(Gdx.graphics.getDeltaTime());
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
		for(Laser laser: lasers) {
			if(!laser.gone){
				laser.Draw(batch);
				if(laser.laserPosition.y>Gdx.graphics.getHeight()+1) {
					laser.gone();
				}
			}
		}

		return lasers;
	}

	public int getHP() {
		return HP;
	}

	public void shoot() {
		Laser laser = new Laser();
		lasers.add(laser);
		float x = position.x + sprite.getWidth() / 2 - 4;
		float y = sprite.getHeight() - 15;
		laser.laserPosition.set(x, y);
	}

	public static void move(double degree) {
//		if (degree < 0) {
//			speed = -(float) Math.pow(degree, 2);
//		} else {
//			speed = (float) Math.pow(degree, 2);
//		}
	}

	public static void setPosition(float x, float y) {
		if (x != 0) {
			position.x = x * Gdx.graphics.getWidth() - sprite.getWidth()/2;
//			position.y = (1 - y) * Gdx.graphics.getWidth();
		}

	}

	public static Vector2 getPosition() {
		return position;
	}
}
