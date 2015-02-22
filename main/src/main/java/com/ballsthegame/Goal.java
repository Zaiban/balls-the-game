package com.ballsthegame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Random;

/**
 * Created by Esa on 12.12.2014.
 */
public class Goal {

    static final int HEIGHT = 50;
    static final int WIDTH = 50;

    private RectF mDimensions;
    private Paint mPaint;

    private Random random;

    public Goal(int x, int y) {
        mDimensions = new RectF();
        mPaint = new Paint();
        mDimensions.set(x, y, x + WIDTH, y + HEIGHT);
        mPaint.setColor(Color.BLUE);



    }

    public RectF getDimensions() {
        return mDimensions;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(mDimensions, mPaint);
    }

    public void setDimensions(int x, int y){
        mDimensions.set(x, y, x + WIDTH, y + HEIGHT);
    }

    public void placeGoal(){

        random = new Random(System.currentTimeMillis());
        int i = random.nextInt(3);

        Log.w("BallsTheGame", "random result: " + i);
        if(i == 0)
        {
            setDimensions(315, 730);
        }
        else if(i == 1)
        {
            setDimensions(395, 730);
        }
        else if(i == 2)
        {
            setDimensions(395, 320);
        }
    }
}
