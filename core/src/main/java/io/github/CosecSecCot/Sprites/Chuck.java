package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public class Chuck extends Bird {
    public Chuck(World world, Core game, int x_pos, int y_pos) {
        super(world, game, "bird_and_pigs", x_pos, y_pos, 668, 879, 58, 54, -3, 0, 20);
        this.damage = 10;
    }

    @Override
    protected void define() {
        super.define();

        assert body != null;
        PolygonShape shape = new PolygonShape();
        Vector2[] vertices = new Vector2[3];
        vertices[0] = new Vector2(0, 15);
        vertices[1] = new Vector2(-26, -26);
        vertices[2] = new Vector2(26, -26);
        shape.set(vertices);
//        CircleShape shape = new CircleShape();
//        shape.setRadius(20);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2f;
        fixtureDef.friction = 10f;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setOrigin(this.getSize().x / 2, this.getSize().y / 2);
    }
}
