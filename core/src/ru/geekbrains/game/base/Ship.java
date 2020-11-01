package ru.geekbrains.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.math.Rnd;
import ru.geekbrains.game.pool.BulletPool;
import ru.geekbrains.game.sprite.Bullet;

public abstract class Ship extends Sprite {

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected float bulletHeight;
    protected int damage;

    protected final Vector2 v;
    protected final Vector2 v0;
    protected final Vector2 bulletV;
    protected final Vector2 bulletPos;

    protected float reloadTimer;
    protected float reloadInterval;

    protected boolean isShoot;
    protected boolean isActive;

    protected int hp;

    public Ship() {
        v = new Vector2();
        v0 = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        isShoot = false;
        isActive = false;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
        v = new Vector2();
        v0 = new Vector2();
        bulletV = new Vector2();
        bulletPos = new Vector2();
        isShoot = false;
        isActive = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if(!isActive){
            pos.mulAdd(v0, delta);
            if(getTop() <= worldBounds.getTop()){
                isActive = true;
                isShoot = true;
                reloadTimer = Rnd.nextFloat(reloadInterval/2, reloadInterval);
            }
        }else {
            pos.mulAdd(v, delta);
            reloadTimer += delta;
            if (reloadTimer >= reloadInterval && isShoot) {
                reloadTimer = 0;
                shoot();
            }
        }
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play();
    }
}
