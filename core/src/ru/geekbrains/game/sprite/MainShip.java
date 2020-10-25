package ru.geekbrains.game.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.security.Key;

import ru.geekbrains.game.base.Sprite;
import ru.geekbrains.game.math.Rect;

public class MainShip extends Sprite {
    private final Vector2 SPD = new Vector2(0.008f, 0);
    private boolean isMove = false;
    private Vector2 touch;
    private int key;
    private int pointer = -1;
    private Rect worldBounds;
    private int blinkTimer;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 2);
        setHeightProportion(0.25F);
        touch = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(!isMove) {
            this.pointer = pointer;
            this.touch = touch;
            isMove = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(this.pointer == pointer) {
            this.pointer = -1;
            isMove = false;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {

            this.key = keycode;
            if(keycode == Input.Keys.LEFT) {
                touch.set(worldBounds.getLeft(), 0);
                isMove = true;
            }
            if(keycode == Input.Keys.RIGHT) {
                touch.set(worldBounds.getRight(), 0);
                isMove = true;

        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(pointer == -1 && key==keycode) {
            this.pointer = -1;
            this.key = -1;
            isMove = false;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        if(isMove) {
            if(touch.x < pos.x) pos.sub(SPD);
            if(touch.x > pos.x) pos.add(SPD);
            blink();
        }
            blink(0);

    }

    private void blink() {
        if(blinkTimer > 10) {
            blink(blinkTimer);
        } else {
            blink(31);
        }
    }

    private void blink(int time) {
        if(blinkTimer < time) {
            blinkTimer = time;
        }
        blinkTimer--;
        if(blinkTimer <= 0){
            setFrame(0);
            return;
        }
        if(blinkTimer%20 == 0) {
            nextFrame();
        }
    }
}
