package de.hsos.prog3.bsteinka.ab05.app.game.logik;

import android.graphics.Point;
import android.util.Log;


public class GameLogic implements CellAccess {

    private final int width;
    private final int height;
    private final int bombNum;

    enum State{ENDED,NORMAL,FLAGGING}
    private State state;

    private Cellstate[][] cellstates;
    private Displaystate[][] displaystates;

    public GameLogic(int width, int height, int bombNum) {
        Log.d("test", "Hallo von GameLogic");
        this.width = width;
        this.height = height;
        this.cellstates = new Cellstate[width][height];
        this.displaystates = new Displaystate[width][height];
        this.bombNum = bombNum;
        this.init();
    }

    private void init() {
        Log.d("test", "Hallo von GameLogic init");
        state = State.NORMAL;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cellstates[x][y] = Cellstate.EMPTY;
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                displaystates[x][y] = Displaystate.UNDISCOVERED;
            }
        }
        int planted = 0;
        while (planted < bombNum) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            if (cellstates[x][y] == Cellstate.EMPTY) {
                cellstates[x][y] = Cellstate.BOMB;
                planted++;
                Log.i(this.getClass().getName(), String.format("Bomb plantet at %s,%s!", String.valueOf(x), String.valueOf(y)));
            }
        }
        Log.d("test", "Hallo vom ende von GameLogic init");
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Cellstate getCellState(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return cellstates[x][y];
        }
        return null;
    }

    @Override
    public Displaystate getDisplayState(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return displaystates[x][y];
        }
        return null;
    }

    @Override
    public int countBombs(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                    if (cellstates[x + dx][y + dy] == Cellstate.BOMB) {
                        count++;
                    }
                }
            }
        }
        return count;
    }


    public void click(Point p) {
        switch (state){
            case ENDED:
                init();
                break;
            case NORMAL:
                normalClick(p);
                break;
            case FLAGGING:
                flagClick(p);
                break;
        }
        if(hasEnded()){
            state = State.ENDED;
        }

    }

    private void normalClick(Point p){
            Log.i("Click", String.format("%d,%d is now true", p.x, p.y));
        if (p.x >= 0 && p.x < width && p.y >= 0 && p.y < height) {
            if(displaystates[p.x][p.y] == Displaystate.UNDISCOVERED){
                discover(p.x,p.y);
            }
        }
        else{
            state = State.FLAGGING;
        }
    }

    public void flagClick(Point p){
        if (p.x >= 0 && p.x < width && p.y >= 0 && p.y < height) {
            if(displaystates[p.x][p.y] == Displaystate.UNDISCOVERED){
                displaystates[p.x][p.y] = Displaystate.FLAGGED;
            }
            else if(displaystates[p.x][p.y] == Displaystate.FLAGGED){
                displaystates[p.x][p.y] = Displaystate.UNDISCOVERED;
            }
        }
        else{
            state = State.NORMAL;
        }
    }

    private void discoverBombs() {
        for (int x = 0; x < displaystates.length; x++) {
            for (int y = 0; y < displaystates[x].length; y++) {
                if(cellstates[x][y] == Cellstate.BOMB){
                    displaystates[x][y] = Displaystate.DISCOVERED;
                }
            }
        }
    }

    private void discover(int x, int y){
        if (cellstates[x][y] == Cellstate.BOMB){
            discoverBombs();
            state = State.ENDED;
            return;
        }
        displaystates[x][y] = Displaystate.DISCOVERED;
        if(countBombs(x,y) == 0){
            for(int dx = -1; dx <= 1; dx++){
                for(int dy = -1; dy <= 1; dy++){
                    if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                        if(displaystates[x+dx][y+dy]==Displaystate.UNDISCOVERED){
                            discover(x+dx,y+dy);
                        }
                    }
                }
            }
        }
    }

    private boolean hasEnded(){
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                switch (displaystates[x][y]){
                    case UNDISCOVERED:
                        return false;
                    case FLAGGED:
                        if(cellstates[x][y] == Cellstate.EMPTY)return false;
                }
            }
        }

        return true;
    }
}
