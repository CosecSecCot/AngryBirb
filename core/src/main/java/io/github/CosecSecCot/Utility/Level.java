package io.github.CosecSecCot.Utility;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Sprites.*;

import java.util.ArrayList;

public class Level {
    public final int LEVEL_NUMBER;
    private final ArrayList<Bird> birds;
    private final ArrayList<Pig> pigs;
    private final ArrayList<Block> blocks;
    private final ArrayList<Entity> entitiesToDestroy;
    private Slingshot slingshot;
    private int score;
    private boolean isComplete;
    private boolean win;
    private int currentBirdIndex;
    private double nextBirdTimer = 0;
    private static final float TIME_FOR_NEXT_BIRD = 0.75f;

    public Level(int levelNumber) {
        this.LEVEL_NUMBER = levelNumber;
        this.birds = new ArrayList<>();
        this.pigs = new ArrayList<>();
        this.blocks = new ArrayList<>();
        this.entitiesToDestroy = new ArrayList<>();
        this.score = 0;
        this.isComplete = false;
        this.win = false;
        this.currentBirdIndex = -1;
    }

    /**
     * Runs every frame.
     *
     * @param deltaTime Delta time.
     */
    public void update(float deltaTime) {
        this.processDestructionQueue();

        boolean allBirdsDestroyed = true;
        for (Bird bird : birds) allBirdsDestroyed &= bird.isDestroyed();

        if (pigs.isEmpty() || allBirdsDestroyed) {
            this.isComplete = true;
            this.win = pigs.isEmpty();
        }

        for (Pig pig : pigs) pig.update(deltaTime);
        for (Block block : blocks) block.update(deltaTime);
        for (Bird bird : birds)
            if (!bird.isDestroyed())
                bird.update(deltaTime);

        slingshot.update(deltaTime);
        if (slingshot.birdHasBeenLaunchedSuccessfully()) {
            nextBirdTimer += deltaTime;
            Core.logger.info("Started Bird destroy timer!");
            slingshot.getCurrentBird().startDestroyTimer();
            Core.logger.info("Bird successfully launched. Loading next bird...");
            Core.logger.info("Birds left: " + birds.size());
            if (nextBirdTimer > TIME_FOR_NEXT_BIRD) {
                placeBirdOnSlingshot();
                nextBirdTimer = 0;
            }
        }
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
        for (Pig pig : pigs) pig.draw(batch);
        for (Block block : blocks) block.draw(batch);
        for (Bird bird : birds)
            if (!bird.isDestroyed())
                bird.draw(batch);

        slingshot.draw(batch);
    }

    public void applyAllRotations() {
        for (Block block : blocks) {
            block.applyRotation();
        }
    }

    /**
     * Places the next bird in the array on the slingshot.
     */
    public void placeBirdOnSlingshot() {
        if (slingshot == null) return;

        currentBirdIndex++; // Move to the next bird
        if (currentBirdIndex < birds.size()) {
            Bird bird = birds.get(currentBirdIndex); // Get the current bird by index
            Vector2 birdPlacementPosition = slingshot.getBirdPlacementPosition();
            bird.getBody().setTransform(birdPlacementPosition, 0); // Set body's position to slingshot
            slingshot.loadBird(bird);
        } else {
            // No more birds left to place
            currentBirdIndex = -1;
            slingshot.setCurrentBird(null);
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public boolean won() {
        return win;
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
        placeBirdOnSlingshot();
    }

    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    private void processDestructionQueue() {
        for (Pig pig : pigs)
            if (pig.isDestroyed()) {
                entitiesToDestroy.add(pig);
                this.score += pig.getPoints();
            }
        for (Block block : blocks)
            if (block.isDestroyed()) {
                entitiesToDestroy.add(block);
                this.score += block.getPoints();
            }
        for (Bird bird : birds) if (bird.isDestroyed()) entitiesToDestroy.add(bird);

        for (Entity entity : entitiesToDestroy) {
            entity.destroy();
        }

        // Remove from level
        for (int i = blocks.size() - 1; i >= 0; i--) if (blocks.get(i).isDestroyed()) blocks.remove(i);
        for (int i = pigs.size() - 1; i >= 0; i--) if (pigs.get(i).isDestroyed()) pigs.remove(i);

        // reset queue
        entitiesToDestroy.clear();
    }
}

