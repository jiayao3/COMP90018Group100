package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Game;

public class MenuScreen implements Screen {

    Game game;
    Texture playButton;
    Texture leaderBoardButton;
    Texture settingsButton;
    Texture exitButton;

    private static final float BUTTON_WIDTH = Gdx.graphics.getWidth() * 0.45f;
    private static final float BUTTON_HEIGHT = Gdx.graphics.getHeight() * 0.07f;
    private static final float PLAY_BUTTON_Y = Gdx.graphics.getHeight() * 0.34f;
    private static final float LEADERBOARD_BUTTON_Y = Gdx.graphics.getHeight() * 0.26f;
    private static final float SETTINGS_BUTTON_Y = Gdx.graphics.getHeight() * 0.18f;
    private static final float EXIT_BUTTON_Y = Gdx.graphics.getHeight() * 0.1f;
    private Texture background;

    public MenuScreen(Game game) {
        this.game = game;
        playButton = new Texture("PlayButton.PNG");
        leaderBoardButton = new Texture("LeaderBoardButton.PNG");
        settingsButton = new Texture("SettingsButton.PNG");
        exitButton = new Texture("ExitButton.PNG");
        background = new Texture("backgroundImage.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.batch.begin();
        float backgroundRatio = (float) background.getWidth() / (float) background.getHeight();
        float newBackgroundWidth = Gdx.graphics.getHeight() * backgroundRatio;
        game.batch.draw(background, (Gdx.graphics.getWidth() - newBackgroundWidth) / 2, 0, newBackgroundWidth, Gdx.graphics.getHeight());
        game.batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.2f));
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        game.batch.begin();
        game.batch.draw(playButton, (Gdx.graphics.getWidth()-BUTTON_WIDTH)/2, PLAY_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(leaderBoardButton, (Gdx.graphics.getWidth()-BUTTON_WIDTH)/2, LEADERBOARD_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(settingsButton, (Gdx.graphics.getWidth()-BUTTON_WIDTH)/2, SETTINGS_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        game.batch.draw(exitButton, (Gdx.graphics.getWidth()-BUTTON_WIDTH)/2, EXIT_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
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
            } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - BUTTON_WIDTH) / 2 &&
                    Gdx.input.getX() <= (Gdx.graphics.getWidth() + BUTTON_WIDTH) / 2 &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() >= SETTINGS_BUTTON_Y &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() <= SETTINGS_BUTTON_Y + BUTTON_HEIGHT) {
                this.dispose();
                game.setScreen(new SettingScreen(game));
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
