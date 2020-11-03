package ru.geekbrains.game.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.math.Rnd;
import ru.geekbrains.game.pool.BulletPool;
import ru.geekbrains.game.pool.ExplosionPool;
import ru.geekbrains.game.sprite.Bullet;
import ru.geekbrains.game.sprite.Explosion;

public abstract class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

    protected Rect worldBounds;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
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

    private float damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;

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
            if(inPosition()){
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
            damageAnimateTimer += delta;
            if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
                frame = 0;
            }
        }
    }

    protected abstract boolean inPosition();

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void damage(int damage) {
        frame = 1;
        damageAnimateTimer = 0f;
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, worldBounds, damage, bulletHeight);
        bulletSound.play();
    }

    public void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
