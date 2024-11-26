package io.github.CosecSecCot.Sprites;

import com.badlogic.gdx.physics.box2d.*;
import io.github.CosecSecCot.Core;

public class Red extends Bird {
    public Red(World world, Core game, int x_pos, int y_pos) {
        super(world, game, "bird_and_pigs", x_pos, y_pos, 902, 797, 48, 48, -3, -2, 20);
        this.damage = 10;
    }

    @Override
    protected void define() {
        super.define();

        assert body != null;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(18);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 2f;
        fixtureDef.friction = 10f;
        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        setOrigin(this.getSize().x / 2, this.getSize().y / 2);
    }
}
