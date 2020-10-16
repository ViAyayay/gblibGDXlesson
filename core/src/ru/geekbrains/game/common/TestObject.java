package ru.geekbrains.game.common;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class TestObject extends Vector2{
    public Texture img;
    public boolean isNoBlocked = true;
    public Vector2 moving = new Vector2(0, 0),
                   target = new Vector2(x, y);

    public TestObject(Texture img, Vector2 pos) {
        this.img = img;
        super.set(pos);
    }

    public Vector2 getCenter(){
        return new Vector2(x-img.getWidth()/2, y-img.getHeight()/2);
    }

    public void move() {
        moving = (new Vector2(target)).sub(x,y);
        super.add(moving.nor());
        if(((int)super.x == (int)target.x) && ((int)super.x == (int)target.x)) {
            super.set(target);
        }
    }

//    public void setCenter(int screenX, int screenY) {
//        pos.set(screenX-img.getWidth(), Gdx.graphics.getHeight() - screenY-img.getHeight());
//    }
}
