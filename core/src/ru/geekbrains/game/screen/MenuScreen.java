package ru.geekbrains.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.base.BaseScreen;
import ru.geekbrains.game.common.TestObject;

public class MenuScreen extends BaseScreen {
    TestObject obg;

    @Override
    public void show() {
        super.show();
        obg = new TestObject(new Texture("badlogic.jpg"), new Vector2(100, 100));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(obg.img, obg.getCenter().x, obg.getCenter().y);
        batch.end();
        if(obg.isNoBlocked) obg.move();
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float halfObjX = obg.img.getWidth()/2;
        float halfObjY = obg.img.getHeight()/2;
        int nScreenY = Gdx.graphics.getHeight() - screenY;

        if((screenX <= obg.x+halfObjX && screenX >= obg.x-halfObjX) && (nScreenY <= obg.y+halfObjY && nScreenY >= obg.y-halfObjY)){
            System.out.println("AAAAAAAAAAA");
        }else {
            obg.target.set(screenX, nScreenY);
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
