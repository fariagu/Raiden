package com.example.gustavo.raiden;

import android.graphics.Canvas;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.TextView;

/**
 * The Main thread which contains the game loop.
 * The thread must have access to the surface view and holder to trigger events every game tick.
 */
public class MainThread extends Thread {
	
	private static final String TAG = MainThread.class.getSimpleName();

	// Surface holder that can access the physical surface
	private SurfaceHolder surfaceHolder;
	// The actual view that handles inputs
	// and draws to the surface
	private MainGamePanel gamePanel;

	// flag to hold game state 
	private boolean running;
	public void setRunning(boolean running) {

		this.running = running;
	}

	public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	// desired fps
	private final static int    MAX_FPS = 60;
	// maximum number of frames to be skipped
	private final static int    MAX_FRAME_SKIPS = 6;
	// the frame period
	private final static int    FRAME_PERIOD = 1000 / MAX_FPS;

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");

		long beginTime;     // the time when the cycle begun, iniciar o contador
		long timeDiff;      // the time it took for the cycle to execute, diferenca entre final e inicial
		int sleepTime;      // ms to sleep (<0 if we're behind), comparar
		int framesSkipped;  // number of frames being skipped, diferenca de frames

		sleepTime = 0;

		while (running) {
			canvas = null;
			// try locking the canvas for exclusive pixel editing
			// in the surface
			try {
				canvas = this.surfaceHolder.lockCanvas();
				synchronized (surfaceHolder) {
					beginTime = System.currentTimeMillis();
					framesSkipped = 0;  // resetting the frames skipped

					this.gamePanel.update(); // update game state

					this.gamePanel.render(canvas); // render state to the screen, draws the canvas on the panel

					timeDiff = System.currentTimeMillis() - beginTime; // calculate how long did the cycle take
					sleepTime = (int)(FRAME_PERIOD - timeDiff); // calculate sleep time

					if (sleepTime > 0) { // if sleepTime > 0 we're OK
						try {
							Thread.sleep(sleepTime); // send the thread to sleep for a short period
						} catch (InterruptedException e) {}
					}

					while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) { // we need to catch up
						this.gamePanel.update(); // update without rendering
						sleepTime += FRAME_PERIOD; // add frame period to check if in next frame
						framesSkipped++;
					}
				}
			} finally {
				// in case of an exception the surface is not left in 
				// an inconsistent state
				if (canvas != null) {
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}	// end finally
		}
	}
	
}
