package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import io.github.CosecSecCot.Core;

public class HelmetPig extends Pig {
    public HelmetPig(World world, Core game, int x_pos, int y_pos) {
        super(world, game, "bird_and_pigs", x_pos, y_pos, 493, 446, 93, 83, 0, 0, 12);
    }

    @Override
    protected void define() {
        super.define();

        assert body != null;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(41);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }
}
