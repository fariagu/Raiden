package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Diogo on 07/05/2016.
 */
public class Ship {
    private static final String TAG = Ship.class.getSimpleName();

    private Bitmap bitmap;      // the animation sequence
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    private int frameNr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private boolean touched;	// if droid is touched/picked up
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite

    private int x, y;           // the X and Y coordinate of the object (top left of the image)
    private int oldX, oldY;      // the old X and Y coordinate of the object to calculate de frame

    public Ship(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x; this.oldX = x;
        this.y = y; this.oldY = y;
        currentFrame = 5;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0L;
    }

    //getters & setters
    public static String getTAG() {return TAG;}
    public Bitmap getBitmap() {return bitmap;}
    public void setBitmap(Bitmap bitmap) {this.bitmap = bitmap;}
    public Rect getSourceRect() {return sourceRect;}
    public void setSourceRect(Rect sourceRect) {this.sourceRect = sourceRect;}
    public int getFrameNr() {return frameNr;}
    public void setFrameNr(int frameNr) {this.frameNr = frameNr;}
    public int getCurrentFrame() {return currentFrame;}
    public void setCurrentFrame(int currentFrame) {this.currentFrame = currentFrame;}
    public long getFrameTicker() {return frameTicker;}
    public void setFrameTicker(long frameTicker) {this.frameTicker = frameTicker;}
    public int getFramePeriod() {return framePeriod;}
    public void setFramePeriod(int framePeriod) {this.framePeriod = framePeriod;}
    public boolean isTouched() {return touched;}
    public void setTouched(boolean touched) {this.touched = touched;}
    public int getSpriteWidth() {return spriteWidth;}
    public void setSpriteWidth(int spriteWidth) {this.spriteWidth = spriteWidth;}
    public int getSpriteHeight() {return spriteHeight;}
    public void setSpriteHeight(int spriteHeight) {this.spriteHeight = spriteHeight;}
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) {this.y = y;}
    public int getOldX() {return oldX;}
    public void setOldX(int oldX) {this.oldX = oldX;}
    public int getOldY() {return oldY;}
    public void setOldY(int oldY) {this.oldY = oldY;}

    public void update(long gameTime) {
        if(touched) {
            if (oldX > x) {
                currentFrame--;
            } else if (oldX < x) {
                currentFrame++;
            } else {
                if (currentFrame > (frameNr / 2))
                    currentFrame--;
                else if (currentFrame < (frameNr / 2))
                    currentFrame++;
                else {
                    currentFrame = frameNr / 2;
                }
            }
        }
        else{
            if (currentFrame > (frameNr / 2))
                currentFrame--;
            else if (currentFrame < (frameNr / 2))
                currentFrame++;
            else {
                currentFrame = frameNr / 2;
            }
        }
        if (currentFrame <= 0)
            currentFrame = 0;
        if (currentFrame >= frameNr)
            currentFrame = frameNr - 1;

        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            //currentFrame++; // increment the frame
            /*if (currentFrame >= frameNr) {
                currentFrame = 0;
            }*/
        }
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect(getX(), getY(), getX() + spriteWidth, getY() + spriteHeight);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2))) {
                setTouched(true);// droid touched
                oldX=eventX;
                oldY=eventY;
            }
            else {
                setTouched(false);
            }
        }
        else {
            setTouched(false);
        }

    }
}
