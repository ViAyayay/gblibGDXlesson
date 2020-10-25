package ru.geekbrains.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.base.Sprite;
import ru.geekbrains.game.math.Rect;
import ru.geekbrains.game.math.Rnd;

public class Star extends Sprite {

    private final Vector2 v;
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.005f, 0.015f));
        v = new Vector2(Rnd.nextFloat(-0.005f, 0.005f), getHeight() * -15);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x, y);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if(this.isOutside(worldBounds)){
            pos.set(Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight()), worldBounds.getTop());
        }
    }
}
