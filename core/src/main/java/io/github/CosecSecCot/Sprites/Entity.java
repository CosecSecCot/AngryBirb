package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public abstract class Entity extends Sprite {
    protected final Vector2 position;
    private final Vector2 size;
    private double health;
    protected final World world;
    protected Body body;
    private float xOffset;
    private float yOffset;

    public Entity(World world, Core game, String sprite_region, int x_pos, int y_pos, int sprite_x_pos, int sprite_y_pos, int width, int height, float xOffset, float yOffset, double health) {
        super(game.atlas.findRegion(sprite_region));
        int spriteStartX = game.atlas.findRegion(sprite_region).getRegionX();
        int spriteStartY = game.atlas.findRegion(sprite_region).getRegionY();
        this.world = world;
        this.position = new Vector2(x_pos, y_pos);
        this.size = new Vector2(width, height);
        this.health = health;
        this.xOffset = xOffset;
        this.yOffset = yOffset;

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
        if (health == 0)
            destroy();
    }

    public void takeDamage(float amount) {
        assert amount > 0;
        this.health = Math.max(0, this.health - amount);
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

    public void setXOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public void setYOffset(float yOffset) {
        this.yOffset = yOffset;
    }

    public void destroy() {
        assert this.world != null;
        if (this.body != null) {
            this.world.destroyBody(body);
            this.body = null;
        }

        this.getTexture().dispose();
    }
}
