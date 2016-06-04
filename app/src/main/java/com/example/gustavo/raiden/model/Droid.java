package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Speed;

import java.util.Random;

public class Droid extends Collidable {

	private Bitmap bulletBitmap;
	private Speed speed;    // the speed with its directions
	private AimedBullet bullet;
	private Ship player;
	private int comeBackCounter;
	private int screenHeight, screenWidth;

	public Droid(Bitmap bitmap, Bitmap bullet, int x, int y, Ship ship, int FPS) {
		super(bitmap, x, y, FPS);
		spriteWidth = bitmap.getWidth();
		spriteHeight = bitmap.getHeight();
		bulletBitmap = bullet;
		speed = new Speed();
		player = ship;
		this.bullet = new AimedBullet(bulletBitmap, x + 16, y + 16, FPS);
	}

	public Bitmap getBulletBitmap() {
		return bulletBitmap;
	}

	public void setBulletBitmap(Bitmap bulletBitmap) {
		this.bulletBitmap = bulletBitmap;
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

	public int getComeBackCounter() {
		return comeBackCounter;
	}

	public void setComeBackCounter(int comeBackCounter) {
		this.comeBackCounter = comeBackCounter;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public void draw(Canvas canvas) {
		if (alive) {
			// where to draw the sprite
			Rect destRect = new Rect(getX() - spriteWidth / 2, getY() - spriteHeight / 2,
					getX() + spriteWidth / 2, getY() + spriteHeight / 2);
			canvas.drawBitmap(bitmap, sourceRect, destRect, null);
		}
		if (bullet.isAlive()) {
			bullet.draw(canvas);
		}
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
	public void update() {
		if (comeBackCounter == 0) {
			this.alive = true;
		}
		if (this.alive) {
			//x += (speed.getXv() * speed.getxDirection());
			//y += (speed.getYv() * speed.getyDirection());

			if (!bullet.isAlive()) {
				bullet.setAlive(true);
				int dy, dx;
				double hip;

				dx = Math.abs(this.x - player.getX());
				dy = Math.abs(this.x - player.getY());
				hip = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

				Speed s = new Speed();
				//s.setXv((float)Math.cos(angle) * 20);
				//s.setYv((float)Math.sin(angle) * 20);
				s.setXv((float) (dx / hip * 15));
				s.setYv((float) (dy / hip * 15));
				s.setyDirection(Speed.DIRECTION_DOWN);
				if (this.x > player.getX()) {
					s.setxDirection(Speed.DIRECTION_LEFT);
				} else if (this.x != player.getX()) {
					s.setxDirection(Speed.DIRECTION_RIGHT);
				}

				bullet.setY(this.y + 20);
				bullet.setX(this.x);

				bullet.setSpeed(s);
			}
			//bullet.update(System.currentTimeMillis());
		} else if (comeBackCounter == -1) {
			Random r = new Random();
			setComeBackCounter(r.nextInt(150 - 75 + 1) + 75);
		} else {
			comeBackCounter--;
		}
		if (bullet.isAlive()) {
			bullet.update(System.currentTimeMillis());
		}
	}

	/**
	 * Handles the {link MotionEvent.ACTION_DOWN} event. If the event happens on the
	 * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
	 * @param eventX - the event's X coordinate
	 * @param eventY - the event's Y coordinate
	 *//*
	public void handleActionDown(int eventX, int eventY) {
		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (eventY <= (y + bitmap.getHeight() / 2))) {
				// droid touched
				setTouched(true);
			}
		}
	}*/

}
