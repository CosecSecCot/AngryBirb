package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Utils.UI.Button;

import java.util.ArrayList;

/**
 * The game screen, implements {@link Screen}
 *
 * @author CosecSecCot
 * @see {@link Core}
 */
public class MainMenuScreen implements Screen {
    private Core game;
    private Viewport viewport;
    private Stage stage;
    private ArrayList<Button> buttons;

    /** @param game Instance of {@link Core} */
    public MainMenuScreen(Core game) {
        this.game = game;
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(this.viewport);
        this.buttons = new ArrayList<>();

        buttons.add(new Button("Play", new Vector2(stage.getWidth() / 2 - 100, stage.getHeight()/2 - 30), new Vector2(200, 60)));
        buttons.get(0).getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });

        for (Button button : buttons) {
            stage.addActor(button.getButton());
        }
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

        ScreenUtils.clear(0.7f, 0.7f, 0.7f, 1f);
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
        game.dispose();
        stage.dispose();
    }

}
