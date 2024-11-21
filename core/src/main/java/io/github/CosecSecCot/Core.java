package io.github.CosecSecCot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.CosecSecCot.Screens.MainMenuScreen;
import io.github.CosecSecCot.Utility.Logger;

public class Core extends Game {
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    public static boolean paused = false;
    public static Logger logger;

    public SpriteBatch batch;
    public Skin skin;
    public TextureAtlas atlas;

    public static Vector2 convertToWorldCoordinates(Camera camera, int screenX, int screenY) {
        Vector3 worldCoordinates = new Vector3(screenX, screenY, 0);
        camera.unproject(worldCoordinates);
        return new Vector2(worldCoordinates.x, worldCoordinates.y);
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("ui/angrybirb/angrybirb.json"));
        atlas = new TextureAtlas("angrybirb_sprites.atlas");

        logger = new Logger(true);

        setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        skin.dispose();
        atlas.dispose();
    }

}
