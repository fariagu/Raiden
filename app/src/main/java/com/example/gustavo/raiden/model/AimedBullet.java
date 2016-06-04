package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Colllision;
import com.example.gustavo.raiden.model.components.Speed;


/**
 * Created by Gustavo Faria on 02/06/2016.
 */
public class AimedBullet extends Bullet {
    private Bitmap bitmap;	// the actual bitmap
    private Rect sourceRect;// the rectangle to be drawn from the animation bitmap
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite
    private int framenr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private int x, y;		    // the X and Y coordinates
    private Speed speed;	    // the speed with its directions
    private boolean alive;	    // whether it's still active or not
    private int ticks;

    public AimedBullet(Bitmap bitmap, Ship s, int fps) {
        this.frameTicker = 0;
        this.framePeriod = 1000 / fps;
        this.bitmap = bitmap;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.x = 0;
        this.y = 0;
        this.speed = new Speed();
        this.alive = true;
        this.ticks = 0;
    }

    public AimedBullet(Bitmap bitmap, int x, int y, Ship s, int FPS) {
        this.frameTicker = 0;
        this.framePeriod = 1000 / FPS;
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
    public Speed getSpeed() {
        return speed;
    }
    public void setSpeed(Speed speed) {
        this.speed = speed;
    }
    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

    public void draw(Canvas canvas) {
        if(this.alive) {
            canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
        }
    }

    /**
     * Method which updates the aimedbullet's position
     */
    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            ticks++;
            if(ticks > 150) {
                ticks = 0;
                this.alive = false;
            } else {
                //this.y -= 20;
            }

            if (alive) {
                x += (speed.getXv() * speed.getxDirection());
                y += (speed.getYv() * speed.getyDirection());
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
    }
}
