package de.hsos.prog3.bsteinka.ab05.app.game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import de.hsos.prog3.bsteinka.ab05.app.R;
import de.hsos.prog3.bsteinka.ab05.app.game.logik.GameLogic;
import de.hsos.prog3.bsteinka.ab05.app.game.ui.GameDisplay;

public class Game {
    private final ImageView imageView;
    private final Bitmap bitmap;
    private final Canvas canvas;
    private final Resources res;
    private GameLogic gameLogic;
    private GameDisplay gameDisplay;

    private int xCells;
    private int yCells;
    private int bombNum;

    private static final int clickThreshold = 500;

    public Game(Point size, ImageView imageView, Resources res) {
        Log.d("test", "Hallo von Game");
        this.imageView = imageView;
        this.bitmap = Bitmap.createBitmap(size.x, size.y, Bitmap.Config.ARGB_8888);
        imageView.setImageBitmap(bitmap);
        this.canvas = new Canvas(this.bitmap);
        this.res = res;
        this.bombNum = res.getInteger(R.integer.bombNumber);
        this.xCells = res.getInteger(R.integer.width);
        this.yCells = res.getInteger(R.integer.height);
        imageView.setOnTouchListener(this::onTouch);
    }

    public void init() {
        Log.d("test", "Hallo von Game init!");
        gameLogic = new GameLogic(xCells, yCells, bombNum);
        gameDisplay = new GameDisplay(gameLogic, canvas, res);
        update();
    }

    public void update() {
        this.gameDisplay.update();
        this.imageView.invalidate();
    }


    public boolean onTouch(View v, MotionEvent e) {
        if (e.getActionMasked() == MotionEvent.ACTION_DOWN) {
            Log.i("Touch", "Action UP");
            Point onField = gameDisplay.canvasToField(new Point((int) e.getX(), (int) e.getY()));
            Log.i("Touch", String.format("%f,%f -> %d,%d", e.getX(), e.getY(), onField.x, onField.y));
            gameLogic.click(onField);
            update();
            return true;
        }
        return false;
    }


}
