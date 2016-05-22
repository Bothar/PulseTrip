package model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import edu.ub.pis2016.dmiguel.pulsetrip.R;

/**
 * Created by David on 16/05/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 720;
    public static final int HEIGHT = 360;
    public static final int MOVEMENT_SPEED= -1;
    private MainThread thread;
    private Background bg;
    private Player player;


    public GamePanel(Context context){
        super(context);

        //Add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        //make gamepanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;

        //Sometimes it can take multiples attempts to stop the thread
        while (retry){
            try{
                thread.setRunning(false);
                thread.join();

            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.run_boy), 75, 79, 10);
        //We can start the game loop
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            if (!player.getPlaying()){
                player.setPlaying(true);
            }
            else{
                if (!player.getJumping()){
                    player.setJump(true);
                    player.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jump_boy), 65, 86, 10);
                }

            }
            return true;
        }

        /*if (event.getAction() == MotionEvent.ACTION_UP){
            player.setJump(false);
            player.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.run_boy), 65, 86, 10);
            return true;
        }*/
        return super.onTouchEvent(event);
    }

    public void update(){
            if (player.getPlaying()){
                bg.update();
                player.update();
            }
    }

    @Override
    public void draw(Canvas canvas){

        final float scaleFacotorX = getWidth()/(WIDTH*1f);
        final float scaleFactorY = getHeight()/(HEIGHT*1f);

        if (canvas!= null){
            final int savedState = canvas.save();
            canvas.scale(scaleFacotorX, scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            canvas.restoreToCount(savedState);

        }

    }

}
