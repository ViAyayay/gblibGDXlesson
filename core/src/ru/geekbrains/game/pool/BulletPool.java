package ru.geekbrains.game.pool;

import ru.geekbrains.game.base.SpritesPool;
import ru.geekbrains.game.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
