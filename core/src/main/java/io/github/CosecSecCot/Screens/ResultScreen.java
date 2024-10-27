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
    private final Core game;
    private final Viewport viewport;
    private final Stage stage;

    public ResultScreen(Core game, GameScreen gameScreen) {
        this.game = game;
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(viewport, this.game.batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        ImageButton retryButton = new ImageButton(game.skin, "retry_button");
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.restartLevel();
            }
        });

        ImageButton menuButton = new ImageButton(game.skin, "menu_button");
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

        this.stage.addActor(table);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f);
        this.game.batch.begin();
        this.game.batch.draw(this.game.atlas.findRegion("background", 1), 0, 0);
        this.game.batch.end();

        this.stage.draw();

    }

    @Override
    public void resize(int width, int height) {
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
        this.stage.dispose();
    }
}
