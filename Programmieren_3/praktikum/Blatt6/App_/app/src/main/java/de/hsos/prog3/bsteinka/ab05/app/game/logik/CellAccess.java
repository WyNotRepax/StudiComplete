package de.hsos.prog3.bsteinka.ab05.app.game.logik;

public interface CellAccess {
    enum Cellstate {EMPTY,BOMB}
    enum Displaystate{DISCOVERED,UNDISCOVERED,FLAGGED}
    int getWidth();
    int getHeight();
    Cellstate getCellState(int x, int y);
    Displaystate getDisplayState(int x, int y);
    int countBombs(int x, int y);
}
