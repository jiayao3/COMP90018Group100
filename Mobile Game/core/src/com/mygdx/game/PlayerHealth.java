package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import sun.jvm.hotspot.gc.shared.Space;

public class PlayerHealth {
    public Vector2 position;
    private Texture fullHeartTexture;
    private Texture emptyHeartTexture;
    public Sprite sprite;
    private static final int margin = 15;
    private static final int heartSize = 50;

    public PlayerHealth() {
        fullHeartTexture = new Texture("heart-full.png");
        emptyHeartTexture = new Texture("heart-empty.png");
    }

    public void renderHearts(SpriteBatch batch, Spaceship spaceship) {
        for (int i = 0; i < spaceship.getHP(); i++) {
            Texture heartTexture;
            if (i < spaceship.getHP()) {
                heartTexture = fullHeartTexture;
            } else {
                heartTexture = emptyHeartTexture;
            }

            batch.draw(heartTexture, margin + i * (heartSize + margin), margin, heartSize, heartSize);
        }
    }


}
