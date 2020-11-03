package ru.geekbrains.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.base.BaseScreen;
import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.pool.BulletPool;
import ru.geekbrains.game.pool.EnemyShipPool;
import ru.geekbrains.game.pool.ExplosionPool;
import ru.geekbrains.game.sprite.Background;
import ru.geekbrains.game.sprite.Bullet;
import ru.geekbrains.game.sprite.EnemyShip;
import ru.geekbrains.game.sprite.MainShip;
import ru.geekbrains.game.sprite.Star;
import ru.geekbrains.game.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;

    private TextureAtlas atlas;
    private Texture bg;
    private Music music;
    private Sound enemyBulletSound;
    private Sound explosionSound;

    private Background background;
    private Star[] stars;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;
    private MainShip mainShip;
    private EnemyEmitter enemyEmitter;

//    private Vector2 tmpPos = new Vector2();

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bg = new Texture("textures/bg.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));

        background = new Background(bg);
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }

        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, worldBounds);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyShipPool, enemyBulletSound, atlas);

        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        mainShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        music.dispose();
        enemyBulletSound.dispose();
        explosionSound.dispose();
        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return false;
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }

        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        mainShip.update(delta);
        enemyEmitter.generate(delta);

    }

    private void checkCollision() {
        for (Bullet bullet : bulletPool.getActiveObjects()) {
            if(bullet.getOwner() == mainShip){
                for (EnemyShip enemyShip : enemyShipPool.getActiveObjects()){
                    if(enemyShip.pos.dst(bullet.pos) < enemyShip.getHalfWidth()){
                        bullet.destroy();
                        enemyShip.damage(bullet.getDamage());
                        break;
                    }
                }
            } else {
                if(bullet.pos.dst(mainShip.pos) < mainShip.getHalfWidth()){
                    mainShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
        for (EnemyShip enemyShip : enemyShipPool.getActiveObjects()) {
            float minDist = mainShip.getHalfWidth() + enemyShip.getHalfWidth();
            if(enemyShip.pos.dst(mainShip.pos) < minDist) {
                enemyShip.destroy();
                mainShip.damage(enemyShip.getDamage());
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyShipPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        mainShip.draw(batch);
        enemyShipPool.drawActiveSprites(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }
}
