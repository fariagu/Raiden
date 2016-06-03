package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.gustavo.raiden.model.components.Speed;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 */
public class Droid {

	private Bitmap bitmap;	// the actual bitmap
	private Bitmap bulletBitmap;
	private int x, y;			// the X and Y coordinate
	private boolean touched;	// if droid is touched/picked up
	private boolean alive;
	private Speed speed;	// the speed with its directions
	private AimedBullet bullet;
	private Ship player;

	public Droid(Bitmap bitmap, Bitmap bullet, int x, int y, Ship ship, int FPS) {
		this.bitmap = bitmap;
		this.bulletBitmap = bullet;
		this.x = x;
		this.y = y;
		this.speed = new Speed();
		this.player = ship;
		this.bullet = new AimedBullet(bulletBitmap, x + 16, y + 16, player, FPS);
		this.alive = true;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public Bitmap getBulletBitmap() {
		return bulletBitmap;
	}
	public void setBulletBitmap(Bitmap bulletBitmap) {
		this.bulletBitmap = bulletBitmap;
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
	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public Speed getSpeed() {
		return speed;
	}
	public void setSpeed(Speed speed) {
		this.speed = speed;
	}
	public AimedBullet getBullet() {
		return bullet;
	}
	public void setBullet(AimedBullet bullet) {
		this.bullet = bullet;
	}
	public Ship getPlayer() {
		return player;
	}
	public void setPlayer(Ship player) {
		this.player = player;
	}

	public void draw(Canvas canvas) {
		if (this.alive) {
			canvas.drawBitmap(bulletBitmap, bullet.getX() - (bulletBitmap.getWidth() / 2), bullet.getY() - (bulletBitmap.getHeight() / 2), null);
			canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
		}
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
		if (!touched) {
			x += (speed.getXv() * speed.getxDirection());
			y += (speed.getYv() * speed.getyDirection());
		}

		if (bullet.isAlive()){
			bullet.update(System.currentTimeMillis());
		}
		else {
			bullet.setAlive(true);
			int dy, dx;
			double  hip;

			dx = Math.abs(this.x - player.getX());
			dy = Math.abs(this.x - player.getY());
			hip = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

			Speed s = new Speed();
			//s.setXv((float)Math.cos(angle) * 20);
			//s.setYv((float)Math.sin(angle) * 20);
			s.setXv((float)(dx / hip * 10));
			s.setYv((float)(dy / hip * 10));
			s.setyDirection(Speed.DIRECTION_DOWN);
			if (this.x > player.getX()){
				s.setxDirection(Speed.DIRECTION_LEFT);
			}
			else if (this.x != player.getX()){
				s.setxDirection(Speed.DIRECTION_RIGHT);
			}

			bullet.setY(this.y+20);
			bullet.setX(this.x);

			bullet.setSpeed(s);

		}
	}
	
	/**
	 * Handles the {link MotionEvent.ACTION_DOWN} event. If the event happens on the
	 * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
	 * @param eventX - the event's X coordinate
	 * @param eventY - the event's Y coordinate
	 */
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2))) {
				// droid touched
				setTouched(true);
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
