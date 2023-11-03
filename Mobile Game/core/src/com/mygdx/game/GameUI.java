package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Items.CustomisedButtonStyle;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class GameUI {
    private static final int MARGIN = 15;
    private static final int HEARTSIZE = 50;
    private static final int PAUSEBUTTONSIZE = 150;
    private final Texture fullHeartTexture;
    private final Texture emptyHeartTexture;
    private final Texture pauseButtonTexture;
//    private final Texture resumeButtonTexture;
//    private final Texture replayButtonTexture;
//    private final Texture menuButtonTexture;
    private final Stage stage;
    private final GameScreen gameScreen;
    private TextButton resumeButton;
    private TextButton replayButton;
    private TextButton menuButton;

    private final Game game;
    private BitmapFont font;
    private BitmapFont pauseFont;
    final float MENU_BUTTON_WIDTH = Gdx.graphics.getWidth() * 0.45f;
    final float MENU_BUTTON_HEIGHT = Gdx.graphics.getHeight() * 0.07f;

    final float STRING_BUTTON_GAP = Gdx.graphics.getHeight() * 0.06f;
    final float BUTTON_BUTTON_GAP = Gdx.graphics.getHeight() * 0.02f;

    private static final int RESUME_BUTTON_Y = 800;
    private static final int MENU_BUTTON_Y = 500;



    public GameUI(final Game game, final GameScreen gameScreen) {
        this.stage = new Stage();
        this.game = game;
        this.gameScreen = gameScreen;

        font = new BitmapFont();
        font.getData().setScale(3);

        // Import a new font for "pauseFont"
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/neon_pixel-7.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24; // set the desired font size
        pauseFont = generator.generateFont(parameter);
        generator.dispose();
        // Change the size
        pauseFont.getData().setScale(4.5f, 4.5f);


        fullHeartTexture = new Texture("heart-full.png");
        emptyHeartTexture = new Texture("heart-empty.png");
        pauseButtonTexture = new Texture("Pause.png");

        TextButton.TextButtonStyle buttonStyle = new CustomisedButtonStyle(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT).getButtonStyle();
        resumeButton = new TextButton("RESUME", buttonStyle);
        replayButton = new TextButton("REPLAY", buttonStyle);
        menuButton = new TextButton("MENU", buttonStyle);
        resumeButton.setSize(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        replayButton.setSize(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        menuButton.setSize(MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        resumeButton.setPosition((Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2, RESUME_BUTTON_Y);
        replayButton.setPosition((Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2, RESUME_BUTTON_Y);
        menuButton.setPosition((Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2, MENU_BUTTON_Y);

        // Add listeners to buttons
        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameScreen.isPaused = false;
            }
        });

        replayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                hideGameScreen(gameScreen);
                GameScreen.gameOver = false;
            }
        });

        menuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                gameScreen.hide();
                GameScreen.isPaused = false;
            }
        });

        // Add buttons to stage
        stage.addActor(resumeButton);
        stage.addActor(replayButton);
        stage.addActor(menuButton);

        // Set input processor to the stage
        Gdx.input.setInputProcessor(stage);
    }

    private void hideGameScreen(GameScreen gameScreen) {
        gameScreen.hide();
    }

    public void render(Spaceship spaceship) {
//        resumeButton.remove(); // Don't show the resume button on the game over menu
//        replayButton.remove(); // Show the replay button on the game over menu
//        menuButton.remove(); // Show the menu button on the game over menu

        for (int i = 0; i < spaceship.getHP(); i++) {
            Texture heartTexture;
            if (i < spaceship.getHP()) {
                heartTexture = fullHeartTexture;
            } else {
                heartTexture = emptyHeartTexture;
            }

            game.batch.draw(heartTexture, MARGIN + i * (HEARTSIZE + MARGIN), MARGIN, HEARTSIZE, HEARTSIZE);
        }

        float x = Gdx.graphics.getWidth() - PAUSEBUTTONSIZE - MARGIN;
        float y = Gdx.graphics.getHeight() - PAUSEBUTTONSIZE - MARGIN;

        game.batch.draw(pauseButtonTexture, x, y, PAUSEBUTTONSIZE, PAUSEBUTTONSIZE);
        checkPauseButtonClick(x, y);
    }

    private void checkPauseButtonClick(float x, float y) {

        if (Gdx.input.isTouched() && Gdx.input.getX() >= x + MARGIN && Gdx.input.getX() <= x + PAUSEBUTTONSIZE &&
                Gdx.input.getY() >= MARGIN && Gdx.input.getY() <= MARGIN + PAUSEBUTTONSIZE) {

            GameScreen.isPaused = true;
        }
    }

    public void renderPauseMenu(GameScreen gameScreen) {

        // Print the current score
        // Define the words use pauseFont
//        GlyphLayout layout = new GlyphLayout(pauseFont, "Your Current score: \n" + gameScreen.getScore());
//        // Define the position - Make it to be at center based on the string
//        float stringWidth = layout.width;
//        float stringHeight = layout.height;
//        float x = (Gdx.graphics.getWidth() - stringWidth) / 2;
//        float y = (Gdx.graphics.getHeight() + stringHeight) / 2 + 100;
//        // Draw shadow color
//        pauseFont.setColor(Color.GRAY);
//        pauseFont.draw(game.batch, layout, x + 5, y - 5);
//        // Draw main text color
//        pauseFont.setColor(Color.YELLOW);
//        pauseFont.draw(game.batch, layout, x, y);

        // Create two separate GlyphLayout objects
        GlyphLayout layoutText = new GlyphLayout(pauseFont, "Your Current score:");
        GlyphLayout layoutScore = new GlyphLayout(pauseFont, String.valueOf(gameScreen.getScore()));
        // Calculate x-coordinates to center both strings
        float xText = (Gdx.graphics.getWidth() - layoutText.width) / 2;
        float xScore = (Gdx.graphics.getWidth() - layoutScore.width) / 2;
        // Define y-coordinates for the strings
        float spacing = 10; // spacing between the two lines
        float yText = Gdx.graphics.getHeight() / 2 + layoutText.height + spacing / 2 + 200;
        float yScore = Gdx.graphics.getHeight() / 2 - layoutScore.height - spacing / 2 + 200;
        // Draw both strings
        pauseFont.setColor(Color.valueOf("#FFFF00"));
        pauseFont.draw(game.batch, layoutText, xText, yText);
        pauseFont.draw(game.batch, layoutScore, xScore, yScore);

        // Before drawing the stage, set the visibility or add/remove actors
        replayButton.remove(); // Don't show the replay button on the pause menu
        resumeButton.setVisible(true); // Show the resume button on the pause menu
        menuButton.setVisible(true); // Show the menu button on the pause menu

        // Add the buttons to the stage if they're not already added
        if (!resumeButton.hasParent()) stage.addActor(resumeButton);
        if (!menuButton.hasParent()) stage.addActor(menuButton);


        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();


        // redefined the button positions
//        final float RESUME_BUTTON_Y = yScore - layoutScore.height + - STRING_BUTTON_GAP - MENU_BUTTON_HEIGHT;
//        final float MENU_BUTTON_Y = RESUME_BUTTON_Y - BUTTON_BUTTON_GAP - MENU_BUTTON_HEIGHT;


        // Display buttons
//        game.batch.draw(resumeButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, RESUME_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
//        game.batch.draw(menuButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, MENU_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
//        if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() <= RESUME_BUTTON_Y + MENU_BUTTON_HEIGHT &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() >= RESUME_BUTTON_Y) {
//            GameScreen.isPaused = false;
//        } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() >= MENU_BUTTON_Y &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() <= MENU_BUTTON_Y + MENU_BUTTON_HEIGHT) {
//            game.setScreen(new MenuScreen(game));
//            gameScreen.hide();
//            GameScreen.isPaused = false;
//        }
    }

    public void renderLose(GameScreen gameScreen) {

        // Before drawing the stage, set the visibility or add/remove actors
        resumeButton.remove(); // Don't show the resume button on the game over menu
        replayButton.setVisible(true); // Show the replay button on the game over menu
        menuButton.setVisible(true); // Show the menu button on the game over menu

        // Add the buttons to the stage if they're not already added
        if (!replayButton.hasParent()) stage.addActor(replayButton);
        if (!menuButton.hasParent()) stage.addActor(menuButton);

        // Handle the stage logic
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        stage.act();
        stage.draw();
//        game.batch.draw(replayButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, RESUME_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
//        game.batch.draw(menuButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, MENU_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
//
//        if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() <= RESUME_BUTTON_Y + MENU_BUTTON_HEIGHT &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() >= RESUME_BUTTON_Y) {
//            game.setScreen(new GameScreen(game));
//            gameScreen.hide();
//            GameScreen.gameOver = false;
//        } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() >= MENU_BUTTON_Y &&
//                Gdx.graphics.getHeight() - Gdx.input.getY() <= MENU_BUTTON_Y + MENU_BUTTON_HEIGHT) {
//            game.setScreen(new MenuScreen(game));
//            gameScreen.hide();
//            GameScreen.gameOver = false;
//        }
    }

    public void renderScore(int score) {
        font.draw(game.batch, "Score: " + score, Gdx.graphics.getWidth() - 300, 50 + MARGIN);
    }

    public void dispose() {
        font.dispose();
        pauseFont.dispose();
//        stage.dispose();
    }

    public void checkLevelUp(GameScreen gameScreen){
        if(Gdx.input.isTouched()){
            gameScreen.newScreen();
        }
    }

    public Stage getStage() {
        return stage;
    }

}
