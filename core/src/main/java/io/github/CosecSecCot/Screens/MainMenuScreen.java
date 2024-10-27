package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;

/**
 * The main menu screen, implements {@link Screen}
 *
 * @see Core
 */
public class MainMenuScreen implements Screen {
    private final Core game;
    private final Viewport viewport;
    private final Stage stage;

    /** @param game Instance of {@link Core} */
    public MainMenuScreen(Core game) {
        this.game = game;
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(this.viewport, game.batch);

        Label title = new Label("Angry Birb", game.skin, "title");
        title.setPosition(stage.getWidth()/2 - title.getWidth()/2, stage.getHeight() - title.getHeight() - 100);

        ImageButton playButton = new ImageButton(game.skin, "play_button");
        playButton.setPosition(stage.getWidth() / 2 - playButton.getWidth()/2, stage.getHeight()/2 - playButton.getHeight()/2);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        ImageButton exitButton = new ImageButton(game.skin, "exit_button");
        exitButton.setPosition(10, 10);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                System.exit(0);
            }
        });


        stage.addActor(title);
        stage.addActor(playButton);
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    /**
     * Handles Input
     *
     * @param deltaTime Delta time.
     */
    public void handleInput(float deltaTime) {
    }

    /**
     * Runs every frame to update the contents on the screen.
     *
     * @param deltaTime Delta time.
     */
    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f);
        game.batch.begin();
        game.batch.draw(game.atlas.findRegion("background", 1), 0, 0);
        game.batch.end();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        Core.logger.info("Resized MainMenuScreen to " + width + "x" + height);
        this.viewport.update(width, height);
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
