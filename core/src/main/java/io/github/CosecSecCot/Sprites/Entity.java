package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public abstract class Entity extends Sprite {
    protected final Vector2 position;
    private final Vector2 size;
    private final double health;
    private double currentHealth;
    protected final World world;
    protected Body body;
    private float xOffset;
    private float yOffset;
    private boolean isDestroyed;

    public Entity(World world, Core game, String sprite_region, float x_pos, float y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health) {
        super(game.atlas.findRegion(sprite_region));
        int spriteStartX = game.atlas.findRegion(sprite_region).getRegionX();
        int spriteStartY = game.atlas.findRegion(sprite_region).getRegionY();
        this.world = world;
        this.position = new Vector2(x_pos, y_pos);
        this.size = new Vector2(width, height);
        this.health = health;
        this.currentHealth = health;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.isDestroyed = false;

        this.define();

        TextureRegion texture = new TextureRegion(this.getTexture(), spriteStartX + sprite_x_pos, spriteStartY + sprite_y_pos, width, height);
        this.setBounds(0, 0, width, height);
        this.setRegion(texture);
    }

    /**
     * Used to define the {@link Body} of the entity.
     */
    abstract protected void define();

    /**
     * Runs every frame.
     *
     * @param deltaTime Delta time.
     */
    public void update(float deltaTime) {
        this.setPosition(body.getPosition().x - this.getWidth()/2 + xOffset, body.getPosition().y - this.getHeight()/2 - yOffset);
    }

    public void takeDamage(double amount) {
        assert amount > 0;
        this.currentHealth = Math.max(0, this.currentHealth - amount);
        if (this.currentHealth <= 0)
            this.markForDestruction();
    }

    public double getCurrentHealth() {
        return currentHealth;
    }

    public double getMaxHealth() {
        return health;
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public void markForDestruction() {
        if (!isDestroyed) {
            isDestroyed = true;
        }
    }

    public void destroy() {
        if (this.world != null && this.body != null) {
            this.world.destroyBody(body);
            this.body = null;
            this.isDestroyed = true;
        }
    }
}
