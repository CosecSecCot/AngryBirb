package io.github.CosecSecCot.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Utils.UI.CustomButton;

import java.util.ArrayList;

/**
 * The level select screen, implements {@link Screen}
 *
 * @see Core
 */
public class LevelSelectScreen implements Screen {
    private final Core game;
    private final Viewport viewport;
    private Stage stage;
    private final ArrayList<CustomButton> levelButtons;

    public LevelSelectScreen(Core game) {
        this.game = game;
        this.stage = new Stage();
        this.viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        this.stage = new Stage(this.viewport);
        this.levelButtons = new ArrayList<>();

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label headingLabel = new Label("Select Level", new Skin(Gdx.files.internal("ui/testskin/testskin.json")), "title");

        table.add(headingLabel).expandX().colspan(3).center().padTop(30).padBottom(30);
        table.row();

        CustomButton backButton = new CustomButton("Back", new Vector2(10, 10), new Vector2(100, 50));
        backButton.getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        for (int i = 1; i <= 3; i++) {
            CustomButton button = new CustomButton(String.format("%d", i));
            button.getButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new GameScreen(game));
                }
            });
            levelButtons.add(button);
        }

        for (CustomButton levelButton : levelButtons) {
            table.add(levelButton.getButton()).expandX().width(50).height(50);
        }

        stage.addActor(table);
//        table.setDebug(true);
        stage.addActor(backButton.getButton());
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void show() {

    }

    /**
     * Handles Input
     *
     * @param deltaTime Delta time.
     */
    public void handleInput(float deltaTime) {
    }

    /**
     * Runs every frame to update the contents on the screen.
     *
     * @param deltaTime Delta time.
     */
    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    @Override
    public void render(float deltaTime) {
        update(deltaTime);

        ScreenUtils.clear(0.7f, 0.7f, 0.7f, 1f);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
        stage.dispose();
    }

}
