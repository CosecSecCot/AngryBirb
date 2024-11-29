package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public class Bomb extends Bird {
    public Bomb(World world, Core game, float x_pos, float y_pos) {
        super(world, game, "bird_and_pigs", x_pos, y_pos, 409, 725, 61, 81, 0, -10, 25);
        this.damage = 12;
    }

    @Override
    protected void define() {
        super.define();

        assert body != null;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(30);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 3f;
        fixtureDef.friction = 10f;
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setOrigin(this.getSize().x / 2, 30);
    }
}
