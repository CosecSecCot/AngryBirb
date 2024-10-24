package io.github.CosecSecCot.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Screens.GameScreen;
import io.github.CosecSecCot.Screens.LevelSelectScreen;
import io.github.CosecSecCot.Screens.ResultScreen;

/**
 * HUD for the game.
 */
public class GameScreenHUD implements Disposable {
    Core game;
    public Stage stage;
    public Viewport viewport;

    private final Label score;
    private final ImageButton pauseButton;
    private final Window pauseMenu;
    private final ImageButton addScoreButton;
    private final ImageButton resumeButton;
    private final ImageButton retryButton;
    private final ImageButton menuButton;
    private final ImageButton resultButton;

    public GameScreenHUD(Core game, GameScreen gameScreen) {
        this.game = game;
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, game.batch);

        pauseButton = new ImageButton(game.skin, "pause_button");
        score = new Label(String.format("SCORE: %06d", 0), game.skin);
        score.setPosition(stage.getWidth() - score.getWidth() - 30, stage.getHeight() - score.getHeight() - 10);

        pauseMenu = new Window("PAUSED", game.skin);

        pauseButton.setPosition(10, stage.getHeight() - pauseButton.getHeight() - 10);
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.paused = true;
                disableButtons();
                pauseMenu.setVisible(true);
            }
        });

        addScoreButton = new ImageButton(game.skin, "add_score");
        addScoreButton.setPosition(stage.getWidth() - addScoreButton.getWidth() - 30, stage.getHeight() - score.getHeight() - addScoreButton.getHeight() - 30);
        addScoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.increaseScore(1000);
                setScore(gameScreen.getScore());
            }
        });

        resumeButton = new ImageButton(game.skin, "resume_button");
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.paused = false;
                enableButtons();
                pauseMenu.setVisible(false);
            }
        });

        retryButton = new ImageButton(game.skin, "retry_button");
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.paused = false;
                enableButtons();
                pauseMenu.setVisible(false);
                gameScreen.restartLevel();
            }
        });

        menuButton = new ImageButton(game.skin, "menu_button");
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Core.paused = false;
                enableButtons();
                pauseMenu.setVisible(false);
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        pauseMenu.padTop(64);
        pauseMenu.padLeft(32);
        pauseMenu.setHeight(stage.getHeight());
        pauseMenu.setWidth(stage.getWidth()/3);
        pauseMenu.setPosition(0, 0);
        pauseMenu.add(resumeButton).padBottom(100).row();
        pauseMenu.add(retryButton).pad(10).row();
        pauseMenu.add(menuButton);
        pauseMenu.setResizable(false);
        pauseMenu.setMovable(false);
        pauseMenu.setVisible(false);

        resultButton = new ImageButton(game.skin, "result_button");
        resultButton.setPosition(stage.getWidth() - resultButton.getWidth() - 10, 10);
        resultButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ResultScreen(game, gameScreen));
            }
        });

        stage.addActor(score);
        stage.addActor(pauseButton);
        stage.addActor(addScoreButton);
        stage.addActor(resultButton);
        stage.addActor(pauseMenu);
//        stage.setDebugAll(true);
    }

    public void disableButtons() {
        pauseButton.setDisabled(true);
        pauseButton.setTouchable(Touchable.disabled);

        addScoreButton.setDisabled(true);
        addScoreButton.setTouchable(Touchable.disabled);

        resultButton.setDisabled(true);
        resultButton.setTouchable(Touchable.disabled);
    }

    public void enableButtons() {
        pauseButton.setDisabled(false);
        pauseButton.setTouchable(Touchable.enabled);

        addScoreButton.setDisabled(false);
        addScoreButton.setTouchable(Touchable.enabled);

        resultButton.setDisabled(false);
        resultButton.setTouchable(Touchable.enabled);
    }

    public void setScore(Integer newScore) {
        score.setText(String.format("SCORE: %06d", newScore));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
