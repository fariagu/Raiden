package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.gustavo.raiden.model.components.Colllision;
import com.example.gustavo.raiden.model.components.Speed;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 */
public class Bullet {
    private Bitmap bitmap;    // the actual bitmap
    private Rect sourceRect;// the rectangle to be drawn from the animation bitmap
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite
    private int framenr;        // number of frames in animation
    private int currentFrame;   // the current frame
    private long frameTicker;   // the time of the last frame update
    private int framePeriod;    // milliseconds between each frame (1000/fps)
    private int x, y;            // the X and Y coordinates
    private Speed speed;        // the speed with its directions
    private boolean alive;        // whether it's still active or not
    private int ticks;
    private Droid enemy;

    public Bullet() {
        this.frameTicker = 0;
        this.framePeriod = 0;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = 0;
        spriteHeight = 0;
        sourceRect = new Rect(0, 0, 1, 1);
        this.speed = new Speed(0, 0);
        this.alive = false;
        this.ticks = 0;
    }

    public Bullet(Bitmap bitmap, int x, int y, int fps) {
        this.frameTicker = 0;
        this.framePeriod = 1000 / fps;
        this.bitmap = bitmap;
        currentFrame = 0;
        framenr = 2;
        spriteWidth = bitmap.getWidth() / 2;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        this.x = x;
        this.y = y;
        this.speed = new Speed(0, 15);
        this.speed.setyDirection(Speed.DIRECTION_UP);
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
    public Droid getEnemy() {
        return enemy;
    }
    public void setEnemy(Droid enemy) {
        this.enemy = enemy;
    }

    public void draw(Canvas canvas) {
        if (this.alive == true) {
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
            if (ticks > 120) {
                ticks = 0;
                alive = false;
            } else {
                this.y += speed.getYv() * speed.getyDirection();
            }


            if (frameTicker % 10 == 0)
                currentFrame++; // increment the frame
            if (currentFrame >= framenr) {
                currentFrame = 0;
            }

            // define the rectangle to cut out sprite
            this.sourceRect.left = currentFrame * spriteWidth;
            this.sourceRect.right = this.sourceRect.left + spriteWidth;
        }
    }

    public void checkCollision(){
        if (Colllision.collisionDetected(bitmap, this.x, this.y, enemy.getBitmap(), enemy.getX(), enemy.getY())){
            enemy.setAlive(false);
        }
    }
}
