package geoshooter.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import geoshooter.Collision.BoundingBox;


public abstract class Sprite {
    private Bitmap image;
    protected BoundingBox myBox;
    protected int xVelocity, yVelocity;
    protected boolean isAlly;
    private static int spriteCount = 0;
    public boolean deleteMe = false;
    public Sprite(Bitmap bmp) {
        image = bmp;
    }
    public abstract void update();
    public Bitmap getBitmap(){
        return image;
    }
    public int getWidth() {
        return image.getWidth();
    }
    public int getHeight(){
        return image.getHeight();
    }
    public int getX(){
        return myBox.left;
    }
    public int getY(){
        return myBox.top;
    }
    public boolean alliedWith(Sprite s){
        return (isAlly == s.isAlly);
    }
    public boolean intersects(Sprite s){
        return myBox.intersects(s.myBox);
    }
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, getX(), getY(), null);
    }
    public int getLeftGrid(){
        return myBox.leftGrid;
    }
    public int getRightGrid(){
        return myBox.rightGrid;
    }
    public int getTopGrid(){
        return myBox.topGrid;
    }
    public int getBotGrid(){
        return myBox.botGrid;
    }
}