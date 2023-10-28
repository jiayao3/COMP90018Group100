package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Game;

public class SettingScreen implements Screen {
    private Stage stage;
    private Game game;

    public SettingScreen(final Game game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Create UI widgets
        Table table = new Table();
        table.setFillParent(true);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("default-font"), skin.getColor("white"));

        Label label = new Label("Sample Text", labelStyle);
        Label movementLabel = new Label("Movement Control", labelStyle);


        float buttonWidth = 200;
        final TextButton faceButton = new TextButton("Face", skin);
        final TextButton touchButton = new TextButton("Touch", skin);
        final TextButton gyroButton = new TextButton("Gyroscope", skin);

        Label attackLabel = new Label("Attack Control", labelStyle);
        TextButton tapButton = new TextButton("Tap", skin);
        TextButton blinkButton = new TextButton("Eyes", skin);
        TextButton voiceButton = new TextButton("Voice", skin);

        // Add listeners to the buttons
        addButtonListener(faceButton, touchButton, gyroButton);
        addButtonListener(touchButton, faceButton, gyroButton);
        addButtonListener(gyroButton, faceButton, touchButton);
        addButtonListener(tapButton, blinkButton, voiceButton);
        addButtonListener(blinkButton, tapButton, voiceButton);
        addButtonListener(voiceButton, tapButton, blinkButton);

        // Add widgets to table and stage
        table.add(movementLabel).colspan(3).padBottom(10).row();
        table.add(faceButton).width(buttonWidth).padRight(10);
        table.add(touchButton).width(buttonWidth).padRight(10);
        table.add(gyroButton).width(buttonWidth).padTop(0).row();
        table.add(attackLabel).colspan(3).padBottom(10).row();
        table.add(tapButton).width(buttonWidth).padRight(10);
        table.add(blinkButton).width(buttonWidth).padRight(10);
        table.add(voiceButton).width(buttonWidth).padTop(0).row();

        TextButton backButton = new TextButton("Back", new Skin(Gdx.files.internal("uiskin.json")));
        backButton.setWidth(200);
        backButton.setHeight(100);
        backButton.setPosition((Gdx.graphics.getWidth() - backButton.getWidth()) / 2, 100);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });


        stage.addActor(table);
        stage.addActor(backButton);
    }

    private void addButtonListener(final TextButton activeButton, final TextButton... otherButtons) {
        activeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activeButton.setColor(Color.GREEN);
                for (TextButton button : otherButtons) {
                    button.setColor(Color.WHITE);
                }
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
