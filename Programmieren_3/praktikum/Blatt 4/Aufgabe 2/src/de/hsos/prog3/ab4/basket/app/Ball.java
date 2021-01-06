package de.hsos.prog3.ab4.basket.app;

import de.hsos.prog3.ab4.basket.util.Interaktionsbrett;

public class Ball {
    private static final String NAME = "Ball";

    private final Interaktionsbrett ib;
    private float x, y;
    private final float startX,startY;
    private int breite, hoehe;
    private final double speed;
    private double xSpeed, ySpeed;
    private int minY, maxY;

    Ball(Interaktionsbrett ib, int x, int y, int breite, int hoehe, double speed, int minY, int maxY) {
        this.x = x - breite / 2;
        this.y = y - hoehe / 2;

        this.startX = this.x;
        this.startY = this.y;

        this.breite = breite;
        this.hoehe = hoehe;

        this.minY = minY;
        this.maxY = maxY-hoehe;

        this.ib = ib;
        this.speed = speed;
        double angle = Math.random() * Math.PI * 2;
        xSpeed = Math.sin(angle) * speed / 1000;
        ySpeed = Math.cos(angle) * speed / 1000;
        ib.neuesRechteck(this, NAME, (int)this.x, (int)this.y, breite, hoehe);
    }

    public void update(int delta) {
        System.out.println(xSpeed);
        x += delta * xSpeed;
        y += delta * ySpeed;
        if (y < minY) {
            y = (y - minY) * -1 + minY;
            ySpeed *= -1;
        } else if (y > maxY) {
            y = (y - maxY) * -1 + maxY;
            ySpeed *= -1;
        }
        ib.verschiebeObjektNach(this, NAME, (int)x, (int)y);
    }

    public void reset(){
        this.x = startX;
        this.y = startY;

        double angle = Math.random() * Math.PI * 2;
        xSpeed = Math.sin(angle) * speed / 1000;
        ySpeed = Math.cos(angle) * speed / 1000;
    }
}
