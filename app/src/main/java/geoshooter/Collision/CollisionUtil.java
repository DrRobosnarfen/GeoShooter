package geoshooter.Collision;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import geoshooter.GameView;
import geoshooter.Sprites.Sprite;

public class CollisionUtil {
    private static ArrayList<Sprite>[][] grid = new ArrayList[GameView.GRID_COLUMNS][GameView.GRID_ROWS];
    public static int score = 0;
    public static void initializeGrid() {
        for (int i = 0; i < GameView.GRID_COLUMNS; i++) {
            for (int j = 0; j < GameView.GRID_ROWS; j++) {
                grid[i][j] = new ArrayList<Sprite>(5);
            }
        }
    }

    public static void addToGrid(Sprite sprite) {
        for (int i = sprite.getLeftGrid(); i <= sprite.getRightGrid(); i++) {
            for (int j = sprite.getTopGrid(); j <= sprite.getBotGrid(); j++) {
            //    System.out.println("ADDING TO GRID L R T B " + sprite.getLeftGrid() + " " + sprite.getRightGrid() + " " + sprite.getTopGrid() + " " + sprite.getBotGrid() );
                grid[i][j].add(sprite);
            }
        }
    }
    public static void remove(Sprite sprite){
        for (int i = sprite.getLeftGrid(); i <= sprite.getRightGrid(); i++) {
            for (int j = sprite.getTopGrid(); j <= sprite.getBotGrid(); j++) {
            //    System.out.println("REMOVING FROM GRID: L R T B " + sprite.getLeftGrid() + " " + sprite.getRightGrid() + " " + sprite.getTopGrid() + " " + sprite.getBotGrid() );
                grid[i][j].remove(sprite);
            }
        }
    }
    public static void checkBroadPhaseCollision() {
        for (int i = 0; i < GameView.GRID_COLUMNS; i++) {
            for (int j = 0; j < GameView.GRID_ROWS; j++) {
                if (grid[i][j].size() < 2) continue;
                for (int k = 0; k < grid[i][j].size(); k++) {
                    Sprite temp1 = grid[i][j].get(k);
                    for (int l = k + 1; l < grid[i][j].size(); l++) {
                        Sprite temp2 = grid[i][j].get(l);
                        if(temp1.alliedWith(temp2))
                            continue;
                        if(temp1.deleteMe == true || temp2.deleteMe == true)
                            continue;
                        midPhaseCollision(temp1, temp2);
                    }
                }

            }
        }
    }

    private static void midPhaseCollision(Sprite sprite1, Sprite sprite2) {
        if (sprite1.intersects(sprite2)) {
            narrowPhaseCollision(sprite1, sprite2);
        }
    }

    private static void narrowPhaseCollision(Sprite sprite1, Sprite sprite2) {
        int left =  Math.max(sprite1.getX(), sprite2.getX());
        int right =  Math.min(sprite1.getX() + sprite1.getWidth(), sprite2.getX() + sprite2.getWidth());
        int top =  Math.max(sprite1.getY(), sprite2.getY());
        int bot =  Math.min(sprite1.getY() + sprite1.getHeight(), sprite2.getY() + sprite2.getHeight());
        for (int i = left; i < right; i++) {
            for (int j = top; j < bot; j++) {
                int sprite1Pixel = getBitmapPixel(sprite1, i, j);
                int sprite2Pixel = getBitmapPixel(sprite2, i, j);
                if (isFilled(sprite1Pixel) && isFilled(sprite2Pixel)) {
                    sprite1.deleteMe = true;
                    sprite2.deleteMe = true;
                    score++;
                    return;
                }
            }
        }
    }

    private static int getBitmapPixel(Sprite sprite, int i, int j) {
        return sprite.getBitmap().getPixel(i - sprite.getX(), j -  sprite.getY());
    }

    private static boolean isFilled(int pixel) {
        return pixel != Color.TRANSPARENT;
    }
}