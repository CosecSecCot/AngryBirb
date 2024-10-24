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
 * @see Core
 */
public class GameScreen implements Screen {
    private final Core game;
    private final OrthographicCamera camera;
    private final GameScreenHUD hud;
    private final Viewport viewport;
    private final World world;
    // TODO: Level
    private int score;

    /** @param game Instance of {@link Core} */
    public GameScreen(Core game) {
        this.game = game;
        this.score = 0;
        this.hud = new GameScreenHUD(game, game.batch);

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, camera);

        // Make gameCamera centered in the middle of the screen
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Box2D setup
        this.world = new World(new Vector2(0, -9.8f), true);
    }

    public void restartLevel() {
        game.setScreen(new GameScreen(game));
        dispose();
    }

    public void increaseScore(int amount) {
        this.score += amount;
    }

    public int getScore() {
        return this.score;
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

        ScreenUtils.clear(0.7f, 0.7f, 0.7f, 1f);
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
