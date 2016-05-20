package com.example.gustavo.raiden.model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.example.gustavo.raiden.R;
import com.example.gustavo.raiden.model.components.Speed;

import static com.example.gustavo.raiden.R.drawable.hld;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 */
public class Droid {

	private Bitmap bitmap;	// the actual bitmap
	private int x, y;			// the X and Y coordinate
	private Speed speed;	// the speed with its directions
	private Context context;
	
	public Droid(int x, int y, Context c) {
		this.x = x;
		this.y = y;
		this.speed = new Speed();
		this.context = c;
		this.bitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.hld);
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
	
	public Speed getSpeed() {
		return speed;
	}

	public void setSpeed(Speed speed) {
		this.speed = speed;
	}

	public void shoot(){
		Bullet b = new Bullet(this.x, this.y, this.context, this);
	}

	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
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

		x += (speed.getXv() * speed.getxDirection());
		y += (speed.getYv() * speed.getyDirection());
	}
}
