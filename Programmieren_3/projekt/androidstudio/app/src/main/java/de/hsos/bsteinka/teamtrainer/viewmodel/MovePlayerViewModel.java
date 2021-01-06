package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.bsteinka.teamtrainer.data.Team;

public class MovePlayerViewModel extends TeamTrainerViewModel {

    LiveData<List<Team>> allTeams;
    private int playerId;


    public MovePlayerViewModel(Application application) {
        super(application);
        allTeams = appRepository.getAllTeams();
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void moveTo(int teamId) {
        appRepository.updatePlayerTeam(playerId, teamId);
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }


}
