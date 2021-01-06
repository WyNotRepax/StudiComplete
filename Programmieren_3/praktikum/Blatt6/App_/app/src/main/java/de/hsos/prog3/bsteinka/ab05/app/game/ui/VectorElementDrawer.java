package de.hsos.prog3.bsteinka.ab05.app.game.ui;

import android.content.res.Resources;
import android.graphics.Canvas;

import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import de.hsos.prog3.bsteinka.ab05.app.R;

public class VectorElementDrawer implements ElementDrawer {

    private final Canvas canvas;

    private final int w, h;

    private final VectorDrawableCompat mine;
    private final VectorDrawableCompat undiscovered;
    private final VectorDrawableCompat flag;

    private final VectorDrawableCompat number1;
    private final VectorDrawableCompat number2;
    private final VectorDrawableCompat number3;
    private final VectorDrawableCompat number4;
    private final VectorDrawableCompat number5;
    private final VectorDrawableCompat number6;
    private final VectorDrawableCompat number7;
    private final VectorDrawableCompat number8;

    public VectorElementDrawer(Canvas canvas, int singleFieldWidth, int singleFieldHeight, Resources res) {
        this.canvas = canvas;
        this.w = singleFieldWidth;
        this.h = singleFieldHeight;

        // Setup mine Image
        mine = VectorDrawableCompat.create(res, R.drawable.ic_mine, null);
        mine.setBounds(0, 0, w, h);

        // Setup undiscoverd Image
        undiscovered = VectorDrawableCompat.create(res, R.drawable.ic_background_tile, null);
        undiscovered.setBounds(0, 0, w, h);

        // Setup flag Image
        flag = VectorDrawableCompat.create(res, R.drawable.ic_flag, null);
        flag.setBounds(0, 0, w, h);

        // Setup number Images
        number1 = VectorDrawableCompat.create(res, R.drawable.ic_tile_1, null);
        number1.setBounds(0, 0, w, h);
        number2 = VectorDrawableCompat.create(res, R.drawable.ic_tile_2, null);
        number2.setBounds(0, 0, w, h);
        number3 = VectorDrawableCompat.create(res, R.drawable.ic_tile_3, null);
        number3.setBounds(0, 0, w, h);
        number4 = VectorDrawableCompat.create(res, R.drawable.ic_tile_4, null);
        number4.setBounds(0, 0, w, h);
        number5 = VectorDrawableCompat.create(res, R.drawable.ic_tile_5, null);
        number5.setBounds(0, 0, w, h);
        number6 = VectorDrawableCompat.create(res, R.drawable.ic_tile_6, null);
        number6.setBounds(0, 0, w, h);
        number7 = VectorDrawableCompat.create(res, R.drawable.ic_tile_7, null);
        number7.setBounds(0, 0, w, h);
        number8 = VectorDrawableCompat.create(res, R.drawable.ic_tile_8, null);
        number8.setBounds(0, 0, w, h);
    }

    @Override
    public void drawMine(int x, int y) {
        draw(mine,x,y);
    }

    @Override
    public void drawUndiscovered(int x, int y) {
        draw(undiscovered,x,y);
    }

    @Override
    public void drawNumber(int x, int y, int n) {
        VectorDrawableCompat number = getImageForNumber(n);
        if (number != null) {
            draw(number,x,y);
        }
    }

    @Override
    public void drawFlag(int x, int y) {
        draw(undiscovered,x,y);
        draw(flag,x,y);
    }

    private VectorDrawableCompat getImageForNumber(int n) {
        switch (n) {
            case 1:
                return number1;
            case 2:
                return number2;
            case 3:
                return number3;
            case 4:
                return number4;
            case 5:
                return number5;
            case 6:
                return number6;
            case 7:
                return number7;
            case 8:
                return number8;
            default:
                return null;
        }
    }

    private void draw(VectorDrawableCompat img, int x, int y){
        canvas.save();
        canvas.translate(x, y);
        img.draw(canvas);
        canvas.restore();
    }
}
