package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Spaceship {
	public Vector2 position;
	private Texture img;
	public Sprite sprite;
	public Laser laser;
	ArrayList<Laser> lasers;
	public float speed = 300;
	public int HP = 5;
	
	public Spaceship() {
		img = new Texture("spaceship.png");
		sprite = new Sprite(img);
		sprite.setScale(1);
		sprite.setSize(img.getWidth() * 3, img.getHeight() * 3);
		lasers = new ArrayList<>();
		position = new Vector2((Gdx.graphics.getWidth()-sprite.getWidth())/2, 50);
	}
	
	public void Update(float deltaTime) {
		if(Gdx.input.isTouched()) {
			float touchX = Gdx.input.getX();

			// Check if the touch is on the left or right side of the screen
			if (touchX < Gdx.graphics.getWidth() / 2) {
				position.x -= deltaTime * speed;
			} else {
				position.x += deltaTime * speed;
			}

			// Fire laser
			if(Gdx.input.justTouched()) {
				Laser laser = new Laser();
				lasers.add(laser);
				float x = position.x + sprite.getWidth() / 2 - 4;
				float y = sprite.getHeight() - 15;
				laser.laserPosition.set(x, y);
			}
		}

		// within screen
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
}
