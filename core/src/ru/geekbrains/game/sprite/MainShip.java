package ru.geekbrains.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.security.Key;

import ru.geekbrains.game.base.Sprite;
import ru.geekbrains.game.math.Rect;

public class MainShip extends Sprite {
    private final Vector2 v;
    private final float SPD = 0.008f;
    private Vector2 touch;
    private int pointer = -1;
    private int key;
    private Rect worldBounds;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 2);
        setHeightProportion(0.25F);
        v = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(v.isZero() || this.pointer == pointer) {
            this.pointer = pointer;
            this.touch = touch;
            if(touch.x < pos.x) v.x = -SPD;
            if(touch.x > pos.x) v.x = SPD;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(this.pointer == pointer) {
            this.pointer = -1;
            v.setZero();
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(v.isZero()) {
            if(keycode == Input.Keys.LEFT) v.x = -SPD;
            if(keycode == Input.Keys.RIGHT) v.x = SPD;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(pointer == -1) {
            v.setZero();
        }
        return false;
    }

    @Override
    public void update(float delta) {
        if(!v.isZero()) {
            pos.add(v);
            blink();
            System.out.println(2 + v.toString());
        }
    }

    private void blink() {
    }
}
