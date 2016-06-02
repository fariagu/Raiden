/*
package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gustavo.raiden.R;
import com.example.gustavo.raiden.model.components.Speed;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 */
/*public class Bullet {

    private Bitmap bitmap;	// the actual bitmap
    private int x, y;			// the X and Y coordinate
    private boolean touched;	// if droid is touched/picked up
    private Speed speed;	// the speed with its directions
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite
    private int currentFrame;   // the current frame
    private int frameNr;        // number of frames in animation
    private Rect sourceRect;    // the rectangle to be drawn from the animation bitmap


    public Bullet(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed();
        this.currentFrame = 0;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        frameNr = 2;
        spriteWidth = bitmap.getWidth() / frameNr;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isTouched() {
        return touched;
    }
    public void setTouched(boolean touched) {
        this.touched = touched;
    }
    public Speed getSpeed() {
        return speed;
    }
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        Rect destRect = new Rect(getX() - bitmap.getWidth() / frameNr, getY() - bitmap.getHeight() / 2,
                getX() + bitmap.getWidth() / frameNr, getY() + bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
//        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    /**
     * Method which updates the bullet's internal state every tick
     */
   /* public void update() {
        y += 100;
        if(currentFrame==0)
            currentFrame=1;
        else
            currentFrame=0;
        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
    }
}
*/

package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Speed;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 */
public class Bullet {

    private Bitmap bitmap;	// the actual bitmap
    private Rect sourceRect;// the rectangle to be drawn from the animation bitmap
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite
    private int framenr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private int x, y;		    // the X and Y coordinates
    private boolean touched;    // if droid is touched/picked up
    private Speed speed;	    // the speed with its directions
    private boolean alive;	    // whether it's still active or not
    private int ticks;

    public Bullet(Bitmap bitmap) {
        this.bitmap = bitmap;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.x = 0;
        this.y = 0;
        this.speed = new Speed();
        this.alive = true;
        this.ticks = 0;
    }

    public Bullet(Bitmap bitmap, int x, int y) {
        this.bitmap = bitmap;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.x = x;
        this.y = y;
        this.speed = new Speed();
        this.alive = true;
        this.ticks = 0;
    }

    //Getters & Setters
    public Bitmap getBitmap() {return bitmap;}
    public void setBitmap(Bitmap bitmap) {this.bitmap = bitmap;}
    public int getX() {return x;}
    public void setX(int x) {this.x = x;}
    public int getY() {return y;}
    public void setY(int y) {this.y = y;}
    public boolean isTouched() {return touched;}
    public void setTouched(boolean touched) {this.touched = touched;}
    public Speed getSpeed() {return speed;}
    public void setSpeed(Speed speed) {this.speed = speed;}
    public boolean isAlive() {return alive;}
    public void setAlive(boolean alive) {this.alive = alive;}
    public void setTicks(int ticks) {this.ticks = ticks;}

    public void draw(Canvas canvas) {
        if(this.alive==true && this.ticks >= 0) {
            Rect destRect = new Rect(this.x - (bitmap.getWidth() / 2), this.y - (bitmap.getHeight() / 2),
                    this.x + bitmap.getWidth() / 2, this.y + (bitmap.getHeight() / 2));
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);
        }
    }

    /**
     * Method which updates the bullet's position
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
        ticks++;
        if(ticks > 120) {
            ticks = 0;
            alive = false;
        } else {
            this.y -= 20;
        }


            if (ticks % 10 == 0)
                currentFrame++; // increment the frame
            if (currentFrame >= framenr) {
                currentFrame = 0;
            }

            // define the rectangle to cut out sprite
            this.sourceRect.left = currentFrame * spriteWidth;
            this.sourceRect.right = this.sourceRect.left + spriteWidth;

        }


        /*if( this.x <20 &&  this.y<20)
            this.alive = false;
        else {
           this.y -= 1;
        }*/
    }
}
