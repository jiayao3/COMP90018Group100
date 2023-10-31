package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import com.mygdx.game.DataCallback;

public class LeaderBoardScreen implements Screen {

    private Stage stage;
    private Table table;
    private ScrollPane scrollPane;
    private TextButton backButton;
    private Game game;
    private Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
    private Texture background;

    public LeaderBoardScreen(Game game) {
        this.game = game;
        background = new Texture("backgroundImage.png");
    }

    @Override
    public void show() {

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("default-font"), skin.getColor("white"));


        table.clear();
        Label titleLabel = new Label("Leaderboard", labelStyle);
        table.add(titleLabel).colspan(2).padBottom(40).row();

        // show the leaderboard
        displayLeaderboardData();

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

//        float scale = 3f;
//        labelStyle.font.getData().setScale(scale);

        Label nameLabel = new Label(playerName, labelStyle);
        Label scoreLabel = new Label(Integer.toString(score), labelStyle);

        table.add(nameLabel).padRight(150);
        table.add(scoreLabel).padBottom(0).row();
    }

    private void displayLeaderboardData() {
        game.getFirebaseInterface().getLeaderboardData(new DataCallback<Map<String, Integer>>() {
            @Override
            public void onDataReceived(Map<String, Integer> leaderboardData) {
                // sort the data in reverse order
                TreeMap<Integer, String> sortedLeaderboard = new TreeMap<>(Collections.reverseOrder());
                for (Map.Entry<String, Integer> entry : leaderboardData.entrySet()) {
                    sortedLeaderboard.put(entry.getValue(), entry.getKey());
                }

                // add the entries to the table
                for (Map.Entry<Integer, String> entry : sortedLeaderboard.entrySet()) {
                    addLeaderboardEntry(entry.getValue(), entry.getKey());
                }
            }

            @Override
            public void onError(Exception e) {
                Gdx.app.log("LeaderBoardScreen", "Error fetching leaderboard data: " + e.getMessage());
            }
        });
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        float backgroundRatio = (float) background.getWidth() / (float) background.getHeight();
        float newBackgroundWidth = Gdx.graphics.getHeight() * backgroundRatio;
        game.batch.draw(background, (Gdx.graphics.getWidth() - newBackgroundWidth) / 2, 0, newBackgroundWidth, Gdx.graphics.getHeight());
        game.batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(new Color(0, 0, 0, 0.7f));
        shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
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
