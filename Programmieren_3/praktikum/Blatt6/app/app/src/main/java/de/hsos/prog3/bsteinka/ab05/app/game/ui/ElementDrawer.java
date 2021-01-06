package de.hsos.prog3.bsteinka.ab05.app.game.ui;

public interface ElementDrawer {

    void drawMine(int x, int y);
    void drawUndiscovered(int x, int y);
    void drawNumber(int x, int y, int n);
    void drawFlag(int x, int y);
}
