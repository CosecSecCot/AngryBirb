package io.github.CosecSecCot.Utility;

import io.github.CosecSecCot.Sprites.Bird;
import io.github.CosecSecCot.Sprites.Block;
import io.github.CosecSecCot.Sprites.Pig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CollisionHandlerTest {
    Bird bird;
    Pig pig;
    Block block;

    @Before
    public void setUp() {
        bird = new Bird(10, 20) {
            @Override
            protected void define() {}
        };

        pig = new Pig(10) {
            @Override
            protected void define() {
            }
        };

        block = new Block(10, 10) {
            @Override
            protected void define() {
            }
        };
    }

    @Test
    public void handleCollision() {
        double initialBirdHealth = bird.getCurrentHealth();

        // Bird collides with pig
        CollisionHandler.handleCollision(bird, pig);

        assertEquals(initialBirdHealth - 10, bird.getCurrentHealth(), 0.01);
        assertFalse(bird.isDestroyed());
        assertTrue(pig.isDestroyed());

        // Bird collides with block
        CollisionHandler.handleCollision(bird, block);

        assertTrue(bird.isDestroyed());
        assertTrue(block.isDestroyed());

    }
}
