package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game;

public class MenuScreen implements Screen {

    Game game;
    Texture playButton;
    Texture exitButton;
    private static final int PLAY_BUTTON_WIDTH = 500;
    private static final int PLAY_BUTTON_HEIGHT = 200;
    private static final int EXIT_BUTTON_WIDTH = 400;
    private static final int EXIT_BUTTON_HEIGHT = 150;
    private static final int PLAY_BUTTON_Y = 800;
    private static final int EXIT_BUTTON_Y = 500;

    public MenuScreen(Game game) {
        this.game = game;
        playButton = new Texture("PlayButton.PNG");
        exitButton = new Texture("ExitButton.PNG");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        game.batch.draw(playButton, (Gdx.graphics.getWidth()-PLAY_BUTTON_WIDTH)/2, PLAY_BUTTON_Y, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        game.batch.draw(exitButton, (Gdx.graphics.getWidth()-EXIT_BUTTON_WIDTH)/2, EXIT_BUTTON_Y, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        pressed();
        game.batch.end();
    }



    public void pressed() {
        if(Gdx.input.isTouched()) {
            if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - PLAY_BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + PLAY_BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= PLAY_BUTTON_Y) {
                this.dispose();
                game.setScreen(new GameScreen(game));
                System.out.println("Game started");
            } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + EXIT_BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= EXIT_BUTTON_Y - EXIT_BUTTON_HEIGHT/2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT/2) {
                Gdx.app.exit();
            }
        }
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
    }
}
