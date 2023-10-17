package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game;

public class MenuScreen implements Screen, InputProcessor {

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
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        game.batch.draw(playButton, (Gdx.graphics.getWidth()-PLAY_BUTTON_WIDTH)/2, 800, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        game.batch.draw(exitButton, (Gdx.graphics.getWidth()-EXIT_BUTTON_WIDTH)/2, 500, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        touched();
        game.batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        if (screenX >= (Gdx.graphics.getWidth() - PLAY_BUTTON_WIDTH) / 2 &&
//                screenX <= (Gdx.graphics.getWidth() + PLAY_BUTTON_WIDTH) / 2 &&
//                screenY >= 800 - PLAY_BUTTON_HEIGHT/2 &&
//                screenY <= 800 + PLAY_BUTTON_HEIGHT/2) {
//            this.dispose();
//            game.setScreen(new GameScreen(game));
//        }
        return false;
    }

    public void touched() {
        if(Gdx.input.isTouched()) {
            if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - PLAY_BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + PLAY_BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= PLAY_BUTTON_Y - PLAY_BUTTON_HEIGHT/2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= PLAY_BUTTON_Y + PLAY_BUTTON_HEIGHT/2) {
                this.dispose();
                game.setScreen(new GameScreen(game));
            } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - EXIT_BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + EXIT_BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= EXIT_BUTTON_Y - EXIT_BUTTON_HEIGHT/2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= EXIT_BUTTON_Y + EXIT_BUTTON_HEIGHT/2) {
                Gdx.app.exit();
            }
        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
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
