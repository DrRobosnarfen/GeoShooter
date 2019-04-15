package geoshooter.Sprites;


import android.graphics.Bitmap;

import java.util.Random;

import geoshooter.Collision.BoundingBox;
import geoshooter.Collision.CollisionUtil;
import geoshooter.GameView;

public class EnemySprite extends Sprite {
    private int target_X, target_Y;
    private int c =0;
    public EnemySprite(Bitmap bmp, int player_X) {
        super(bmp);
        isAlly = false;
        Random temp = new Random();
        int tempX = temp.nextInt(GameView.screenWidth-getWidth());
        int tempY = temp.nextInt(GameView.screenHeight/6+100);
        myBox = new BoundingBox(tempX, tempX + getWidth(), tempY, tempY + getHeight());
        target_X = player_X + temp.nextInt(20)-10;
        target_Y = GameView.screenHeight - 100;
        xVelocity = (target_X - getX())/(temp.nextInt(70)+30);
        yVelocity = (target_Y - getY())/(temp.nextInt(30)+70);
        CollisionUtil.addToGrid(this);
    }
    public void updateX(int xf){
        target_X = xf;
    }

    public void update() {
        if(++c>30){
            myBox.update(xVelocity, yVelocity);
            deleteMe = myBox.isOutOfBounds();
            if(!deleteMe && myBox.checkGridChange()){
                CollisionUtil.remove(this);
                myBox.updateGrid();
                CollisionUtil.addToGrid(this);
            }

        }
    }
}
