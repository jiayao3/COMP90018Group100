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
    Texture leaderBoardButton;
    private static final int BUTTON_WIDTH = 500;
    private static final int BUTTON_HEIGHT = 200;
    private static final int PLAY_BUTTON_Y = 800;
    private static final int EXIT_BUTTON_Y = 200;
    private static final int LEADERBOARD_BUTTON_Y = 500;

    public MenuScreen(Game game) {
        this.game = game;
        playButton = new Texture("PlayButton.PNG");
        exitButton = new Texture("ExitButton.PNG");
        leaderBoardButton = new Texture("LeaderBoardButton.PNG");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        game.batch.draw(playButton, (Gdx.graphics.getWidth()-BUTTON_WIDTH)/2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(exitButton, (Gdx.graphics.getWidth()-BUTTON_WIDTH)/2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(leaderBoardButton, (Gdx.graphics.getWidth()-BUTTON_WIDTH)/2, LEADERBOARD_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        pressed();
        game.batch.end();
    }



    public void pressed() {
        if(Gdx.input.isTouched()) {
            if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= PLAY_BUTTON_Y + BUTTON_HEIGHT &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= PLAY_BUTTON_Y) {
                this.dispose();
                game.setScreen(new GameScreen(game));
                System.out.println("Game started");
            } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= EXIT_BUTTON_Y &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= EXIT_BUTTON_Y + BUTTON_HEIGHT) {
                Gdx.app.exit();
            } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= LEADERBOARD_BUTTON_Y &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= LEADERBOARD_BUTTON_Y + BUTTON_HEIGHT) {
                this.dispose();
                game.setScreen(new LeaderBoardScreen(game));
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
