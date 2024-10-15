package io.github.CosecSecCot.Utils.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CustomButton {
    private String text;
    private final Skin skin;
    private final TextButton button;
    private Vector2 position;
    private Vector2 size;

    public CustomButton(String text, Vector2 position, Vector2 size) {
        this.text = text;
        this.position = position;
        this.size = size;
        this.skin = new Skin(Gdx.files.internal("ui/testskin/testskin.json"));

        this.button = new TextButton(text, skin);
        this.button.setPosition(position.x, position.y);
        this.button.setSize(size.x, size.y);
    }

    public CustomButton(String text) {
        this.text = text;
        this.skin = new Skin(Gdx.files.internal("ui/testskin/testskin.json"));

        this.button = new TextButton(text, skin);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TextButton getButton() {
        return button;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getSize() {
        return size;
    }

    public void setSize(Vector2 size) {
        this.size = size;
    }
}
