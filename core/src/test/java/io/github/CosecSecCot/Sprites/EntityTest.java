package io.github.CosecSecCot.Sprites;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {
    Entity testEntity;

    @Before
    public void setUp() {
        testEntity = new Entity(100) {
            @Override
            protected void define() {}
        };
    }

    @Test
    public void testTakeDamage() {
        double initialHealth = testEntity.getCurrentHealth();

        testEntity.takeDamage(30);

        assertEquals(initialHealth - 30, testEntity.getCurrentHealth(), 0.01);
        assertFalse(testEntity.isDestroyed());

        testEntity.takeDamage(70);
        assertEquals(0, testEntity.getCurrentHealth(), 0.01);
        assertTrue(testEntity.isDestroyed());
    }

    @Test
    public void testMarkForDestruction() {
        assertFalse(testEntity.isDestroyed());
        testEntity.markForDestruction();
        assertTrue(testEntity.isDestroyed());
    }
}
