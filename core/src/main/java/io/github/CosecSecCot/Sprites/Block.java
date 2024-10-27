package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public abstract class Block extends Entity {
    protected double damage;
    protected int points;
    protected double resistance;
    protected double hitsToDestroy;
    protected float rotationAngle = 0;
    // TODO: state

    public Block(World world, Core game, String sprite_region, int x_pos, int y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health) {
        super(world, game, sprite_region, x_pos, y_pos, sprite_x_pos, sprite_y_pos, width, height, xOffset, yOffset, health);
    }

    public Block(World world, Core game, String sprite_region, int x_pos, int y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health, float rotationAngle) {
        super(world, game, sprite_region, x_pos, y_pos, sprite_x_pos, sprite_y_pos, width, height, xOffset, yOffset, health);
        this.rotationAngle = rotationAngle;
    }

    public void applyRotation() {
        this.getBody().setTransform(this.getBody().getPosition(), (float)Math.toRadians(rotationAngle));
    }

    public void dealDamage() {}

    @Override
    protected void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getPosition());
        this.body = world.createBody(bodyDef);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.setOrigin(this.getSize().x / 2, this.getSize().y / 2);
        this.setRotation(this.getBody().getAngle() * MathUtils.radiansToDegrees);
    }
}
