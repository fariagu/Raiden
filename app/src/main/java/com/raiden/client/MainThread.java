package com.raiden.client;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import java.text.DecimalFormat;

/**
 * The Main thread which contains the game loop.
 * The thread must have access to the surface view and holder to trigger events every game tick.
 */
public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();

    private final static int MAX_FPS = 60; // desired fps
    private final static int MAX_FRAME_SKIPS = 5; // maximum number of frames to be skipped
    private final static int FRAME_PERIOD = 1000 / MAX_FPS; // the frame period
    // we'll be reading the stats every second
    private final static int STAT_INTERVAL = 1000; //ms
    // the average will be calculated by storing
    private final static int FPS_HISTORY_NR = 10; // the last n FPSs
    // Stuff for stats
    private final DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
    private final SurfaceHolder surfaceHolder; // Surface holder that can access the physical surface
    private final MainGamePanel gamePanel;// The actual view that handles inputs, and draws to the surface
    private long lastStatusStore = 0; // last time the status was stored
    private long statusIntervalTimer = 0L; // the status time counter
    private long totalFramesSkipped = 0L; // number of frames skipped since the game started
    private long framesSkippedPerStatCycle = 0L; // number of frames skipped in a store cycle (1 sec)
    private int frameCountPerStatCycle = 0; // number of rendered frames in an interval
    private long totalFrameCount = 0L;
    private double fpsStore[]; // the last FPS values
    private long statsCount = 0; // the number of times the stat has been read
    private boolean running; // flag to hold game state

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        Canvas canvas;
        Log.d(TAG, "Starting game loop");
        initTimingElements();

        long beginTime;     // the time when the cycle begun, iniciar o contador
        long timeDiff;      // the time it took for the cycle to execute, diferenca entre final e inicial
        int sleepTime;      // ms to sleep (<0 if we're behind), comparar
        int framesSkipped;  // number of frames being skipped, diferenca de frames

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

                    try {
                        this.gamePanel.render(canvas); // render state to the screen, draws the canvas on the panel
                    } catch (NullPointerException e) {
                        System.exit(1);
                    }

                    timeDiff = System.currentTimeMillis() - beginTime; // calculate how long did the cycle take
                    sleepTime = (int) (FRAME_PERIOD - timeDiff); // calculate sleep time

                    if (sleepTime > 0) { // if sleepTime > 0 we're OK
                        try {
                            Thread.sleep(sleepTime); // send the thread to sleep for a short period
                        } catch (InterruptedException ignored) {
                        }
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) { // we need to catch up
                        this.gamePanel.update(); // update without rendering
                        sleepTime += FRAME_PERIOD; // add frame period to check if in next frame
                        framesSkipped++;
                    }
                    if (framesSkipped > 0)
                        Log.d(TAG, "Skipped: " + framesSkipped);
                    framesSkippedPerStatCycle += framesSkipped;
                    storeStats();
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }    // end finally
            //running = this.gamePanel.isRunning();
        }
    }

    /**
     * The statistics:
     * Called every cycle
     * Checks if time since last store is greater than the statistics gathering period (1 sec)
     * if so it calculates the FPS for the last period and stores it.
     * <p/>
     * It tracks the number of frames per period.
     * The number of frames since the start of the period are summed up
     * The calculation takes part only if the next period and the frame count is reset to 0.
     */

    private void storeStats() {
        frameCountPerStatCycle++;
        totalFrameCount++;

        statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer); // check the actual time

        if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {
            double actualFps = (double) (frameCountPerStatCycle / (STAT_INTERVAL / 1000)); // calculate the actual frames per status check interval
            fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps; // stores the latest fps in the array
            statsCount++; // increase the number of times statistics was calculated

            double totalFps = 0.0;
            for (int i = 0; i < FPS_HISTORY_NR; i++) { // sum up the stored fps values
                totalFps += fpsStore[i];
            }

            // obtain the average
            double averageFps;
            if (statsCount < FPS_HISTORY_NR) {// in case of the first 10 triggers
                averageFps = totalFps / statsCount;
            } else { // every case after the 10 first
                averageFps = totalFps / FPS_HISTORY_NR;
            }
            totalFramesSkipped += framesSkippedPerStatCycle; // saving the number of total frames skipped
            // resetting the counters after a status record (1 sec)
            framesSkippedPerStatCycle = 0;
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;

            statusIntervalTimer = System.currentTimeMillis();
            lastStatusStore = statusIntervalTimer;
            Log.d(TAG, "Average FPS:" + df.format(averageFps));
            gamePanel.setAvgFps("FPS: " + df.format(averageFps));
        }
    }

    private void initTimingElements() {
        // initialise timing elements
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++) {
            fpsStore[i] = 0.0;
        }
        Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialised");
    }


}
