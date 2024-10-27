package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import io.github.CosecSecCot.Core;

public abstract class Bird extends Entity {
    protected double damage;
    protected double speed;
    protected double hitsToDestroy;
    protected boolean isLoaded;

    public Bird(World world, Core game, String sprite_region, int x_pos, int y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health) {
        super(world, game, sprite_region, x_pos, y_pos, sprite_x_pos, sprite_y_pos, width, height, xOffset, yOffset, health);
    }

    @Override
    protected void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getPosition());
        this.body = world.createBody(bodyDef);
    }

    public boolean isLoaded() { return isLoaded; }
    public void setLoaded(boolean loaded) { this.isLoaded = loaded; }

    public void dealDamage() {}

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
    }
}
