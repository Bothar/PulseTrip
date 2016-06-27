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
    private double dy, dx;
    private boolean playing;
    private boolean jump;
    private boolean slide=false;
    private Animation animation = new Animation();
    private long startTIme;
    private boolean newAnim;

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
        newAnim = true;
    }

    public void setSlide(boolean b){
        slide = b;
        y+=16;
        dx = 0;
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
        if (dy == 0 && newAnim){
            setBitmap(spritesheet, 75, 79, 10);
            setDimensions(65,79);
            newAnim = false;
        }
        if (slide){
            dx += 1;
            if (dx > 12){
                slide = false;
                y-=16;
                setBitmap(spritesheet, 75, 79, 10);
                setDimensions(65,79);
            }
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
            image[i] = Bitmap.createBitmap(res, i*w, 0, w, h);
        }

        animation.setFrames(image);
        startTIme = System.nanoTime();
    }

    public boolean getJumping(){
        return dy != 0;
    }

    public boolean getSliding(){
        return slide;
    }

    public void setDimensions(int h, int w){
        height = h;
        width = w;
    }

}
