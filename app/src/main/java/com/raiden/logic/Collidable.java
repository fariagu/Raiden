package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.raiden.gui.Image;

/**
 * Class of every object in the game that can collide with another object.
 */
public class Collidable extends Image {

    long frameTicker;   // the time of the last frame update
    int framePeriod;    // milliseconds between each frame (1000/fps)
    Rect sourceRect;    // the rectangle to be drawn from the animation bitmap
    int spriteWidth;    // the width of the sprite to calculate the cut out rectangle
    int spriteHeight;   // the height of the sprite
    int x, y;           // the X and Y coordinate of the object (top left of the image)
    boolean alive;      // if the power up was picked up

    /**
     * Default constructor for the Collidable.
     */
    public Collidable() {
        super();
        x = 0;
        y = 0;

        spriteWidth = 10;
        spriteHeight = 10;

        frameTicker = 0;
        framePeriod = 1;

        alive = true;
    }

    /**
     * Main constructor of the Collidable.
     *
     * @param bitmap Imagem para o objecto
     * @param x      posição na horizontal
     * @param y      posição na vertical
     * @param FPS    velocidade de jogo
     */
    Collidable(Bitmap bitmap, int x, int y, int FPS) {
        super(bitmap, 0, 0);
        this.x = x;
        this.y = y;

        spriteWidth = bitmap.getWidth();
        spriteHeight = bitmap.getHeight();

        frameTicker = 0;
        framePeriod = 1000 / FPS;

        alive = true;
    }

    //Getters & Setters

    /**
     * Getter for the bitmap of the Collidable.
     * @return Bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Setter to change the bitmap of the Collidable.
     * @param bitmap Nova imagem
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Getter of the Width of the Sprite.
     * @return int Sprite width
     */
    public int getSpriteWidth() {
        return spriteWidth;
    }

    /**
     * Getter of the Height of the Sprite.
     * @return int Sprite height
     */
    public int getSpriteHeight() {
        return spriteHeight;
    }

    /**
     * Getter of the X cordinate of the Collidable.
     * @return int
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for the X cordinate of the Collidable.
     * @param x coord of the center.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter of the Y cordinate of the Collidable.
     * @return int
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for the Y cordinate of the Collidable.
     * @param y coord of the center.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Checks if the Collidable is Alive.
     * @return true when alive, false when dead
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Changes the value of alive.
     * @param alive new state of alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Draws the bitmap to the screen of the mobile.
     * @param canvas mobile screen
     */
    public void draw(Canvas canvas) {
        if (alive) {
            // where to draw the sprite
            Rect destRect = new Rect(x - (spriteWidth / 2), y - (spriteHeight / 2),
                    x + (spriteWidth / 2), y + (spriteHeight / 2));
            canvas.drawBitmap(bitmap, sourceRect, destRect, null);
        }
    }
}
