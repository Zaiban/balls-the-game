package com.ballsthegame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Wall {

    private Paint mPaint;  // mPaint style and color
    private Rect mDimensions;

    public Wall(int color) {
        mPaint = new Paint();
        mDimensions = new Rect();
        mPaint.setColor(color);

    }

    public void set(int x, int y, int width, int height) {
        // The box's rect do not change unless the view's size changes
        mDimensions.set(x, y, x + width - 1, y + height - 1);
    }

    public Rect getDimensions() {
        return mDimensions;
    }

    public void draw(Canvas canvas) {
        canvas.drawRect(mDimensions, mPaint);
    }
}