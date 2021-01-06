package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.bsteinka.teamtrainer.data.Match;
import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.data.Team;

public class ViewTeamViewModel extends TeamTrainerViewModel {


    private LiveData<Team> team;
    private int teamId;

    private LiveData<List<Player>> players;
    private LiveData<List<Match>> matches;

    public ViewTeamViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Team> getTeam() {
        return team;
    }

    public int getTeamId() {
        return teamId;
    }

    public void deleteTeam() {
        appRepository.deleteTeam(teamId);
    }

    public void hideTeam() {
        appRepository.hideTeam(teamId);
    }

    public LiveData<List<Player>> getPlayers() {
        return players;
    }

    public LiveData<List<Match>> getMatches() {
        return matches;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
        team = appRepository.getTeam(teamId);
        players = appRepository.getPlayersForTeam(teamId);
        matches = appRepository.getMatchesForTeam(teamId);
    }
}
