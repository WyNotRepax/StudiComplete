package de.hsos.prog3.bsteinka.ab07.swingolfsheet.data;

import java.io.Serializable;

public class Sheet implements Serializable {
    private String name;
    private String[] players;
    private int[][] scores;
    private int holeNum;

    public Sheet(String name, String[] players, int holeNum) {
        this.name = name;
        this.players = players;
        this.scores = new int[players.length][holeNum];
        this.holeNum = holeNum;
    }

    public int getScore(int playerIndex, int holeIndex) {
        return scores[playerIndex][holeIndex];
    }

    public void setScore(int playerIndex, int holeIndex, int value) {
        scores[playerIndex][holeIndex] = value;
    }

    public int getPlayerNum() {
        return players.length;
    }

    public String getPlayer(int playerIndex) {
        return players[playerIndex];
    }

    public int getHoleNum() {
        return holeNum;
    }

    public String getName() {
        return name;
    }

}
