package ru.geekbrains.game.pool;

import ru.geekbrains.game.base.SpritesPool;
import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private BulletPool bulletPool;
    private Rect worldBounds;

    public EnemyShipPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}
