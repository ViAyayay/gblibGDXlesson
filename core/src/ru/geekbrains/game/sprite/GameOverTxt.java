package ru.geekbrains.game.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.base.Ship;
import ru.geekbrains.game.math.Rect;

public class GameOverTxt extends Ship {

    public GameOverTxt(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"), 1, 1, 1);
        this.v0.set(0, -0.2f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.08f);
        if(isActive) setBottom(0);
        else setBottom(worldBounds.getTop());
    }

    @Override
    protected boolean inPosition() {
        return getBottom()<= 0;
    }
}
