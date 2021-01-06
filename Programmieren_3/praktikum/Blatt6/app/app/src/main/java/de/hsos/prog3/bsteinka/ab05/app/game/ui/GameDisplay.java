package de.hsos.prog3.bsteinka.ab05.app.game.ui;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import de.hsos.prog3.bsteinka.ab05.app.game.logik.CellAccess;

public class GameDisplay {
    private final Canvas canvas;
    private final CellAccess cells;
    private final Paint backgroundPaintFill;
    private final Paint backgroundPaintStroke;
    private Point fieldSize;
    private float xStep;
    private float yStep;

    private final ElementDrawer drawer;

    public GameDisplay(CellAccess cells, Canvas canvas, Resources res) {
        this.canvas = canvas;
        this.cells = cells;

        backgroundPaintFill = new Paint();
        backgroundPaintFill.setStyle(Paint.Style.FILL);
        backgroundPaintFill.setColor(Color.GRAY);
        backgroundPaintFill.setStrokeWidth(1);

        backgroundPaintStroke = new Paint();
        backgroundPaintStroke.setStyle(Paint.Style.STROKE);
        backgroundPaintStroke.setColor(Color.BLACK);
        backgroundPaintStroke.setStrokeWidth(5);

        calcFieldSize();

        drawer = new VectorElementDrawer(canvas, (int) xStep, (int) yStep, res);

    }

    private void calcFieldSize() {
        float fieldRatio = (float) cells.getWidth() / (float) cells.getHeight();
        float canvasRatio = (float) canvas.getWidth() / (float) canvas.getHeight();
        Log.i("calcFieldSize", String.format("fieldRatio: %d/%d=%f", cells.getWidth(), cells.getHeight(), fieldRatio));
        Log.i("calcFieldSize", String.format("canvasRatio: %d/%d=%f", canvas.getWidth(), canvas.getHeight(), canvasRatio));
        if (fieldRatio < canvasRatio) {
            Log.i("calcFieldSize", "Screen Height is Limiting!");
            fieldSize = new Point((int) (canvas.getHeight() * fieldRatio), canvas.getHeight());
        } else {
            Log.i("calcFieldSize", "Screen Width is Limiting!");
            fieldSize = new Point(canvas.getWidth(), (int) (canvas.getWidth() * fieldRatio));
        }
        xStep = fieldSize.x / (float) cells.getWidth();
        yStep = fieldSize.y / (float) cells.getHeight();
        canvas.translate((canvas.getWidth() - fieldSize.x) / 2, (canvas.getHeight() - fieldSize.y) / 2);
    }

    public void update() {
        canvas.drawColor(Color.LTGRAY);
        drawBackground();
        for (int x = 0; x < cells.getWidth(); x++) {
            for (int y = 0; y < cells.getHeight(); y++) {
                int screenX = (int) (x * xStep);
                int screenY = (int) (y * yStep);
                switch (cells.getDisplayState(x, y)) {
                    case DISCOVERED:
                        if (cells.getCellState(x, y) == CellAccess.Cellstate.BOMB) {
                            drawer.drawMine(screenX, screenY);
                        } else {
                            drawer.drawNumber(screenX, screenY, cells.countBombs(x, y));
                        }
                        break;
                    case FLAGGED:
                        drawer.drawFlag(screenX,screenY);
                        break;
                    case UNDISCOVERED:
                        drawer.drawUndiscovered(screenX,screenY);
                        break;
                }
            }
        }
    }


    private void drawBackground() {
        canvas.drawRect(new Rect(0, 0, fieldSize.x, fieldSize.y), backgroundPaintFill);
        canvas.drawRect(new Rect(0, 0, fieldSize.x, fieldSize.y), backgroundPaintStroke);
        for (int x = 0; x < cells.getWidth(); x++) {
            canvas.drawLine(x * xStep, 0, x * xStep, fieldSize.y, backgroundPaintStroke);
        }
        for (int y = 0; y < cells.getHeight(); y++) {
            canvas.drawLine(0, y * yStep, fieldSize.x, y * yStep, backgroundPaintStroke);
        }
    }

    public Point canvasToField(Point p) {
        int x = p.x;
        x -= (canvas.getWidth() - fieldSize.x) / 2;
        x *= cells.getWidth();
        x /= fieldSize.x;
        int y = p.y;
        y -= (canvas.getHeight() - fieldSize.y) / 2;
        y *= cells.getHeight();
        y /= fieldSize.y;
        return new Point(x, y);
    }
}
