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
	public int HP = 30;
	
	public Spaceship() {
		img = new Texture("spaceship.png");
		sprite = new Sprite(img);
		sprite.setScale(1);
		lasers = new ArrayList<>();
		position = new Vector2((Gdx.graphics.getWidth()-sprite.getWidth())/2, 0);
	}
	
	public void Update(float deltaTime) {
		if(Gdx.input.isKeyPressed(Keys.A)) position.x-=deltaTime*speed;
		if(Gdx.input.isKeyPressed(Keys.D)) position.x+=deltaTime*speed;
		if(Gdx.input.isButtonJustPressed(0)) {
			laser = new Laser();
			lasers.add(laser);
			float x = position.x + sprite.getWidth()/2 - 4; 
			float y = sprite.getHeight() - 15;
			laser.laserPosition.set(x,y);
		}
		if(position.x <=0) position.x = 0;
		if(position.x >=Gdx.graphics.getWidth() - sprite.getWidth()) position.x = Gdx.graphics.getWidth() - sprite.getWidth();
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
}
