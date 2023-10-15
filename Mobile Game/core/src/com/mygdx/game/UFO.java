package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class UFO {
	public Vector2 position;
	public Sprite sprite;
	private Texture img_ufo;
	public int HP;
	public boolean right;
	public float speed = 150;
	
	public UFO() {
		img_ufo = new Texture("UFO.png");
		sprite = new Sprite(img_ufo);
		sprite.setScale(1);
		position = new Vector2((Gdx.graphics.getWidth()-sprite.getWidth())/2, Gdx.graphics.getHeight() - sprite.getHeight());
		HP = 30;
		right = true;
	}
	
	public void Update(float deltaTime) {
		if(right) position.x+=deltaTime*speed;
		if(!right) position.x-=deltaTime*speed;
		if(position.x <=0) right = true;
		if(position.x >=Gdx.graphics.getWidth() - sprite.getWidth()) right = false;
		
		if(HP==0) position.set(0, 10000);
	}
	
	public void Draw(SpriteBatch batch) {
		Update(Gdx.graphics.getDeltaTime());
		sprite.setPosition(position.x, position.y);
		sprite.draw(batch);
	}
	
	public void detectHit(Laser laser) {
		if(laser.laserSprite.getBoundingRectangle().overlaps(sprite.getBoundingRectangle())) {
			HP -= 10;
			System.out.println(HP);
			laser.gone();
		}
	}
}
