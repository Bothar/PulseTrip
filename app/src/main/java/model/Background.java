package model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by David on 17/05/2016.
 */
public class Background {
    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res){
        image = res;
        dx =-10;
    }

    public void update(){
        x+=dx;
        if (x<-GamePanel.WIDTH){
            x=0;
        }
    }

    public void draw(Canvas canvas){


        canvas.drawBitmap(image, x, y, null);
        if (x<0){
            canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
        }
    }

    public void incSpeed(){
        if (dx > -20) dx--;
    }

    public void decSpeed(){
        if (dx < -10) dx++;
    }




}
