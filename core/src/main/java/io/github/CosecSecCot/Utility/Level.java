package io.github.CosecSecCot.Utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.CosecSecCot.Sprites.Bird;
import io.github.CosecSecCot.Sprites.Block;
import io.github.CosecSecCot.Sprites.Pig;
import io.github.CosecSecCot.Sprites.Slingshot;

import java.util.ArrayList;

public class Level {
    public final int LEVEL_NUMBER;
    private final ArrayList<Bird> birds;
    private final ArrayList<Pig> pigs;
    private final ArrayList<Block> blocks;
    private Slingshot slingshot;
    private boolean isComplete;

    public Level(int levelNumber) {
        this.LEVEL_NUMBER = levelNumber;
        this.birds = new ArrayList<>();
        this.pigs = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.isComplete = false;
    }

    /**
     * Runs every frame.
     *
     * @param deltaTime Delta time.
     */
    public void update(float deltaTime) {
        if (birds.isEmpty() || pigs.isEmpty()) {
            this.isComplete = true;
            return;
        }

        for (Bird bird : birds) {
            bird.update(deltaTime);
        }
        for (Pig pig : pigs) {
            pig.update(deltaTime);
        }
        for (Block block : blocks) {
            block.update(deltaTime);
        }
        slingshot.update(deltaTime);
    }

    /**
     * Draws all the entities in the level.
     *
     * <p>
     * {@code SpriteBatch.begin()} must be called before invoking this function.
     * </p>
     *
     * @param batch {@link SpriteBatch} of the game.
     */
    public void draw(SpriteBatch batch) {
        for (Pig pig : pigs) {
            pig.draw(batch);
        }
        for (Block block : blocks) {
            block.draw(batch);
        }
        for (Bird bird : birds) {
            bird.draw(batch);
        }
        slingshot.draw(batch);
    }

    public void applyAllRotations() {
        for (Block block : blocks) {
            block.applyRotation();
        }
    }

    public ArrayList<Bird> getBirds() {
        return birds;
    }

    public ArrayList<Pig> getPigs() {
        return pigs;
    }

    public ArrayList<Block> getBlocks() {
        return blocks;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void addBird(Bird bird) {
        birds.add(bird);
    }

    public void addPig(Pig pig) {
        pigs.add(pig);
    }

    public void addBlock(Block block) {
        blocks.add(block);
    }

    public void setSlingshot(Slingshot slingshot) {
        this.slingshot = slingshot;
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    public void destroy() {
        for (Bird bird : birds) {
            bird.destroy();
        }
        birds.clear();
        for (Pig pig : pigs) {
            pig.destroy();
        }
        birds.clear();
        for (Block block : blocks) {
            block.destroy();
        }
        blocks.clear();
        slingshot.destroy();
    }
}

