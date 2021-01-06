package de.hsos.prog3.ab02.test;

import de.hsos.prog3.ab02.logik.Simulator;

import java.lang.reflect.Field;

public class Test {

    public static void main(String[] args) {
        Simulator s = new Simulator();


        s.berechneAnfangsGeneration(10, 50);
        printSpielfeld(getSpielfeld(s));
        s.berechneFolgeGeneration();
        System.out.print("\n\n\n");
        printSpielfeld(getSpielfeld(s));

    }

    public static void printSpielfeld(boolean[][] spielfeld) {
        for (int x = 0; x < spielfeld.length; x++) {
            for (int y = 0; y < spielfeld.length; y++) {
                if (spielfeld[x][y]) {
                    System.out.print("O");
                } else {
                    System.out.print("_");
                }
            }
            System.out.print("\n");
        }
    }

    private static boolean[][] getSpielfeld(Simulator s) {
        try {
            Field field = s.getClass().getDeclaredField("spielfeld");
            field.setAccessible(true);
            return (boolean[][]) field.get(s);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
