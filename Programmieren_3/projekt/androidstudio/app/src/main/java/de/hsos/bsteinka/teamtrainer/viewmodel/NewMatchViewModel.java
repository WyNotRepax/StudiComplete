package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import de.hsos.bsteinka.teamtrainer.data.Match;

public class NewMatchViewModel extends TeamTrainerViewModel {

    private int teamId;

    public NewMatchViewModel(@NonNull Application application) {
        super(application);
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean insert(String opponent, String referee, String location, boolean isHomeGame, int dateYear, int dateMonth, int dateDay) {
        Log.i(this.getClass().getName(), "TeamId:" + teamId);
        if (opponent.isEmpty()) {
            return false;
        }
        Match match = new Match(opponent);
        match.teamId = this.teamId;
        if (!referee.isEmpty()) {
            match.referee = referee;
        }
        if (!location.isEmpty()) {
            match.location = location;
        }
        match.isHomeGame = isHomeGame;
        match.dateYear = dateYear;
        match.dateMonth = dateMonth;
        match.dateDay = dateDay;
        appRepository.insertMatch(match);
        return true;
    }
}
