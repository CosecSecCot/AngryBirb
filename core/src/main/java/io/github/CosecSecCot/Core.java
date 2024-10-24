package io.github.CosecSecCot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import io.github.CosecSecCot.Screens.MainMenuScreen;

public class Core extends Game {
    public static final int V_WIDTH = 1280;
    public static final int V_HEIGHT = 720;
    public SpriteBatch batch;
    public static boolean paused = false;

    public Skin skin;
    public Texture background_img;

    @Override
    public void create() {
        batch = new SpriteBatch();

        skin = new Skin(Gdx.files.internal("ui/angrybirb/angrybirb.json"));
        background_img = new Texture(Gdx.files.internal("BACKGROUND_1.png"));

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
        background_img.dispose();
    }

}
