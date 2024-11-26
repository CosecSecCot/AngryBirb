package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Scenes.GameScreenHUD;
import io.github.CosecSecCot.Sprites.*;
import io.github.CosecSecCot.Utility.CollisionHandler;
import io.github.CosecSecCot.Utility.Level;

/**
 * The game screen, implements {@link Screen}
 *
 * @see Core
 */
public class GameScreen implements Screen {
    private final Core game;
    private final GameScreenHUD hud;
    private final Viewport viewport;
    private final Camera camera;
    private final World world;
    private final InputMultiplexer inputMultiplexer;
    private final Box2DDebugRenderer debugRenderer;
    private final Level level;
    private int score;
    private static final float TIME_TO_RESULT_SCREEN = 2f;
    private float resultTimer = 0f;

    /** @param game Instance of {@link Core} */
    public GameScreen(Core game, Level level) {
        this.game = game;
        this.level = level;
        this.score = 0;
        this.hud = new GameScreenHUD(game, this);

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, camera);

        // Make gameCamera centered in the middle of the screen
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Box2D setup
        this.world = new World(new Vector2(0, -10f), true);
        CollisionHandler collisionHandler = new CollisionHandler();
        this.world.setContactListener(collisionHandler);
        this.debugRenderer = new Box2DDebugRenderer();

        // Input
        inputMultiplexer = new InputMultiplexer();

        // Ground
        BodyDef groundBodyDef = new BodyDef();
        PolygonShape groundShape = new PolygonShape();
        FixtureDef groundFixtureDef = new FixtureDef();
        Body groundBody;

        groundBodyDef.type = BodyDef.BodyType.StaticBody;
        groundBodyDef.position.set(0, 0);
        groundBody = world.createBody(groundBodyDef);
        groundShape.setAsBox(viewport.getWorldWidth(), 127);
        groundFixtureDef.shape = groundShape;
        groundBody.createFixture(groundFixtureDef);

        // Level
        switch (this.level.LEVEL_NUMBER) {
            case 1 -> {
                this.level.addBird(new Red(this.world, this.game, 100, 170));
                this.level.addBird(new Red(this.world, this.game, 160, 170));
                this.level.addBird(new Red(this.world, this.game, 220, 170));
                this.level.addPig(new NormalPig(this.world, this.game, 800, 170));
                this.level.addPig(new NormalPig(this.world, this.game, 860, 170));
                this.level.addBlock(new Wood(this.world, this.game, 740, 240, 90));
                this.level.addBlock(new Wood(this.world, this.game, 920, 240, 90));
                this.level.addBlock(new Wood(this.world, this.game, 740, 320));
                this.level.addBlock(new Wood(this.world, this.game, 920, 320));
            }
            case 2 -> {
                this.level.addBird(new Red(this.world, this.game, 100, 170));
                this.level.addBird(new Chuck(this.world, this.game, 160, 170));
                this.level.addBird(new Red(this.world, this.game, 230, 170));
                this.level.addPig(new NormalPig(this.world, this.game, 820, 170));
                this.level.addPig(new HelmetPig(this.world, this.game, 995, 200));
                this.level.addPig(new NormalPig(this.world, this.game, 1140, 170));
                this.level.addBlock(new Glass(this.world, this.game, 740, 240, 90));
                this.level.addBlock(new Glass(this.world, this.game, 740, 320));
                this.level.addBlock(new Wood(this.world, this.game, 910, 240, 90));
                this.level.addBlock(new Wood(this.world, this.game, 910, 320));
                this.level.addBlock(new Glass(this.world, this.game, 1080, 240, 90));
                this.level.addBlock(new Glass(this.world, this.game, 1080, 320));
            }
            case 3 -> {
                this.level.addBird(new Red(this.world, this.game, 100, 170));
                this.level.addBird(new Chuck(this.world, this.game, 160, 170));
                this.level.addBird(new Bomb(this.world, this.game, 230, 170));
                this.level.addPig(new HelmetPig(this.world, this.game, 820, 200));
                this.level.addPig(new KingPig(this.world, this.game, 995, 200));
                this.level.addPig(new NormalPig(this.world, this.game, 1140, 170));
                this.level.addBlock(new Stone(this.world, this.game, 740, 240, 90));
                this.level.addBlock(new Stone(this.world, this.game, 740, 320));
                this.level.addBlock(new Stone(this.world, this.game, 910, 240, 90));
                this.level.addBlock(new Stone(this.world, this.game, 910, 320));
                this.level.addBlock(new Wood(this.world, this.game, 1080, 240, 90));
                this.level.addBlock(new Wood(this.world, this.game, 1080, 320));
            }
        }
        Slingshot slingshot = new Slingshot(this.world, this.game, 300, 177, this.camera);
        this.level.setSlingshot(slingshot);

        inputMultiplexer.addProcessor(hud.getStage()); // Handles UI input
        inputMultiplexer.addProcessor(slingshot); // Handles slingshot input
    }

    public void restartLevel() {
        dispose();
        this.game.setScreen(new GameScreen(game, new Level(this.level.LEVEL_NUMBER)));
    }

    public int getScore() {
        return this.score;
    }

    public Core getGame() {
        return this.game;
    }

    public World getWorld() {
        return this.world;
    }

    @Override
    public void show() {
        this.level.applyAllRotations();
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Runs every frame to update the contents on the screen.
     *
     * @param deltaTime Delta time.
     */
    public void update(float deltaTime) {
        // how much time between physics calculation
        this.world.step(1 / 60f, 10, 5);

        this.level.update(deltaTime);
        this.score = this.level.getScore();
        this.hud.setScore(this.score);

        if (this.level.isComplete()) {
            resultTimer += deltaTime;
            if (resultTimer > TIME_TO_RESULT_SCREEN) {
                game.setScreen(new ResultScreen(game, this, this.level.won()));
            }
        }
    }

    @Override
    public void render(float delta) {
        if (!Core.paused) {
            this.update(delta);
        }

        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f);

        // Drawing sprites
        this.game.batch.begin();
        this.game.batch.draw(this.game.atlas.findRegion("background", 1), 0, 0);
        this.level.draw(this.game.batch);
        this.game.batch.end();

        debugRenderer.render(world, camera.combined);
        this.hud.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.hud.getViewport().update(width, height);
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
        this.world.dispose();
        this.hud.dispose();
    }

}
