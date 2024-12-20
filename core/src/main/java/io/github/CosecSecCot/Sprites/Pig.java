package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public abstract class Pig extends Entity {
    protected int points;

    /**
     * Constructor for sample pig. Only to be used for testing!
     *
     * @param health health of the pig.
     */
    public Pig(double health) {
        super(health);
    }

    public Pig(World world, Core game, String sprite_region, float x_pos, float y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health) {
        super(world, game, sprite_region, x_pos, y_pos, sprite_x_pos, sprite_y_pos, width, height, xOffset, yOffset, health);
        body.setUserData(this);
    }

    public int getPoints() {
        return this.points;
    }

    @Override
    protected void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getPosition());
        body = world.createBody(bodyDef);
    }
}
