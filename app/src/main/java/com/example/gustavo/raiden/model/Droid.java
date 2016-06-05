package com.example.gustavo.raiden.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.gustavo.raiden.model.components.Speed;

import java.util.Random;

public class Droid extends Collidable {

	static private int screenHeight, screenWidth;
	private Bitmap bulletBitmap;
	private Speed speed;    // the speed with its directions
	private int absspeed;
	private AimedBullet bullet;
	private Ship player;
	private int comeBackCounter;

	public Droid(Bitmap bitmap, Bitmap bullet, int x, int y, Ship ship, int FPS) {
		super(bitmap, x, y, FPS);
		bulletBitmap = bullet;
		speed = new Speed();
		absspeed = 20;
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

	public int getAbsspeed() {
		return absspeed;
	}

	public void setAbsspeed(int absspeed) {
		this.absspeed = absspeed;
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

	static public void setScreenHeight(int h) {
		screenHeight = h;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	static public void setScreenWidth(int w) {
		screenWidth = w;
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
				alive = true;
			}
			if (alive) {
				x += (speed.getXv() * speed.getxDirection());
				y += (speed.getYv() * speed.getyDirection());

				if (!bullet.isAlive()) {
					bullet.setAlive(true);
					int dy, dx;
					double hip;

					dx = Math.abs(x - player.getX() + 18);
					dy = Math.abs(y - player.getY() + 19);
					hip = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

					Speed s = new Speed();
					s.setXv((float) (dx / hip * absspeed));
					s.setYv((float) (dy / hip * absspeed));
					s.setyDirection(Speed.DIRECTION_DOWN);
					if (x > player.getX()) {
						s.setxDirection(Speed.DIRECTION_LEFT);
					} else {
						s.setxDirection(Speed.DIRECTION_RIGHT);
					}

					bullet.setY(this.y + 20);
					bullet.setX(this.x);

					bullet.setSpeed(s);
				}
				//bullet.update(System.currentTimeMillis());
			} else if (comeBackCounter == -1) {
				//making a new ship
				Random r = new Random();
				setComeBackCounter(r.nextInt(200 - 100 + 1) + 50);
				speed.setXv(speed.getXv() + (absspeed / 15));
				speed.setYv(speed.getYv() + (absspeed / 15));
				if (r.nextInt(1) == 0)
					speed.setxDirection(Speed.DIRECTION_LEFT);
				else
					speed.setxDirection(Speed.DIRECTION_RIGHT);
				if (r.nextInt(1) == 0)
					speed.setyDirection(Speed.DIRECTION_UP);
				else
					speed.setyDirection(Speed.DIRECTION_DOWN);
				do {
					x = r.nextInt(screenWidth - bitmap.getWidth()) + bitmap.getWidth() / 2;
				} while ((x >= player.getX() - 200) && (x <= player.getX() + 200));
				do {
					y = r.nextInt((screenHeight * 2 / 3) - bitmap.getHeight()) + bitmap.getHeight() / 2;
				} while ((y >= player.getY() - 200) && (y <= player.getY() + 200));
			} else {
				comeBackCounter--;
			}

			bullet.update(System.currentTimeMillis());
		}
	}

}
