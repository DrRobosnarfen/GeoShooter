package geoshooter.Sprites;

import android.graphics.Bitmap;

import geoshooter.Collision.BoundingBox;
import geoshooter.Collision.CollisionUtil;

public  class AmmoSprite extends Sprite {
    public AmmoSprite(int x, int y, Bitmap bmp){
        super(bmp);
        isAlly = true;
        xVelocity = 0;
        yVelocity = -15;
        myBox = new BoundingBox(x + 1 - getWidth()/2, x + getWidth(), y -getHeight()-5 , y-5);
        CollisionUtil.addToGrid(this);
    }
    public void update() {
        myBox.update(xVelocity,  yVelocity);
        deleteMe = myBox.isOutOfBounds();
        if(!deleteMe && myBox.checkGridChange()){
            CollisionUtil.remove(this);
            myBox.updateGrid();
            CollisionUtil.addToGrid(this);
        }
    }

}