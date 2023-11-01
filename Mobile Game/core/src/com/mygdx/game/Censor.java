package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Censor {
    private Vector2 topLeft;
    private Vector2 bottomRight;
    private Texture censorTexture;

    public Censor(Vector2 topLeft, Vector2 bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
        createCensorTexture();
    }

    private void createCensorTexture() {
        int width = (int) (bottomRight.x - topLeft.x);
        int height = (int) (bottomRight.y - topLeft.y);

        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        censorTexture = new Texture(pixmap);
        pixmap.dispose();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(censorTexture, topLeft.x, topLeft.y);
    }
}
