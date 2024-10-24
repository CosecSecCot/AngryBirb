package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.CosecSecCot.Core;

import java.util.ArrayList;

/**
 * The level select screen, implements {@link Screen}
 *
 * @see Core
 */
public class LevelSelectScreen implements Screen {
    private final Core game;
    private final Viewport viewport;
    private Stage stage;

    private final ArrayList<TextButton> levelButtons;

    public LevelSelectScreen(Core game) {
        this.game = game;
        this.stage = new Stage();
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(this.viewport, game.batch);

        this.levelButtons = new ArrayList<>();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label headingLabel = new Label("Select Level", game.skin, "title");

        table.add(headingLabel).expandX().colspan(3).center().padTop(30).padBottom(50);
        table.row();
        table.padLeft(256).padRight(256);

        ImageButton backButton = new ImageButton(game.skin, "back_button");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        for (int i = 1; i <= 3; i++) {
            TextButton button = new TextButton(String.format("%d", i), game.skin, "level_0_star");
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });
            levelButtons.add(button);
        }

        for (TextButton levelButton : levelButtons) {
            table.add(levelButton);
        }

        stage.addActor(table);
//        table.setDebug(true);
        stage.addActor(backButton);
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
