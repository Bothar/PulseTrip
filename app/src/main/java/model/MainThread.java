package model;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by David on 16/05/2016.
 */
public class MainThread extends Thread {

    private int FPS = 30;
    private double avarageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long totalTime = 0;
        int frameCount = 0;
        //  1s/FPS
        long targetTime = 1000/FPS;

        while(running){
            startTime = System.nanoTime();
            canvas = null;

            //try locking the canvas for pixel editing
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    gamePanel.update();
                    gamePanel.draw(canvas);
                }
            }catch (Exception e){}
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e) {e.printStackTrace();}
                }
            }

            //That's the time it took us to update and draw
            timeMillis = (System.nanoTime() - startTime)/1000000;

            //The time we wait it the time we aim - the time it took to update and draw
            waitTime = targetTime - timeMillis;
            try{
                sleep(waitTime);
            }catch (Exception e){}

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if (frameCount == FPS){
                avarageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
            }
        }
    }

    public void setRunning(boolean b){
        running = b;
    }

}
