package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import io.github.CosecSecCot.Core;

public class KingPig extends Pig {
    public KingPig(World world, Core game, float x_pos, float y_pos) {
        super(world, game, "bird_and_pigs", x_pos, y_pos, 41, 2, 126, 152, 0, -20, 12);
        this.points = 1000;
    }

    @Override
    protected void define() {
        super.define();

        assert body != null;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(55);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }
}
