package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.CosecSecCot.Core;

public class ResultScreen implements Screen {
    private Core game;
    private Viewport viewport;
    private Stage stage;

    private final ImageButton retryButton;
    private final ImageButton menuButton;

    public ResultScreen(Core game, GameScreen gameScreen) {
        this.game = game;
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, game.batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        retryButton = new ImageButton(game.skin, "retry_button");
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.restartLevel();
            }
        });

        menuButton = new ImageButton(game.skin, "menu_button");
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LevelSelectScreen(game));
            }
        });

        Label headingLabel = new Label("RESULT", game.skin, "title");
        Label scoreLabel = new Label(String.format("SCORE: %d", gameScreen.getScore()), game.skin, "result");
        table.add(headingLabel).expandX().colspan(2).center().pad(64);
        table.row();
        table.add(scoreLabel).expandX().colspan(2).center().pad(32);
        table.row();
        table.add(menuButton).right().pad(10);
        table.add(retryButton).left().pad(10);
//        table.debug();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
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
        game.batch.draw(game.background_img, 0, 0);
        game.batch.end();

        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
