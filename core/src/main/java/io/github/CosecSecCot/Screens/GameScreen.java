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
import io.github.CosecSecCot.Utility.LevelSave;

import java.io.*;

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
    public GameScreen(World world, Core game, Level level, boolean loaded) {
        this.game = game;
        this.level = level;
        this.score = 0;
        this.hud = new GameScreenHUD(game, this);

        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, camera);

        // Make gameCamera centered in the middle of the screen
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        // Box2D setup
        this.world = world;
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
        if (!loaded) {
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
        }
        Slingshot slingshot = new Slingshot(this.world, this.game, 300, 177, this.camera);
        this.level.setSlingshot(slingshot);


        inputMultiplexer.addProcessor(hud.getStage()); // Handles UI input
        inputMultiplexer.addProcessor(slingshot); // Handles slingshot input
    }

    public void restartLevel() {
        dispose();
        this.game.setScreen(new GameScreen(new World(new Vector2(0, -10), true), game, new Level(this.level.LEVEL_NUMBER), false));
    }


    public void saveLevel() {
        String savePath = "level_%d.dat".formatted(this.level.LEVEL_NUMBER);
        LevelSave saveData = new LevelSave();
        saveData.score = this.score;

        // Iterate over all birds and save their state
        for (Bird bird : this.level.getBirds()) { // Assuming you have a list of birds
            if (!bird.isLauched()) {
                String type;
                if (bird instanceof Red) {
                    type = "Red";
                } else if (bird instanceof Chuck) {
                    type = "Chuck";
                } else {
                    type = "Bomb";
                }

                saveData.birds.add(new LevelSave.BirdData(
                    type,
                    bird.getPosition().x,
                    bird.getPosition().y
                ));
            }
        }

        for (Pig pig : this.level.getPigs()) { // Assuming you have a list of birds
            if (!pig.isDestroyed()) {
                String type;
                if (pig instanceof NormalPig) {
                    type = "Normal";
                } else if (pig instanceof KingPig) {
                    type = "King";
                } else {
                    type = "Helmet";
                }

                saveData.pigs.add(new LevelSave.PigData(
                    type,
                    pig.getBody().getPosition().x,
                    pig.getBody().getPosition().y,
                    pig.getBody().getAngle()
                ));
            }
        }

        for (Block block : this.level.getBlocks()) { // Assuming you have a list of birds
            if (!block.isDestroyed()) {
                String type;
                if (block instanceof Wood) {
                    type = "Wood";
                } else if (block instanceof Stone) {
                    type = "Stone";
                } else {
                    type = "Glass";
                }

                saveData.blocks.add(new LevelSave.BlockData(
                    type,
                    block.getBody().getPosition().x,
                    block.getBody().getPosition().y,
                    block.getRotation(),
                    0, 0
                ));
            }
        }

        // Serialize the saveData object to a file
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savePath))) {
            oos.writeObject(saveData);
            System.out.println("Level saved to: " + savePath);
        } catch (IOException e) {
            Core.logger.error("Failed to save level: " + savePath);
        }
    }

    public void loadLevel() {
        String savePath = "level_%d.dat".formatted(this.level.LEVEL_NUMBER);
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savePath))) {
            LevelSave saveData = (LevelSave) ois.readObject();

            Level level = new Level(this.level.LEVEL_NUMBER);
            World newWorld = new World(new Vector2(0, -10), true);

            // Set score
            level.setScore(saveData.score);

            // Restore birds' states
            for (int i = 0; i < saveData.birds.size(); i++) {
                LevelSave.BirdData birdData = saveData.birds.get(i);

                Bird bird;
                if (birdData.type.equals("Red")) {
                    bird = new Red(newWorld, game, birdData.x, birdData.y);
                } else if (birdData.type.equals("Chuck")) {
                    bird = new Chuck(newWorld, game, birdData.x, birdData.y);
                } else {
                    bird = new Bomb(newWorld, game, birdData.x, birdData.y);
                }

                bird.getBody().setTransform(birdData.x, birdData.y, 0);

                level.addBird(bird);
            }

            for (int i = 0; i < saveData.pigs.size(); i++) {
                LevelSave.PigData pigData = saveData.pigs.get(i);

                Pig pig;
                if (pigData.type.equals("Normal")) {
                    pig = new NormalPig(newWorld, game, pigData.x, pigData.y);
                } else if (pigData.type.equals("King")) {
                    pig = new KingPig(newWorld, game, pigData.x, pigData.y);
                } else {
                    pig = new HelmetPig(newWorld, game, pigData.x, pigData.y);
                }

                pig.getBody().setTransform(pigData.x, pigData.y, pigData.angle);

                level.addPig(pig);
            }

            for (int i = 0; i < saveData.blocks.size(); i++) {
                LevelSave.BlockData blockData = saveData.blocks.get(i);

                Block block;
                if (blockData.type.equals("Wood")) {
                    Core.logger.info("WOOD ROTATION: " + blockData.rotation);
                    block = new Wood(newWorld, game, blockData.x, blockData.y, blockData.rotation);
                } else if (blockData.type.equals("Stone")) {
                    block = new Stone(newWorld, game, blockData.x, blockData.y, blockData.rotation);
                } else {
                    block = new Glass(newWorld, game, blockData.x, blockData.y, blockData.rotation);
                }

                level.addBlock(block);
            }

            dispose();
            this.game.setScreen(new GameScreen(newWorld, game, level, true));

            System.out.println("Level loaded from: " + savePath);
        } catch (IOException | ClassNotFoundException e) {
            Core.logger.error("Failed to load level: " + savePath);
        }
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
        if (game.backgroundMusic.isPlaying()) {
            game.backgroundMusic.pause();
        }
        if (game.levelCompleteMusic.isPlaying()) {
            game.levelCompleteMusic.stop();
        }
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

        //debugRenderer.render(world, camera.combined);
        this.hud.getStage().draw();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.hud.getViewport().update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        this.world.dispose();
        this.hud.dispose();
    }

}
