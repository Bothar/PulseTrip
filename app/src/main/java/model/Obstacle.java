package model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by David on 26/06/2016.
 */
public class Obstacle extends GameObject {

    private Bitmap res;
    public static int number_of_obstacles = 0;

    public Obstacle(Bitmap res, int w, int h, boolean low){
        this.res = res;
        x=GamePanel.WIDTH;
        if (low) y = GamePanel.HEIGHT-85;
        else {
            y = GamePanel.HEIGHT-155;
        }
        height = h;
        width = w;
        dx = GamePanel.MOVEMENT_SPEED;
        number_of_obstacles++;
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(res, x, y, null);
    }

    public void update(){
        x+=dx;
        if (x > -width){
            number_of_obstacles--;
        }
    }

}
