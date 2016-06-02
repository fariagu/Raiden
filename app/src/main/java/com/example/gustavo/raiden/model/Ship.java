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
    private boolean touched;    // if droid is touched/picked up
    private int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    private int spriteHeight;   // the height of the sprite
    private int ibull;

    private int x, y;           // the X and Y coordinate of the object (top left of the image)
    private int oldX, oldY;     // the old X and Y coordinate of the object to calculate de frame

    public Ship(Bitmap bitmap, int x, int y, int width, int height, int fps, int frameCount) {
        this.bitmap = bitmap;
        this.x = x;
        this.oldX = x;
        this.y = y;
        this.oldY = y;
        currentFrame = 5;
        frameNr = frameCount;
        spriteWidth = bitmap.getWidth() / frameCount;
        spriteHeight = bitmap.getHeight();
        sourceRect = new Rect(0, 0, spriteWidth, spriteHeight);
        framePeriod = 1000 / fps;
        frameTicker = 0L;
        ibull = 0;
    }

    public static String getTAG() {
        return TAG;
    }

    //Getters & Setters

    /**
     * Getter para obter o bitmap associado à Ship.
     *
     * @return Bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Setter para modificar o bitmap da Ship.
     *
     * @param bitmap
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Getter para obter o Rect associado à classe Ship.
     *
     * @return Rect
     */
    public Rect getSourceRect() {
        return sourceRect;
    }

    public void setSourceRect(Rect sourceRect) {
        this.sourceRect = sourceRect;
    }

    /**
     * Getter para obter o número de Frames do sprite da Ship.
     *
     * @return int
     */
    public int getFrameNr() {
        return frameNr;
    }

    public void setFrameNr(int frameNr) {
        this.frameNr = frameNr;
    }

    /**
     * Getter para obter o Frame actual no sprite da Ship.
     *
     * @return int
     */
    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    /**
     * Retorna o tempo do último update do Frame do sprite da Ship.
     *
     * @return long
     */
    public long getFrameTicker() {
        return frameTicker;
    }

    public void setFrameTicker(long frameTicker) {
        this.frameTicker = frameTicker;
    }

    public int getFramePeriod() {
        return framePeriod;
    }

    public void setFramePeriod(int framePeriod) {
        this.framePeriod = framePeriod;
    }

    /**
     * Retorna se a Ship está a ser tocada.
     *
     * @return boolean
     */
    public boolean isTouched() {
        return touched;
    }

    public void setTouched(boolean touched) {
        this.touched = touched;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public void setSpriteWidth(int spriteWidth) {
        this.spriteWidth = spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public void setSpriteHeight(int spriteHeight) {
        this.spriteHeight = spriteHeight;
    }

    /**
     * Retorna a coordenada X da Ship.
     *
     * @return int
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Retorna a coordenada Y da Ship.
     *
     * @return int
     */
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getOldX() {
        return oldX;
    }

    public void setOldX(int oldX) {
        this.oldX = oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public void setOldY(int oldY) {
        this.oldY = oldY;
    }

    public int getIbull() {
        return ibull;
    }

    public void setIbull(int ibull) {
        this.ibull = ibull;
    }

    public void update(long gameTime) {
        if (gameTime > frameTicker + framePeriod) {
            frameTicker = gameTime;
            if (touched) {
                if (oldX > x) {
                    currentFrame--;
                } else if (oldX < x) {
                    currentFrame++;
                } else {//isto faz com que mesmo a tocar volte ao estado inicial
                    if (currentFrame > (frameNr / 2))
                        currentFrame--;
                    else if (currentFrame < (frameNr / 2))
                        currentFrame++;
                    else {
                        currentFrame = frameNr / 2;
                    }
                }
            } else {
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


            /*currentFrame++; // increment the frame
            if (currentFrame >= frameNr) {
                currentFrame = 0;
            }*/
        }

        // define the rectangle to cut out sprite
        this.sourceRect.left = currentFrame * spriteWidth;
        this.sourceRect.right = this.sourceRect.left + spriteWidth;
    }

    public void draw(Canvas canvas) {
        // where to draw the sprite
        Rect destRect = new Rect(getX() - bitmap.getWidth() / (frameNr + 1), getY() - bitmap.getHeight() / 2,
                getX() + bitmap.getWidth() / (frameNr + 1), getY() + bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, sourceRect, destRect, null);
    }

    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - 50 - bitmap.getWidth() / frameNr) && (eventX <= (x + 50 + bitmap.getWidth() / frameNr))) {
            if (eventY >= (y - 50 - bitmap.getHeight() / 2) && (eventY <= (y + 50 + bitmap.getHeight() / 2))) {

                setTouched(true);// ship touched
                oldX = eventX;
                oldY = eventY;
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }

    }
}
