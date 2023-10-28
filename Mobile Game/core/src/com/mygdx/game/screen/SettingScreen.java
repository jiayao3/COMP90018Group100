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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.AttackMode;
import com.mygdx.game.ControlMode;
import com.mygdx.game.Game;

public class SettingScreen implements Screen {
    private Stage stage;
    private Game game;
    private Label descriptionLabel;
    private static ControlMode curControlMode = ControlMode.TOUCH_MODE;
    private static AttackMode curAttackMode = AttackMode.TAP_MODE;
    private TextButton faceButton;
    private TextButton touchButton;
    private TextButton gyroButton;
    private TextButton tapButton;
    private TextButton blinkButton;
    private TextButton voiceButton;

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
        faceButton = new TextButton("Face", skin);
        touchButton = new TextButton("Touch", skin);
        gyroButton = new TextButton("Gyroscope", skin);

        Label attackLabel = new Label("Attack Control", labelStyle);
        tapButton = new TextButton("Tap", skin);
        blinkButton = new TextButton("Eyes", skin);
        voiceButton = new TextButton("Voice", skin);

        setButtonColor();
        // Add listeners to the buttons
        addButtonListener(faceButton, "Use face to control the spaceship", ControlMode.FACE_MODE, null, touchButton, gyroButton);
        addButtonListener(touchButton, "Touches left and right side of the screen to control the spaceship", ControlMode.TOUCH_MODE, null, faceButton, gyroButton);
        addButtonListener(gyroButton, "Use gyroscope to control the spaceship", ControlMode.GYROSCOPE_MODE, null, faceButton, touchButton);
        addButtonListener(tapButton, "Tap screen to fire laser", null, AttackMode.TAP_MODE, blinkButton, voiceButton);
        addButtonListener(blinkButton, "Blink eyes to fire laser", null, AttackMode.EYES_BLINKING_MODE, tapButton, voiceButton);
        addButtonListener(voiceButton, "Use voice to fire laser", null, AttackMode.VOICE_MODE, tapButton, blinkButton);


        descriptionLabel = new Label("", labelStyle);
        descriptionLabel.setWrap(true);
        descriptionLabel.setAlignment(Align.center);

        // Add widgets to table and stage
        table.add(movementLabel).colspan(3).padBottom(100).row();
        table.add(faceButton).width(buttonWidth).padRight(10);
        table.add(touchButton).width(buttonWidth).padRight(10);
        table.add(gyroButton).width(buttonWidth).padTop(0).row();
        table.add(attackLabel).colspan(3).padTop(100).padBottom(100).row();
        table.add(tapButton).width(buttonWidth).padRight(10);
        table.add(blinkButton).width(buttonWidth).padRight(10);
        table.add(voiceButton).width(buttonWidth).padTop(0).row();
        table.row().width(Gdx.graphics.getWidth() - 40).padTop(100); // Add some padding
        table.add(descriptionLabel).colspan(3);

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

    private void addButtonListener(final TextButton activeButton, final String description, final ControlMode controlMode, final AttackMode attackMode, final TextButton... otherButtons) {
        activeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                activeButton.setColor(Color.GREEN);
                for (TextButton button : otherButtons) {
                    button.setColor(Color.WHITE);
                }
                descriptionLabel.setText(description);
                if (controlMode != null) {
                    SettingScreen.curControlMode = controlMode;
                } else {
                    SettingScreen.curAttackMode = attackMode;
                }
            }
        });
    }

    public void setButtonColor() {
        if (SettingScreen.getCurAttackMode() == AttackMode.TAP_MODE) {
            tapButton.setColor(Color.GREEN);
        } else if (SettingScreen.getCurAttackMode() == AttackMode.EYES_BLINKING_MODE) {
            blinkButton.setColor(Color.GREEN);
        } else {
            voiceButton.setColor(Color.GREEN);
        }

        if (SettingScreen.getCurControlMode() == ControlMode.FACE_MODE) {
            faceButton.setColor(Color.GREEN);
        } else if (SettingScreen.getCurControlMode() == ControlMode.TOUCH_MODE) {
            touchButton.setColor(Color.GREEN);
        } else {
            gyroButton.setColor(Color.GREEN);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        setButtonColor();
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

    public static ControlMode getCurControlMode() {
        return curControlMode;
    }

    public static AttackMode getCurAttackMode() {
        return curAttackMode;
    }
}
