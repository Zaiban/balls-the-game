package com.ballsthegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.app.Fragment;

import java.sql.Time;
import java.util.Date;
import java.util.Vector;

public class CanvasView extends View {

    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    Context context;
    private Paint timerPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private float gravityX;
    private float gravityY;
    private int timeElapsed;

    public static Ball mPlayer = new Ball(40f, 40f);
    public static Goal mGoal = new Goal(315, 730);
    Vector<Wall> walls = new Vector();

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        Activity host = (Activity) c;

        timeElapsed = 0;
        mPlayer.setmBitmap(context);


        timerPaint = new Paint();
        timerPaint.setColor(Color.BLACK);
        timerPaint.setStyle(Paint.Style.STROKE);
        timerPaint.setStrokeJoin(Paint.Join.ROUND);
        timerPaint.setStrokeWidth(4f);
        timerPaint.setTextSize(30f);

        // Add 20 wall elements
        for (int i = 0; i < 20; i++) {
            walls.add(new Wall(Color.BLACK));
        }
        // Set the boundaries of the maze
        walls.elementAt(0).set(0, 0, 20, 800);
        walls.elementAt(1).set(463, 0, 20, 800);
        walls.elementAt(2).set(10, 0, 480, 20);
        walls.elementAt(3).set(10, 783, 480, 20);
        // Create a maze inside the boundaries
        // set: distance from left, distance from top, width, height
        walls.elementAt(4).set(10, 100, 200, 10);
        walls.elementAt(5).set(280, 100, 190, 10);
        walls.elementAt(6).set(100, 175, 380, 10);
        walls.elementAt(7).set(100, 175, 10, 120);
        walls.elementAt(8).set(10, 375, 185, 10);
        walls.elementAt(9).set(185, 250, 10, 135);
        walls.elementAt(10).set(255, 250, 10, 135);
        walls.elementAt(11).set(320, 250, 10, 135);
        walls.elementAt(12).set(255, 375, 210, 10);
        walls.elementAt(13).set(70, 450, 300, 10);
        walls.elementAt(14).set(370, 250, 10, 135);
        walls.elementAt(15).set(370, 450, 10, 350);
        walls.elementAt(16).set(70, 450, 10, 260);
        walls.elementAt(17).set(140, 525, 10, 260);
        walls.elementAt(18).set(220, 450, 10, 260);
        walls.elementAt(19).set(300, 525, 10, 260);

        /*
        walls.elementAt(N).set(x, y, w, h);
        */


    }

    // override onSizeChanged
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw every wall
        for (int i = 0; i < walls.size(); i++) {
            walls.elementAt(i).draw(canvas);
        }
        // Draw the goal
        mGoal.draw(canvas);

        // Calculate player movement
        mPlayer.moveWithCollisionDetection(walls, gravityX, gravityY);
        // Draw the player
        mPlayer.draw(canvas);

        // Draw elapsed game time
        canvas.drawText("Time: " + timeElapsed, 330, 50, timerPaint);

    }

    public boolean checkGoal() {
        return mPlayer.checkGoal(mGoal);
    }

    public void drawNext() {
        invalidate();
    }

    public void updateTime(int seconds) {
        timeElapsed = seconds;
    }

    public void updateGravity(float x, float y) {
        gravityX = x;
        gravityY = y;
    }


}
