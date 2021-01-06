package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import de.hsos.bsteinka.teamtrainer.data.Match;
import de.hsos.bsteinka.teamtrainer.data.Team;

public class ViewMatchViewModel extends TeamTrainerViewModel {
    private LiveData<Match> match;
    private LiveData<Team> team;
    private int matchId;

    public ViewMatchViewModel(Application application) {
        super(application);
    }


    public LiveData<Match> getMatch() {
        return match;
    }


    public void deleteMatch() {
        appRepository.deleteMatch(matchId);
    }


    public void setMatchId(int matchId) {
        this.matchId = matchId;
        match = appRepository.getMatch(matchId);
        team = Transformations.switchMap(match, (match) -> appRepository.getTeam((match != null) ? match.teamId : 0));
    }


    public int getMatchId() {
        return matchId;
    }

    public LiveData<Team> getTeam() {
        return team;
    }

    public void update(int score, int otherScore) {
        appRepository.updateMatchScore(matchId, score, otherScore);
    }
}
