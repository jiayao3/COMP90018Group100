package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.mygdx.game.Game;

public class LeaderBoardScreen implements Screen {

    private Stage stage;
    private Table table;
    private ScrollPane scrollPane;
    private TextButton backButton;

    private Game game;
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

    public LeaderBoardScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
//        table.setBackground(someDrawable);

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("default-font"), skin.getColor("white"));

        float scale = 3.5f;
        labelStyle.font.getData().setScale(scale);

        Label titleLabel = new Label("Leaderboard", labelStyle);
        table.add(titleLabel).colspan(2).padBottom(40).row();

        addLeaderboardEntry("Player 1", 100);
        addLeaderboardEntry("Player 2", 90);

        scrollPane = new ScrollPane(table);
        scrollPane.setFillParent(true);

        // Add the ScrollPane to the stage
        stage.addActor(scrollPane);

        backButton = new TextButton("Back", new Skin(Gdx.files.internal("uiskin.json")));
        float buttonWidth = 200f;
        float buttonHeight = 100f;
        backButton.setWidth(buttonWidth);
        backButton.setHeight(buttonHeight);
        backButton.setPosition((Gdx.graphics.getWidth() - backButton.getWidth()) / 2, 100); // Adjust the position as needed


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });


        stage.addActor(backButton);
    }

    private void addLeaderboardEntry(String playerName, int score) {

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("default-font"), skin.getColor("white"));

        float scale = 3f;
        labelStyle.font.getData().setScale(scale);

        Label nameLabel = new Label(playerName, labelStyle);
        Label scoreLabel = new Label(Integer.toString(score), labelStyle);

        table.add(nameLabel).padRight(150);
        table.add(scoreLabel).padBottom(0).row();
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw the stage
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
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
        stage.dispose();
    }
}
