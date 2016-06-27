package model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import edu.ub.pis2016.dmiguel.pulsetrip.R;

/**
 * Created by David on 16/05/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 720;
    public static final int HEIGHT = 360;
    float downY , upY;
    private MainThread thread;
    private Background bg;
    private Player player;
    private ArrayList<Obstacle> obstacles;
    private int[] obstacle_res = {R.drawable.stone, R.drawable.tree_1, R.drawable.saw};


    public GamePanel(Context context){
        super(context);

        obstacles = new ArrayList<>();
        //Add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);

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
            thread = null;

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.run_boy), 75, 79, 10);
        player.setDimensions(65, 79);
        //We can start the game loop
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int MIN_DISTANCE = 50;
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();

                return true;

            case MotionEvent.ACTION_UP:
                if (!player.getPlaying()){
                    player.setPlaying(true);
                }
                upY = event.getY();

                float deltaY = downY - upY;



                if (Math.abs(deltaY) > MIN_DISTANCE) {

                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                }
                break;

        }return super.onTouchEvent(event);
    }

    public void update(){
            if (player.getPlaying()){
                bg.update();
                player.update();
                updateObstacles();



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
            for (Obstacle o : obstacles){
                o.draw(canvas);
            }
            drawScore(canvas);
            canvas.restoreToCount(savedState);

        }

    }
    private void onTopToBottomSwipe(){
        if (!player.getSliding() && !player.getJumping()){
            player.setSlide(true);
            player.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.slide_boy), 75, 70, 10);
            player.setDimensions(65, 70);
        }
    }


    private void onBottomToTopSwipe(){
        if (!player.getJumping() && !player.getSliding()){
            player.setJump(true);
            player.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.jump_boy), 75, 86, 10);
            player.setDimensions(65,70);
        }
    }

    private void updateObstacles() {
        //Generating obstacles
        if (obstacles.size() < 4) {
            if (obstacles.isEmpty() || (obstacles.get(obstacles.size() - 1).getX() < GamePanel.WIDTH - 200 && (new Random().nextInt(10) == 0))) {
                int next = new Random().nextInt(4);
                switch (next) {
                    case 0:
                        obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(), obstacle_res[next]), 67, 40, true));
                        break;
                    case 1:
                        obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(), obstacle_res[next]), 98, 40, true));
                        break;
                    default:
                        obstacles.add(new Obstacle(BitmapFactory.decodeResource(getResources(), obstacle_res[2]), 40, 40, false));
                        break;
                }

            }
        }

        //Removing obstacles out of screen
        for (int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).update();

            if (obstacles.get(i).getX() < -obstacles.get(i).getWidth()) {
                obstacles.remove(i);
            }
            //Checking collision
            if (collision(obstacles.get(i), player)) {
                newGame();
                break;
            } else if (obstacles.get(i).getX() < player.getX() && !obstacles.get(i).getScored()) {
                player.incScore();
                obstacles.get(i).setScored();
                //Incrementing speed
                if (player.getScore()%10 == 0){
                    for (Obstacle o : obstacles){
                        o.incSpeed();
                    }
                    bg.incSpeed();
                }
            }
        }
    }

    private boolean collision(Obstacle obstacle, Player player) {
        if (Rect.intersects(obstacle.getRectangle(), player.getRectangle())){
            return true;
        }
        return false;
    }

    public void newGame(){

        //Clean all obstacles
        obstacles.clear();
        //Reset the background
        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.background));
        //Reset the Player
        player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.run_boy), 75, 79, 10);
        player.setDimensions(65, 79);
        player.setPlaying(false);

        //show score and restart menu


    }

    private void drawScore(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(String.valueOf(player.getScore()),WIDTH/2, 100, paint);

    }
}


