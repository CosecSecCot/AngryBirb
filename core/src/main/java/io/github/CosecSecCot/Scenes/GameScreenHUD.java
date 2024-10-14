package io.github.CosecSecCot.Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;
import io.github.CosecSecCot.Screens.MainMenuScreen;
import io.github.CosecSecCot.Utils.UI.Button;

import java.util.ArrayList;

/**
 * HUD for the game.
 *
 * @author CosecSecCot
 */
public class GameScreenHUD implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer scoreValue;
    private Label score;

    private ArrayList<Button> buttons;

    /** @param spriteBatch The sprite batch for the game. */
    public GameScreenHUD(Core game, SpriteBatch spriteBatch, BitmapFont font) {
        this.scoreValue = 0;
        viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);
        buttons = new ArrayList<>();

        score = new Label(String.format("SCORE: %06d", scoreValue), new Skin(Gdx.files.internal("ui/testskin/testskin.json")));
        score.setPosition(stage.getWidth() - score.getWidth() - 10, stage.getHeight() - score.getHeight() - 10);

        buttons.add(new Button("Back", new Vector2(10, 10), new Vector2(100, 50)));
        buttons.get(0).getButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        stage.addActor(score);
        for (Button button : buttons) {
            stage.addActor(button.getButton());
        }
        Gdx.input.setInputProcessor(this.stage);
    }

    public void setScore(Integer score) {
        this.scoreValue = score;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
