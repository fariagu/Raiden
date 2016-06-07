package com.raiden.logic;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.raiden.logic.components.Speed;

import java.util.Random;

/**
 * The enemies in the game.
 */
public class Droid extends Collidable {

	static private int screenHeight, screenWidth;
	private final Speed speed;    // the speed with its directions
	private final AimedBullet bullet;
	private Bitmap bulletBitmap;
	private Ship player;
	private int absspeed;
	private int comeBackCounter;

	/**
	 * Default constructor of the Droid.
	 */
	public Droid() {
		super();
		speed = new Speed();
		absspeed = 20;
		this.bullet = new AimedBullet();
	}

	/**
	 * Construtor do Droid.
	 * @param bitmap
	 * @param bullet
	 * @param x
	 * @param y
	 * @param ship
	 * @param FPS
	 */
	public Droid(Bitmap bitmap, Bitmap bullet, int x, int y, Ship ship, int FPS) {
		super(bitmap, x, y, FPS);
		bulletBitmap = bullet;
		speed = new Speed();
		absspeed = 20;
		player = ship;
		this.bullet = new AimedBullet(bulletBitmap, FPS);
	}

	/**
	 * Setter to change the Screen Height stored in the Droid.
	 *
	 * @param h screen Height
	 */
	static public void setScreenHeight(int h) {
		screenHeight = h;
	}

	/**
	 * Setter to change the Screen Height stored in the Droid.
	 * @param w screen Width
	 */
	static public void setScreenWidth(int w) {
		screenWidth = w;
	}

	/**
	 * Setter to change the Speed of the Droid with a x velocity and a y velocity and the direction.
	 * @return
	 */
	public Speed getSpeed() {
		return speed;
	}

	/**
	 * Setter to change the speed in both x and y directions.
	 * @param absspeed the new speed to both directions
	 */
	public void setAbsspeed(int absspeed) {
		this.absspeed = absspeed;
	}

	/**
	 * Getter for the Bullet the Droid is shooting.
	 * @return
	 */
	public AimedBullet getBullet() {
		return bullet;
	}

	/**
	 * Setter to change the amount of time the Droid will take to revive.
	 * @param comeBackCounter time
	 */
	public void setComeBackCounter(int comeBackCounter) {
		this.comeBackCounter = comeBackCounter;
	}

	/**
	 * Draws the bitmap of the Droid to the screen of the mobile and invokes the draw of his bullet.
	 * @param canvas mobile screen
	 */
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
	 * @param gameTime the gametime to know if there should be an update.
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
