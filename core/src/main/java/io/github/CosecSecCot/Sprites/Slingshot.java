package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public class Slingshot {
    Sprite back;
    Sprite front;
    private final Vector2 position;
    private Vector2 angle;
    private boolean isDragging;
    private Bird currentBird;
    private float launchVelocity;
    private final float MAX_PULL_DISTANCE = 0f;
    private final float MIN_PULL_DISTANCE = 0f;
    private float pullDistance;
    private Vector2 rubberBandEndCoordinate;
    private final World world;
    private Body body;

    public Slingshot(World world, Core game, int x_pos, int y_pos) {
        this.back = new Sprite(game.atlas.findRegion("bird_and_pigs"));
        this.front = new Sprite(game.atlas.findRegion("bird_and_pigs"));
        this.world = world;
        this.position = new Vector2(x_pos, y_pos);

        int spriteStartX = game.atlas.findRegion("bird_and_pigs").getRegionX();
        int spriteStartY = game.atlas.findRegion("bird_and_pigs").getRegionY();
        float xOffset = -11;
        float yOffset = 50;

        this.define();

        Vector2 spritePositionBack = new Vector2(0, 0);
        TextureRegion textureBack = new TextureRegion(back.getTexture(), spriteStartX + (int)spritePositionBack.x, spriteStartY + (int)spritePositionBack.y, 39, 200);
        this.back.setBounds(position.x+ xOffset, position.y-this.getSize().y/2+ yOffset, textureBack.getRegionWidth(), textureBack.getRegionHeight());
        this.back.setRegion(textureBack);

        Vector2 spritePositionFront = new Vector2(833, 0);
        TextureRegion textureFront = new TextureRegion(front.getTexture(), spriteStartX + (int)spritePositionFront.x, spriteStartY + (int)spritePositionFront.y, 43, 125);
        this.front.setBounds(position.x-27+ xOffset, position.y-this.getSize().y/2+85+ yOffset, textureFront.getRegionWidth(), textureFront.getRegionHeight());
        this.front.setRegion(textureFront);
    }

    private void define() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(position);
        this.body = world.createBody(bodyDef);

        assert body != null;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10, 50);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        this.body.createFixture(fixtureDef);
    }

    public void update(float deltaTime) {
//        back.setPosition(body.getPosition().x - back.getWidth()/2 + xOffset, body.getPosition().y - back.getHeight()/2 - yOffset);
    }

    public void draw(SpriteBatch batch) {
        this.back.draw(batch);
        this.front.draw(batch);
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getSize() {
        return new Vector2(66, 200);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getAngle() {
        return angle;
    }

    public Bird getCurrentBird() {
        return currentBird;
    }

    public float getLaunchVelocity() {
        return launchVelocity;
    }

    public float getPullDistance() {
        return pullDistance;
    }

    public Vector2 getRubberBandEndCoordinate() {
        return rubberBandEndCoordinate;
    }

    public void loadBird(Bird bird) {}

    public void destroy() {
        assert world != null;
        if (body != null) {
            this.world.destroyBody(body);
            this.body = null;
        }

        this.back.getTexture().dispose();
        this.front.getTexture().dispose();
    }

}
