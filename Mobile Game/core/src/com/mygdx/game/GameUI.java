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
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.MenuScreen;

public class GameUI {
    private static final int MARGIN = 15;
    private static final int HEARTSIZE = 50;
    private static final int PAUSEBUTTONSIZE = 150;
    private final Texture fullHeartTexture;
    private final Texture emptyHeartTexture;
    private final Texture pauseButtonTexture;
    private final Texture resumeButtonTexture;
    private final Texture replayButtonTexture;
    private final Texture menuButtonTexture;
    private final Game game;
    private BitmapFont font;
    private BitmapFont pauseFont;
    final float MENU_BUTTON_WIDTH = Gdx.graphics.getWidth() * 0.45f;
    final float MENU_BUTTON_HEIGHT = Gdx.graphics.getHeight() * 0.07f;

    final float STRING_BUTTON_GAP = Gdx.graphics.getHeight() * 0.06f;
    final float BUTTON_BUTTON_GAP = Gdx.graphics.getHeight() * 0.02f;

    private static final int RESUME_BUTTON_Y = 800;
    private static final int MENU_BUTTON_Y = 500;



    public GameUI(Game game) {
        fullHeartTexture = new Texture("heart-full.png");
        emptyHeartTexture = new Texture("heart-empty.png");
        pauseButtonTexture = new Texture("Pause.png");
        resumeButtonTexture = new Texture("ResumeButton.PNG");
        replayButtonTexture = new Texture("ReplayButton.png");
        menuButtonTexture = new Texture("MenuButton.PNG");
        this.game = game;
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
    }

    public void render(Spaceship spaceship) {
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
        GlyphLayout layout = new GlyphLayout(font, "Your Current score: " + gameScreen.getScore());
        // Make it to be at center
        float stringWidth = layout.width;
        float stringHeight = layout.height;
        float x = (Gdx.graphics.getWidth() - stringWidth) / 2;
        float y = (Gdx.graphics.getHeight() + stringHeight) / 2 + 100;
        // TODO: Trying to implement a new font
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/Karma Future.otf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        parameter.size = 24; // set the desired font size
//        BitmapFont font = generator.generateFont(parameter);
//        generator.dispose();
        // Change the size
        font.getData().setScale(5f, 5f);
        // Draw shadow color
        font.setColor(Color.GRAY);
        font.draw(game.batch, layout, x + 5, y - 5);
        // Draw main text color
        font.setColor(Color.YELLOW);
        font.draw(game.batch, layout, x, y);

        game.batch.draw(resumeButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, RESUME_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        game.batch.draw(menuButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, MENU_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() <= RESUME_BUTTON_Y + MENU_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() >= RESUME_BUTTON_Y) {
            GameScreen.isPaused = false;
        } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() >= MENU_BUTTON_Y &&
                Gdx.graphics.getHeight() - Gdx.input.getY() <= MENU_BUTTON_Y + MENU_BUTTON_HEIGHT) {
            game.setScreen(new MenuScreen(game));
            gameScreen.hide();
            GameScreen.isPaused = false;
        }
    }

    public void renderLose(GameScreen gameScreen) {

        game.batch.draw(replayButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, RESUME_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
        game.batch.draw(menuButtonTexture, (Gdx.graphics.getWidth()-MENU_BUTTON_WIDTH)/2, MENU_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);

        if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() <= RESUME_BUTTON_Y + MENU_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() >= RESUME_BUTTON_Y) {
            game.setScreen(new GameScreen(game));
            gameScreen.hide();
            GameScreen.gameOver = false;
        } else if (Gdx.input.getX() >= (Gdx.graphics.getWidth() - MENU_BUTTON_WIDTH) / 2 &&
                Gdx.input.getX() <= (Gdx.graphics.getWidth() + MENU_BUTTON_WIDTH) / 2 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() >= MENU_BUTTON_Y &&
                Gdx.graphics.getHeight() - Gdx.input.getY() <= MENU_BUTTON_Y + MENU_BUTTON_HEIGHT) {
            game.setScreen(new MenuScreen(game));
            gameScreen.hide();
            GameScreen.gameOver = false;
        }
    }

    public void renderScore(int score) {
        font.draw(game.batch, "Score: " + score, Gdx.graphics.getWidth() - 300, 50 + MARGIN);
    }

    public void dispose() {
        font.dispose();
        pauseFont.dispose();
    }

    public void checkLevelUp(GameScreen gameScreen){
        if(Gdx.input.isTouched()){
            gameScreen.newScreen();
        }
    }

}
