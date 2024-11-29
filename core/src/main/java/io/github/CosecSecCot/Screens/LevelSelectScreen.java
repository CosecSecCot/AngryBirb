package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Utility.Level;

import java.util.ArrayList;

/**
 * The level select screen, implements {@link Screen}
 *
 * @see Core
 */
public class LevelSelectScreen implements Screen {
    private final Core game;
    private final Viewport viewport;
    private final Stage stage;

    public LevelSelectScreen(Core game) {
        this.game = game;
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(this.viewport, game.batch);

        ArrayList<TextButton> levelButtons = new ArrayList<>();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label headingLabel = new Label("Select Level", game.skin, "title");

        table.add(headingLabel).expandX().colspan(3).center().padTop(30).padBottom(50);
        table.row();
        table.padLeft(256).padRight(256);

        ImageButton backButton = new ImageButton(game.skin, "back_button");
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        for (int i = 1; i <= 3; i++) {
            TextButton button = new TextButton(String.format("%d", i), game.skin, "level_0_star");
            int levelNumber = i;
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(new World(new Vector2(0, -10), true), game, new Level(levelNumber), false));
                }
            });
            levelButtons.add(button);
        }

        for (TextButton levelButton : levelButtons) {
            table.add(levelButton);
        }

        this.stage.addActor(table);
//        table.setDebug(true);
        this.stage.addActor(backButton);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float deltaTime) {
        ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1f);
        this.game.batch.begin();
        this.game.batch.draw(this.game.atlas.findRegion("background", 1), 0, 0);
        this.game.batch.end();
        this.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }

}
