package geoshooter.Collision;

import android.content.res.Resources;

import geoshooter.GameView;

public class BoundingBox {
    public int top, bot, left, right;
    public int leftGrid, rightGrid, topGrid, botGrid;
    public BoundingBox(int left, int right, int top, int bot){
        this.left = left;
        this.right = right;
        this.top = top;
        this.bot = bot;
        updateGrid();
    }
    public void update(int xMovement, int yMovement){
        left += xMovement;
        right += xMovement;
        top += yMovement;
        bot += yMovement;
    }
    public void updateGrid(){
        leftGrid =  left /GameView.cellWidth;
        rightGrid =  right/GameView.cellWidth;
        topGrid =  top/GameView.cellHeight;
        botGrid = bot/GameView.cellHeight;
    }
    public boolean checkGridChange(){
        if(leftGrid != left/GameView.cellWidth || rightGrid != right/GameView.cellWidth
                || topGrid != top/GameView.cellHeight || botGrid != bot/GameView.cellHeight) {
            return true;
        }
        return false;
    }
    public boolean intersects(BoundingBox otherBox){
        return(left<= otherBox.right && right >= otherBox.left && top <= otherBox.bot && bot >= otherBox.top);
    }
    public boolean isOutOfBounds(){
        if(right<0 || left> GameView.screenWidth|| bot > GameView.screenHeight || top < 0)
            return true;
        else return false;
    }
}
