package io.github.CosecSecCot.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.CosecSecCot.Core;

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

    /** @param spriteBatch The sprite batch for the game. */
    public GameScreenHUD(SpriteBatch spriteBatch, BitmapFont font) {
        viewport = new FitViewport(Core.V_WIDTH, Core.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        score = new Label(String.format("%06d", scoreValue), new Label.LabelStyle(font, Color.WHITE));

        table.add(score).expandX().padTop(10);

        stage.addActor(table);

    }

    public void setScore(Integer score) {
        this.scoreValue = score;
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
