package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public abstract class Block extends Entity {
    protected double damage = 10;
    protected int points;
    protected float rotationAngle;

    public Block(World world, Core game, String sprite_region, float x_pos, float y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health) {
        super(world, game, sprite_region, x_pos, y_pos, sprite_x_pos, sprite_y_pos, width, height, xOffset, yOffset, health);
        this.rotationAngle = 0f;
        body.setUserData(this);
    }

    public Block(World world, Core game, String sprite_region, float x_pos, float y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health, float rotationAngle) {
        super(world, game, sprite_region, x_pos, y_pos, sprite_x_pos, sprite_y_pos, width, height, xOffset, yOffset, health);
        this.rotationAngle = rotationAngle;
        body.setUserData(this);
    }

    public void applyRotation() {
        this.getBody().setTransform(this.getBody().getPosition(), (float)Math.toRadians(rotationAngle));
    }

    public double getDamage() {
        return damage;
    }

    @Override
    protected void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getPosition());
        this.body = world.createBody(bodyDef);
    }

    public int getPoints() {
        return this.points;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.setOrigin(this.getSize().x / 2, this.getSize().y / 2);
        this.setRotation(this.getBody().getAngle() * MathUtils.radiansToDegrees);
    }
}
