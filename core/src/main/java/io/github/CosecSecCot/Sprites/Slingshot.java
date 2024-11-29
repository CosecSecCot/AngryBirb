package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public class Slingshot extends InputAdapter {
    private final Sprite back;
    private final Sprite front;
    private final Camera camera;
    private final Vector2 position;
    private Vector2 angle;
    private boolean isDragging;
    private Bird currentBird;
    private float launchVelocity;
    private final float MAX_PULL_DISTANCE = 100f;
    private final float MIN_PULL_DISTANCE = 40f;
    private float pullDistance;
    private Vector2 rubberBandEndCoordinate;
    private final World world;
    private Body body;

    public Slingshot(World world, Core game, float x_pos, float y_pos, Camera camera) {
        this.back = new Sprite(game.atlas.findRegion("bird_and_pigs"));
        this.front = new Sprite(game.atlas.findRegion("bird_and_pigs"));
        this.world = world;
        this.camera = camera;
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

    /**
     * Runs every frame.
     *
     * @param deltaTime Delta time.
     */
    public void update(float deltaTime) {
        if (currentBird != null)
            currentBird.update(deltaTime);
    }

    /**
     * Draws the slingshot on the screen.
     *
     * <p>
     * {@code SpriteBatch.begin()} must be called before invoking this function.
     * </p>
     *
     * @param batch {@link SpriteBatch} of the game.
     */
    public void draw(SpriteBatch batch) {
        this.back.draw(batch);

        if (currentBird != null) {
            currentBird.draw(batch);
        }

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

    public Vector2 getBirdPlacementPosition() {
        // Calculate the bird's resting position between the poles
        return new Vector2(position.x, position.y + getSize().y / 2);
    }

    public void setCurrentBird(Bird currentBird) {
        this.currentBird = currentBird;
    }

    public void loadBird(Bird bird) {
        this.currentBird = bird;
        bird.getBody().setActive(false);

        Vector2 bodyPosition = bird.getBody().getPosition();
        bird.setPosition(bodyPosition.x - bird.getSize().x / 2, bodyPosition.y - bird.getSize().y / 2);
    }

    private boolean isNearSlingshot(float touchX, float touchY) {
        float slingshotX = position.x;
        float slingshotY = position.y;
        float slingshotWidth = getSize().x;
        float slingshotHeight = getSize().y;

        return touchX > (slingshotX - 200) && touchX < (slingshotX + slingshotWidth + 200)
            && touchY > (slingshotY - 200) && touchY < (slingshotY + slingshotHeight + 200);
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (currentBird == null) return false;

        Core.logger.info("Touched Down");

        // Convert screen coordinates to world coordinates
        Vector2 touchPos = Core.convertToWorldCoordinates(camera, screenX, screenY);
        Core.logger.info("Touch Position: " + touchPos);

        if (isNearSlingshot(touchPos.x, touchPos.y)) {
            Core.logger.info("Touched Near Slingshot");
            isDragging = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        Core.logger.info("isDragging: " + isDragging);
        if (!isDragging || currentBird == null) return false;

        Core.logger.info("touch Dragged");

        // Convert screen coordinates to world coordinates
        Vector2 touchPos = Core.convertToWorldCoordinates(camera, screenX, screenY);
        Core.logger.info(String.format("Touch Dragged at: %s", touchPos));

        // Calculate drag direction and distance
        Vector2 dragVector = new Vector2(touchPos.x - getBirdPlacementPosition().x, touchPos.y - getBirdPlacementPosition().y);
        if (dragVector.len() > MAX_PULL_DISTANCE) {
            dragVector.setLength(MAX_PULL_DISTANCE); // Limit to MAX_PULL_DISTANCE
        }

        // Update bird's position
        Vector2 birdPosition = getBirdPlacementPosition().cpy().add(dragVector);
        currentBird.setPosition(birdPosition.x - currentBird.getSize().x / 2, birdPosition.y - currentBird.getSize().y / 2);
        currentBird.getBody().setTransform(birdPosition, 0); // Sync Box2D body

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (currentBird == null) return false;
        if (isDragging) {
            isDragging = false;
        }

        // Calculate the pull distance and direction
        Vector2 birdPosition = currentBird.getBody().getPosition();
        Vector2 slingshotPosition = getBirdPlacementPosition();
        Vector2 pullVector = slingshotPosition.cpy().sub(birdPosition); // Direction of pull

        float pullDistance = pullVector.len();

        if (pullDistance < MIN_PULL_DISTANCE) {
            // If the pull distance is less than the minimum, reset bird to the original position
            Core.logger.info("Pull too short, resetting bird to original position.");
            birdPosition.set(slingshotPosition);
            currentBird.setPosition(
                birdPosition.x - currentBird.getSize().x / 2,
                birdPosition.y - currentBird.getSize().y / 2
            );
            currentBird.getBody().setTransform(birdPosition, 0); // Sync Box2D body
        } else {
            // If the pull distance is sufficient, launch the bird
            Core.logger.info("Launching bird!");

            currentBird.getBody().setActive(true);

            Core.logger.info("Body position: " + currentBird.getBody().getPosition());

            // Normalize the pull vector to get the direction, then scale it for impulse
            pullVector.nor().scl(currentBird.getBody().getMass() * MathUtils.clamp(pullDistance, MIN_PULL_DISTANCE, MAX_PULL_DISTANCE));

            // Apply an impulse to the bird's body in the direction of the pull
            currentBird.getBody().applyLinearImpulse(
                pullVector,
                currentBird.getBody().getWorldCenter(),
                true
            );

            Core.logger.info("Impulse applied: " + pullVector);
        }

        return true;
    }

    /**
     * Checks if the current bird has been launched successfully.
     *
     * @return True if the bird is launched, false otherwise.
     */
    public boolean birdHasBeenLaunchedSuccessfully() {
        if (currentBird == null || currentBird.isDestroyed()) return false;

        return currentBird.getBody().getPosition().dst(getBirdPlacementPosition()) > MAX_PULL_DISTANCE + 10;
    }

    public void destroy() {
        if (world != null && body != null) {
            this.world.destroyBody(body);
            this.body = null;
        }
    }

}
