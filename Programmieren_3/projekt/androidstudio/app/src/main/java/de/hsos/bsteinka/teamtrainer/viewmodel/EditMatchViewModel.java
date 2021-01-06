package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import de.hsos.bsteinka.teamtrainer.data.Match;

public class EditMatchViewModel extends TeamTrainerViewModel {
    private LiveData<Match> match;
    private int matchId;

    public EditMatchViewModel(Application application) {
        super(application);
    }

    public LiveData<Match> getMatch() {
        return match;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
        match = appRepository.getMatch(matchId);
    }


    public boolean update(String opponent, String referee, String location, boolean isHomeGame, int dateYear, int dateMonth, int dateDay) {
        if (opponent.isEmpty()) {
            return false;
        }
        Match match = new Match(opponent);
        if (this.match.getValue() != null) {
            match.teamId = this.match.getValue().id;
        }
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

