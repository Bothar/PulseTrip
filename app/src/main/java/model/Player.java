package model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import edu.ub.pis2016.dmiguel.pulsetrip.R;


/**
 * Created by David on 17/05/2016.
 */
public class Player extends GameObject {

    private Bitmap spritesheet;
    private int score;
    private double dy;
    private boolean playing;
    private boolean jump;
    private boolean slide;
    private Animation animation = new Animation();
    private long startTIme;

    public Player(Bitmap res, int w, int h, int numFrames){
        x = 50;
        y = GamePanel.HEIGHT-125;
        score = 0;
        height = h;
        width = w;
        dy = 0;

        //We create a Bitmap array that's going to store all the different sprites for the image of the player
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;

        for (int i = 0; i<image.length; i++){
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(10);
        startTIme = System.nanoTime();
    }

    public void setJump(boolean b){
        jump = b;
    }

    public void setSlide(boolean b){
        slide = b;
    }

    public void update(){
        long elapsed = (System.nanoTime()-startTIme)/1000000;
        if (elapsed > 100){
            score++;
            startTIme = System.nanoTime();
        }
        animation.update();

        if(jump){
            y -=10;
            dy++;
        }

        if (dy > 10){
            jump = false;
        }
        if (!jump && dy>0){
            y+=10;
            dy--;
        }


    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public int getScore(){
        return score;
    }

    public boolean getPlaying(){
        return playing;
    }

    public void setPlaying(boolean b){
        playing = b;
    }


    public void resetScore(){
        score = 0;
    }

    public void setBitmap(Bitmap res, int w, int h, int numFrames){
        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i<image.length; i++){
            image[i] = Bitmap.createBitmap(res, i*width, 0, width, height);
        }

        animation.setFrames(image);
        startTIme = System.nanoTime();
    }

    public boolean getJumping(){
        return dy != 0;
    }



}
