package io.github.CosecSecCot.Utility;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.ContactListener;
import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Sprites.Bird;
import io.github.CosecSecCot.Sprites.Block;
import io.github.CosecSecCot.Sprites.Entity;
import io.github.CosecSecCot.Sprites.Pig;

public class CollisionHandler implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Object userDataA = contact.getFixtureA().getBody().getUserData();
        Object userDataB = contact.getFixtureB().getBody().getUserData();

        handleCollision(userDataA, userDataB);
        handleCollision(userDataB, userDataA);
    }

    @Override
    public void endContact(Contact contact) {}

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {}

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {}

    public static void handleCollision(Object objectA, Object objectB) {
        if (objectA instanceof Bird bird) {
            if (objectB instanceof Pig pig) {
                applyCollisionDamage(bird, pig, 10, bird.getDamage());
            } else if (objectB instanceof Block block) {
                applyCollisionDamage(bird, block, 10, bird.getDamage());
            }
        } else if (objectA instanceof Block block) {
            if (objectB instanceof Pig pig) {
                applyCollisionDamage(pig, block, block.getDamage(), 5);
            }
        }
    }

    public static void applyCollisionDamage(Entity entityA, Entity entityB, double damageToA, double damageToB) {
        entityA.takeDamage(damageToA);
        entityB.takeDamage(damageToB);

        if (Core.logger != null) {
            Core.logger.info(String.format("%s HP: %f/%f", entityA.getClass().getSimpleName(), entityA.getCurrentHealth(), entityA.getMaxHealth()));
            Core.logger.info(String.format("%s HP: %f/%f", entityB.getClass().getSimpleName(), entityB.getCurrentHealth(), entityB.getMaxHealth()));
        }
    }
}
