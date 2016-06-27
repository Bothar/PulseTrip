package model;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by David on 27/06/2016.
 */
public class BonusItem extends GameObject {

    private Bitmap res;

    public BonusItem(Bitmap res, int w, int h, int speed) {
        this.res = res;
        x = GamePanel.WIDTH;
        y = randomNum(GamePanel.HEIGHT - 190,GamePanel.HEIGHT - 65);
        height = h;
        width = w;
        dx = speed;
    }

    public void draw(Canvas canvas) {

        canvas.drawBitmap(res, x, y, null);
    }

    public void update() {
        x += dx;
    }

    public void incSpeed() {
        if (dx > -20) dx--;
    }

    public void decSpeed(){
        if (dx < -10) dx++;
    }

    private int randomNum(int low, int high){
        return  new Random().nextInt(high-low) + low;
    }
}
