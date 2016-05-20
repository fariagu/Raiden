package com.example.gustavo.raiden.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;

import com.example.gustavo.raiden.R;
import com.example.gustavo.raiden.model.components.Speed;

/**
 * Created by gustavo on 20-05-2016.
 */
public class Bullet extends Droid {
    private Droid parent;

    public Bullet (int x, int y, Context c, Droid p){
        super(x, y, c);
        this.setBitmap(BitmapFactory.decodeResource(c.getResources(), R.drawable.bullet));
        this.parent = p;

    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(this.getBitmap(), this.getX() - (this.getBitmap().getWidth() / 2), this.getY() - (this.getBitmap().getHeight() / 2) + parent.getY(), null);
    }

    /**
     * Method which updates the droid's internal state every tick
     */
    public void update() {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        // check collision with right wall if heading right
        if (this.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                && this.getX() + this.getBitmap().getWidth() / 2 >= width) {
            this.getSpeed().toggleXDirection();
        }
        // check collision with left wall if heading left
        if (this.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && this.getX() - this.getBitmap().getWidth() / 2 <= 0) {
            this.getSpeed().toggleXDirection();
        }
        // check collision with bottom wall if heading down
        if (this.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && this.getY() + this.getBitmap().getHeight() / 2 >= height) {
            this.getSpeed().toggleYDirection();
        }
        // check collision with top wall if heading up
        if (this.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && this.getY() - this.getBitmap().getHeight() / 2 <= 0) {
            this.getSpeed().toggleYDirection();
        }

        int x = this.getX();
        int y = this.getY();
        Speed s = this.getSpeed();

        this.setX(x + (int)(s.getXv() * s.getxDirection()));
        this.setY(y + (int)(s.getYv() * s.getyDirection()));
    }

}
