package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import io.github.CosecSecCot.Core;

public class Glass extends Block {
    public Glass(World world, Core game, int x_pos, int y_pos) {
        super(world, game, "blocks", x_pos, y_pos, 660, 714, 167, 20, 0, 0, 5);
        this.points = 25;
    }

    public Glass(World world, Core game, int x_pos, int y_pos, float rotationAngle) {
        super(world, game, "blocks", x_pos, y_pos, 660, 714, 167, 20, 0, 0, 5, rotationAngle);
        this.points = 25;
    }

    @Override
    protected void define() {
        super.define();

        assert this.body != null;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(this.getSize().x/2, this.getSize().y/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.5f;
        fixtureDef.friction = 5f;
        fixtureDef.shape = shape;
        this.getBody().createFixture(fixtureDef);
    }
}
