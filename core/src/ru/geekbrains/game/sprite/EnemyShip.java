package ru.geekbrains.game.sprite;

import ru.geekbrains.game.base.EnemySettingsDto;
import ru.geekbrains.game.base.Ship;
import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.pool.BulletPool;
import ru.geekbrains.game.pool.ExplosionPool;

public class EnemyShip extends Ship {

    public EnemyShip(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float delta) {
        bulletPos.set(pos.x, getBottom());
        super.update(delta);
        if (getTop() < worldBounds.getBottom()) {
            isActive = false;
            isShoot = false;
            destroyed = true;
        }
    }

    public void set(EnemySettingsDto settings) {
        this.regions = settings.getRegions();
        this.v.set(settings.getV());
        this.v0.set(settings.getV0());
        this.bulletRegion = settings.getBulletRegion();
        this.bulletHeight = settings.getBulletHeight();
        this.bulletV.set(settings.getBulletV());
        this.bulletSound = settings.getBulletSound();
        this.damage = settings.getDamage();
        this.reloadInterval = settings.getReloadInterval();
        setHeightProportion(settings.getHeight());
        this.hp = settings.getHp();
    }

    @Override
    public void destroy() {
        isActive = false;
        isShoot = false;
        super.destroy();
    }

    @Override
    protected boolean inPosition() {
        return getTop() <= worldBounds.getTop();
    }
}
