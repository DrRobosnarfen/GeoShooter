package geoshooter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import geoshooter.R;

import java.util.*;

import geoshooter.Collision.CollisionUtil;
import geoshooter.Sprites.*;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private PlayerSprite playerSprite;
    public static int counter;
    public static ArrayList<Sprite> masterList = new ArrayList();
    public static final int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    public static final int GRID_COLUMNS = 15;
    public static final int GRID_ROWS = 10;
    public static final int cellWidth = screenWidth/GRID_COLUMNS;
    public static final int cellHeight = screenHeight/GRID_ROWS;


    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int x = (int) e.getX();
        playerSprite.updateX(x);
        return true;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    public void surfaceCreated(SurfaceHolder holder) {
        CollisionUtil.initializeGrid();
        playerSprite = new PlayerSprite(BitmapFactory.decodeResource(getResources(), R.drawable.player_sprite2));
        masterList.add(playerSprite);
        thread.setRunning(true);
        thread.start();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    public void update() {
        counter++;
        for(Sprite s: masterList){
            s.update();
        }
        CollisionUtil.checkBroadPhaseCollision();
    }


    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        if (counter % playerSprite.getFirerate() == 1) {
            masterList.add(new AmmoSprite(playerSprite.getX() + playerSprite.getWidth()/2, playerSprite.getY(), BitmapFactory.decodeResource(getResources(), R.drawable.bullet1)));
            masterList.add(new EnemySprite(BitmapFactory.decodeResource(getResources(), R.drawable.enemy1), playerSprite.getX()));
          //  masterList.add(new EnemySprite(BitmapFactory.decodeResource(getResources(), R.drawable.enemy1), playerSprite.getX()));
       //     masterList.add(new EnemySprite(BitmapFactory.decodeResource(getResources(), R.drawable.enemy1), playerSprite.getX()));
        }
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(90);
        canvas.drawText(String.valueOf(CollisionUtil.score), screenWidth-200, 100, p);
        /*
        for(int i=0 ; i<screenWidth; i += cellWidth){
            canvas.drawLine(i, 0, i, screenHeight, p);
        }
        for(int i=0 ; i<screenHeight; i += cellHeight){
            canvas.drawLine(0, i, screenWidth, i, p);
        } */
        for(Sprite sprite: masterList){
            if(sprite.deleteMe){
                masterList.remove(sprite);
                CollisionUtil.remove(sprite);
                continue;
            }
            sprite.draw(canvas);
        }
    }
}
