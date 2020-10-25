package ru.geekbrains.game.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.math.Rect;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region) {
        this.regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int i) {
        this.regions = new TextureRegion[i];
        int x = 0;
        int y = 0;
        for (int j = 0; j < i; j++) {
            regions[j] = new TextureRegion(region, x, y, region.getRegionWidth()/i, region.getRegionHeight());
            x += region.getRegionWidth()/i;
        }

    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
                );
    }

    public void update(float delta) {

    }

    public void resize(Rect worldBounds) {

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }


    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void nextFrame(){
        int i = frame;
        if(++i==regions.length) i = 0;
        frame = i;
    }

    public void setFrame(int i){if(regions.length > i && i>=0) frame = i;}


}
