package ru.geekbrains.game.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.base.Ship;
import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.pool.BulletPool;

public class MainShip extends Ship {

    private static final float SHIP_HEIGHT = 0.15f;
    private static final float MARGIN = 0.05f;
    private static final float RELOAD_INTERVAL = 0.2f;
    private static final int HP = 100;

    private static final int INVALID_POINTER = -1;

    private final Vector2 SPD = new Vector2(0.008f, 0);
    private boolean isMove = false;
    private Vector2 moveTarget = new Vector2();
    private int key;
    private int pointer = INVALID_POINTER;

public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
    super(atlas.findRegion("main_ship"), 1, 2, 2);
    this.bulletPool = bulletPool;
    this.bulletRegion = atlas.findRegion("bulletMainShip");
    this.bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    this.bulletHeight = 0.01f;
    this.damage = 1;
    this.v0.set(0.5f, 0);
    this.bulletV.set(0, 0.5f);
    this.reloadInterval = RELOAD_INTERVAL;
    this.hp = HP;
}

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(SHIP_HEIGHT);
        setBottom(worldBounds.getBottom() + MARGIN);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        isShoot = true;
        if(!isMove) {
            this.pointer = pointer;
            this.moveTarget = touch;
            isMove = true;
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(this.pointer == pointer) {
            isShoot = false;
            this.pointer = INVALID_POINTER;
            isMove = false;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.UP:
                isShoot = true;
                break;
            case Input.Keys.LEFT:
                key = keycode;
                moveTarget.set(worldBounds.getLeft(), 0);
                isMove = true;
                break;
            case Input.Keys.RIGHT:
                key = keycode;
                moveTarget.set(worldBounds.getRight(), 0);
                isMove = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(pointer == INVALID_POINTER &&keycode == Input.Keys.UP){
            isShoot = false;
        }else if(pointer == INVALID_POINTER && key==keycode) {
            this.pointer = INVALID_POINTER;
            this.key = INVALID_POINTER;
            isMove = false;
        }
        return false;
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getTop());
        super.update(delta);
        if(isMove) {
            if(moveTarget.x < pos.x) pos.sub(SPD);
            if(moveTarget.x > pos.x) pos.add(SPD);
//            blink();
        }
//            blink(0);

    }

    public void dispose() {
        bulletSound.dispose();
    }

//    private void blink() {
//        if(blinkTimer > 10) {
//            blink(blinkTimer);
//        } else {
//            blink(31);
//        }
//    }
//
//    private void blink(int time) {
//        if(blinkTimer < time) {
//            blinkTimer = time;
//        }
//        blinkTimer--;
//        if(blinkTimer <= 0){
//            setFrame(0);
//            return;
//        }
//        if(blinkTimer%20 == 0) {
//            nextFrame();
//        }
//    }
}
