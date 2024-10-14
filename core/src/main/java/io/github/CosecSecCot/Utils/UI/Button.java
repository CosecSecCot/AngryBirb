package io.github.CosecSecCot.Utils.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Button {
    private String text;
    private Skin skin;
    private TextButton button;
    private Vector2 position;
    private Vector2 size;

    public Button(String text, Vector2 position, Vector2 size) {
        this.text = text;
        this.position = position;
        this.size = size;
        this.skin = new Skin(Gdx.files.internal("ui/testskin/testskin.json"));

        this.button = new TextButton(text, skin);
        this.button.setPosition(position.x, position.y);
        this.button.setSize(size.x, size.y);
    }

    public String getText() {
        return text;
    }

    public TextButton getButton() {
        return button;
    }
}
