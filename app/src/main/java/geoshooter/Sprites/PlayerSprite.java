package geoshooter.Sprites;

import android.graphics.Bitmap;

import geoshooter.Collision.BoundingBox;
import geoshooter.Collision.CollisionUtil;
import geoshooter.GameView;

public class PlayerSprite extends Sprite {
    private int target_X;
    private int firerate = 30;

    public PlayerSprite(Bitmap bmp) {
        super(bmp);
        isAlly = true;
        xVelocity = 8;
        yVelocity = 5;
        myBox = new BoundingBox(GameView.screenWidth/2-(getWidth()-1)/2, GameView.screenWidth/2-(getWidth()-1)/2 + getWidth(),
                GameView.screenHeight - 100, GameView.screenHeight - 100 + getHeight());
        target_X = getX();
     //   CollisionUtil.addToGrid(this);
    }

    public void updateX(int xf) {
        if(xf>GameView.screenWidth-getWidth())
            target_X = GameView.screenWidth - getWidth();
        else if(xf<0)
            target_X = 0;
        else
            target_X = xf;
    }

    public void update() {
        float x = getX();
        if (x < target_X - xVelocity || x > target_X + xVelocity) {
            if (x > target_X) {
                myBox.update(-xVelocity, 0);
            } else if (x < target_X) {
                myBox.update(xVelocity, 0);
            }
        }
        if(myBox.checkGridChange()){
         //   CollisionUtil.remove(this);
            myBox.updateGrid();
          //  CollisionUtil.addToGrid(this);
        }
    }
    public int getFirerate() {
        return this.firerate;
    }
    public void  resetY(){
        myBox.top = GameView.screenHeight - 150;
    }
}
