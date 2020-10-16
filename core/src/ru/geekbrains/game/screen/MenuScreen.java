package ru.geekbrains.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.game.base.BaseScreen;
import ru.geekbrains.game.common.TestObject;

public class MenuScreen extends BaseScreen {
    TestObject obj;

    @Override
    public void show() {
        super.show();
        obj = new TestObject(new Texture("badlogic.jpg"), new Vector2(100, 100));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(obj.img, obj.getCenter().x, obj.getCenter().y);
        batch.end();
        if(obj.isNoBlocked) {
            obj.move();
        }else {

        }
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        obj.isNoBlocked = true;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int nScreenY = Gdx.graphics.getHeight() - screenY;
        if(!obj.isNoBlocked) obj.set((new Vector2(screenX,nScreenY)).sub(obj.drugPos));
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float halfObjX = obj.img.getWidth()/2;
        float halfObjY = obj.img.getHeight()/2;
        int nScreenY = Gdx.graphics.getHeight() - screenY;

        if((screenX <= obj.x+halfObjX && screenX >= obj.x-halfObjX) && (nScreenY <= obj.y+halfObjY && nScreenY >= obj.y-halfObjY)){
            obj.drugPos.set(screenX - obj.x, nScreenY - obj.y);
            obj.isNoBlocked = false;
        }else {
            obj.target.set(screenX, nScreenY);
        }
        return super.touchDown(screenX, screenY, pointer, button);
    }
}
