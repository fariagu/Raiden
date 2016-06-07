package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.raiden.logic.components.Speed;

import java.util.Random;

public class Droid extends Collidable {

	static private int screenHeight, screenWidth;
	private Bitmap bulletBitmap;
	private final Speed speed;    // the speed with its directions
	private final AimedBullet bullet;
	private Ship player;
	private int absspeed;
	private int comeBackCounter;

	public Droid() {
		super();
		speed = new Speed();
		absspeed = 20;
		this.bullet = new AimedBullet();
	}

	public Droid(Bitmap bitmap, Bitmap bullet, int x, int y, Ship ship, int FPS) {
		super(bitmap, x, y, FPS);
		bulletBitmap = bullet;
		speed = new Speed();
		absspeed = 20;
		player = ship;
		this.bullet = new AimedBullet(bulletBitmap, FPS);
	}

	static public void setScreenHeight(int h) {
		screenHeight = h;
	}

	static public void setScreenWidth(int w) {
		screenWidth = w;
	}

	public Speed getSpeed() {
		return speed;
	}

	public void setAbsspeed(int absspeed) {
		this.absspeed = absspeed;
	}

	public AimedBullet getBullet() {
		return bullet;
	}

	public void setComeBackCounter(int comeBackCounter) {
		this.comeBackCounter = comeBackCounter;
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
