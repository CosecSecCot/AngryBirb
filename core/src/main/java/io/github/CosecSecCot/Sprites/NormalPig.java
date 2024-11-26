package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public class NormalPig extends Pig {
    public NormalPig(World world, Core game, int x_pos, int y_pos) {
        super(world, game, "bird_and_pigs", x_pos, y_pos, 732, 855, 48, 48, 0, -2, 10);
    }

    @Override
    protected void define() {
        super.define();

        assert body != null;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(20);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.5f;
        fixtureDef.friction = 1.5f;
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }
}
