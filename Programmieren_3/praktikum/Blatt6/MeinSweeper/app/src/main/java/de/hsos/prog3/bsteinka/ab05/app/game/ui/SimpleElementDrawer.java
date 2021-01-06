package de.hsos.prog3.bsteinka.ab05.app.game.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SimpleElementDrawer implements ElementDrawer{

    private final Canvas canvas;

    private final int w,h;

    private final Paint bombPaint;
    private final Paint undiscoveredPaint;
    private final Paint numberPaint;
    private final Paint flagPaint;

    public SimpleElementDrawer(Canvas canvas, int singleFieldWidth,int singleFieldHeight) {
        this.canvas = canvas;
        this.w = singleFieldWidth;
        this.h = singleFieldHeight;
        bombPaint = new Paint();
        bombPaint.setColor(Color.RED);

        undiscoveredPaint = new Paint();
        undiscoveredPaint.setStrokeWidth(3);
        undiscoveredPaint.setColor(Color.BLACK);

        numberPaint = new Paint();
        numberPaint.setStrokeWidth(3);
        numberPaint.setTextSize(w);
        numberPaint.setColor(Color.BLACK);

        flagPaint = new Paint();
        flagPaint.setStrokeWidth(3);
        flagPaint.setColor(Color.GREEN);
    }


    @Override
    public void drawMine(int x, int y) {
        canvas.drawCircle(x+w/2,y+h/2,w/2,bombPaint);
    }

    @Override
    public void drawUndiscovered(int x, int y) {
        canvas.drawLine(x,y,x+w,y+h,undiscoveredPaint);
        canvas.drawLine(x,y+h,x+w,y,undiscoveredPaint);
    }

    @Override
    public void drawNumber(int x, int y, int n) {
        canvas.drawText(String.valueOf(n),x,y+h,numberPaint);
    }

    @Override
    public void drawFlag(int x,int y){
        canvas.drawLine(x,y,x+w,y+h,flagPaint);
        canvas.drawLine(x,y+h,x+w,y,flagPaint);
    }
}
