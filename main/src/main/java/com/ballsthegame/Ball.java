package com.ballsthegame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import java.util.Vector;

public class Ball {

    private float mRadius;      // Ball's radius
    private float mPosX;  // Ball's center (mPosX,y)
    private float mPosY;
    private float mSpeedX;       // Ball's speed (mPosX,y)
    private float mSpeedY;
    private float mMaxSpeed;
    private int mAngle;
    private RectF mBounds;
    private Paint mPaint;    // The mPaint style, color used for drawing
    private Bitmap mBitmap;// The looks of the Ball
    private boolean mCollided;

    // Constructors
    public Ball(float startingX, float startingY) {
        mRadius = 25;
        mPosX = mRadius + startingX;
        mPosY = mRadius + startingY;
        mSpeedX = 0.0f;       // Ball's speed (mPosX,mPosY)
        mSpeedY = 0.0f;
        mMaxSpeed = 10.0f;
        mAngle = 0;
        mBounds = new RectF();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mCollided = false;
    }

    // Set mBitmap
    public void setmBitmap(Context context) {
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_leopard);
    }

    public void moveWithCollisionDetection(Vector<Wall> walls, float gravityX, float gravityY) {

        // Apply gravity
        mSpeedX += gravityX / 10f;
        mSpeedY += gravityY / 10f;

        // Apply drag
        mSpeedX = mSpeedX * 0.95f;
        mSpeedY = mSpeedY * 0.95f;

        if (mSpeedX > mMaxSpeed)
            mSpeedX = mMaxSpeed;
        if (mSpeedX < 0 - mMaxSpeed)
            mSpeedX = 0 - mMaxSpeed;
        if (mSpeedY > mMaxSpeed)
            mSpeedY = mMaxSpeed;
        if (mSpeedY < 0 - mMaxSpeed)
            mSpeedY = 0 - mMaxSpeed;

        // Check for collision with walls and react
        for (int i = 0; i < walls.size(); i++) {

            mCollided = intersects(mPosX, mPosY, mRadius, walls.elementAt(i).getDimensions().left, walls.elementAt(i).getDimensions().top,
                    walls.elementAt(i).getDimensions().right, walls.elementAt(i).getDimensions().bottom);
            if (mCollided) {
                if (walls.elementAt(i).getDimensions().width() > walls.elementAt(i).getDimensions().height()) {
                    mPosY -= mSpeedY;
                    mSpeedY = -mSpeedY * 0.1f;
                    mPosX += mSpeedX;
                } else {
                    mPosX += mSpeedX;
                    mSpeedX = -mSpeedX * 0.1f;
                    mPosY -= mSpeedY;
                }

            }


        }


        // Get new (x,y) position
        mPosX -= mSpeedX;
        mPosY += mSpeedY;

    }

    public boolean checkGoal(Goal goal) {
        // Check if collision with goal
        if (mBounds.intersect(goal.getDimensions())) return true;
        return false;
    }

    public boolean intersects(float cx, float cy, float radius, float left, float top, float right, float bottom) {
        float closestX = (cx < left ? left : (cx > right ? right : cx));
        float closestY = (cy < top ? top : (cy > bottom ? bottom : cy));
        float dx = closestX - cx;
        float dy = closestY - cy;

        return (dx * dx + dy * dy) <= radius * radius;
    }

    public void draw(Canvas canvas) {
        mBounds.set(mPosX - mRadius, mPosY - mRadius, mPosX + mRadius, mPosY + mRadius);
        //canvas.drawOval(mBounds, mPaint);

        // Increase rotating mAngle.
        mAngle -= (mSpeedY + mSpeedX) / 2;
        if (mAngle > 360)
            mAngle = 0;
        if (mAngle < 0)
            mAngle = 360;
        // Draw the player
        canvas.save();
        canvas.rotate(mAngle, mPosX, mPosY);
        canvas.drawBitmap(mBitmap, null, mBounds, mPaint);
        canvas.restore();
    }
    public void setPosition(float x, float y)
    {
        mPosX = mRadius + x;
        mPosY = mRadius + y;
    }
}