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
		bulletBitmap = bullet;
		speed = new Speed();
		player = ship;
		this.bullet = new AimedBullet(bulletBitmap, x + 16, y + 16, 5, FPS);
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
	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod) {
			frameTicker = gameTime;
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

					dx = Math.abs(this.x - player.getX()+18);
					dy = Math.abs(this.x - player.getY()+19);
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
				setComeBackCounter(r.nextInt(200 - 100 + 1) + 150);
			} else {
				comeBackCounter--;
			}

			bullet.update(System.currentTimeMillis());
		}
	}

}
