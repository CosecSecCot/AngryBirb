package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Scenes.GameScreenHUD;

/**
 * The game screen, implements {@link Screen}
 *
 * @author CosecSecCot
 * @see {@link Core}
 */
public class GameScreen implements Screen {
    private Core game;
    private OrthographicCamera camera;
    private GameScreenHUD hud;
    private Viewport viewport;
    private World world;
    // TODO: Level
    private int score;

    /** @param game Instance of {@link Core} */
    public GameScreen(Core game) {
        this.game = game;
        this.score = 0;
        this.hud = new GameScreenHUD(game.batch, game.font);

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Core.V_WIDTH / Core.PPM, Core.V_HEIGHT / Core.PPM, camera);

        // Make gameCamera centered in the middle of the screen
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Box2D setup
        this.world = new World(new Vector2(0, -9.8f), true);
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

        // how much time between physics calculation
        world.step(1 / 60f, 6, 2);

        hud.setScore(this.score);
    }

    @Override
    public void render(float delta) {
        update(delta);

        ScreenUtils.clear(0f, 0f, 0f, 1f);
        hud.stage.draw();
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
        world.dispose();
        game.dispose();
        hud.dispose();
    }

}
