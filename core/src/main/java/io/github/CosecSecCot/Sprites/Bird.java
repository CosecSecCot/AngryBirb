package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import io.github.CosecSecCot.Core;

public abstract class Bird extends Entity {
    protected double damage;
    private float destroyTimer = 0f;
    private boolean isFainted = false;
    private static final float TIME_TO_DESTROY = 6f;
    private float fadeTimer = 0f;
    private static final float FADE_AWAY_TIME = 1f;

    public Bird(World world, Core game, String sprite_region, float x_pos, float y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health) {
        super(world, game, sprite_region, x_pos, y_pos, sprite_x_pos, sprite_y_pos, width, height, xOffset, yOffset, health);
        body.setUserData(this);
    }

    @Override
    protected void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(this.getPosition());
        this.body = world.createBody(bodyDef);
    }

    public void startDestroyTimer() {
        if (this.isFainted) return;

        this.isFainted = true;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public void update(float deltaTime) {
        if (!isDestroyed()) {
            super.update(deltaTime);
            this.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        }

        if (this.isFainted) {
//            Core.logger.info(String.format("%s timer: %f", this.getClass().getSimpleName(), destroyTimer));
            destroyTimer += deltaTime;
            if (destroyTimer > TIME_TO_DESTROY) {
                this.getBody().setActive(false);
                fadeTimer += deltaTime;
                float alpha = Math.max(0, 1 - (fadeTimer / FADE_AWAY_TIME));
                this.setColor(1, 1, 1, alpha); // Fade out as timer progresses
                if (fadeTimer > FADE_AWAY_TIME) {
                    this.markForDestruction();
                }
            }
        }
    }
}
