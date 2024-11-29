package io.github.CosecSecCot.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Screens.GameScreen;
import io.github.CosecSecCot.Screens.LevelSelectScreen;

/**
 * HUD for the game.
 */
public class GameScreenHUD implements Disposable {
    private final Stage stage;
    private final Viewport viewport;

    private final Label score;
    private final ImageButton pauseButton;
    private final Window pauseMenu;
    private final Window saveLoadPopup;

    public GameScreenHUD(Core game, GameScreen gameScreen) {
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, game.batch);

        // HUD Elements
        pauseButton = new ImageButton(game.skin, "pause_button");
        score = new Label(String.format("SCORE: %06d", 0), game.skin);
        score.setPosition(stage.getWidth() - score.getWidth() - 30, stage.getHeight() - score.getHeight() - 10);

        pauseMenu = new Window("PAUSED", game.skin);

        pauseButton.setPosition(10, stage.getHeight() - pauseButton.getHeight() - 10);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Paused");
                Core.paused = true;
                disableButtons();
                pauseMenu.setVisible(true);
            }
        });

        // Pause Menu Elements
        ImageButton resumeButton = new ImageButton(game.skin, "resume_button");
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Resumed");
                Core.paused = false;
                enableButtons();
                pauseMenu.setVisible(false);
                saveLoadPopup.setVisible(false);
            }
        });

        ImageButton retryButton = new ImageButton(game.skin, "retry_button");
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Retrying level");
                Core.paused = false;
                enableButtons();
                pauseMenu.setVisible(false);
                saveLoadPopup.setVisible(false);
                gameScreen.restartLevel();
            }
        });

        ImageButton menuButton = new ImageButton(game.skin, "menu_button");
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Going to MainMenuScreen");
                Core.paused = false;
                enableButtons();
                pauseMenu.setVisible(false);
                saveLoadPopup.setVisible(false);
                gameScreen.dispose();
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        // Save Menu Elements
        saveLoadPopup = new Window("Save Load", game.skin);

        ImageButton saveButton = new ImageButton(game.skin, "save_button");
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Saving the current level");
                saveLoadPopup.setVisible(true);
                saveLoadPopup.toFront();
            }
        });

        ImageTextButton savePopupButton = new ImageTextButton("Save", game.skin, "without_image");
        savePopupButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Saving level state...");
                gameScreen.saveLevel();
                saveLoadPopup.setVisible(false);
            }
        });

        ImageTextButton loadPopupButton = new ImageTextButton("Load", game.skin, "without_image");
        loadPopupButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Loading saved level state...");
                Core.paused = false;
                enableButtons();
                pauseMenu.setVisible(false);
                saveLoadPopup.setVisible(false);
                gameScreen.loadLevel();
            }
        });

        ImageTextButton cancelPopupButton = new ImageTextButton("Cancel", game.skin, "without_image");
        cancelPopupButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.logger.info("Cancelled Save/Load operation");
                saveLoadPopup.setVisible(false);
            }
        });


        pauseMenu.padTop(64);
        pauseMenu.padLeft(32);
        pauseMenu.setHeight(this.stage.getHeight());
        pauseMenu.setWidth(this.stage.getWidth()/3);
        pauseMenu.setPosition(0, 0);
        pauseMenu.add(resumeButton).padBottom(100).row();
        pauseMenu.add(retryButton).pad(10).row();
        pauseMenu.add(saveButton).pad(10).row();
        pauseMenu.add(menuButton);
        pauseMenu.setResizable(false);
        pauseMenu.setMovable(false);
        pauseMenu.setVisible(false);

        saveLoadPopup.padTop(64);
        saveLoadPopup.padLeft(32);
        saveLoadPopup.setWidth(stage.getWidth() / 2);
        saveLoadPopup.setHeight(stage.getHeight() / 2);
        saveLoadPopup.setPosition(stage.getWidth() / 4, stage.getHeight() / 4);
        saveLoadPopup.add(savePopupButton).pad(10);
        saveLoadPopup.add(loadPopupButton).pad(10).row();
        saveLoadPopup.add(cancelPopupButton).colspan(2).pad(10);
        saveLoadPopup.setVisible(false);
        saveLoadPopup.setMovable(false);
        saveLoadPopup.setResizable(false);

        this.stage.addActor(this.score);
        this.stage.addActor(pauseButton);
        this.stage.addActor(pauseMenu);
        stage.addActor(saveLoadPopup);
//        stage.setDebugAll(true);

    }

    public void disableButtons() {
        Core.logger.info("Disabled Buttons");
        pauseButton.setDisabled(true);
        pauseButton.setTouchable(Touchable.disabled);
    }

    public void enableButtons() {
        Core.logger.info("Enabled Buttons");
        pauseButton.setDisabled(false);
        pauseButton.setTouchable(Touchable.enabled);
    }

    public Stage getStage() {
        return this.stage;
    }

    public Viewport getViewport() {
        return this.viewport;
    }

    public void setScore(int newScore) {
        this.score.setText(String.format("SCORE: %06d", newScore));
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

}
