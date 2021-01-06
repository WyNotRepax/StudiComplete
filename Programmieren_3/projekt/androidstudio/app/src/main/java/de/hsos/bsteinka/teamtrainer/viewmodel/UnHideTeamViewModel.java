package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.bsteinka.teamtrainer.data.Team;

public class UnHideTeamViewModel extends TeamTrainerViewModel {

    private LiveData<List<Team>> allTeams;


    public UnHideTeamViewModel(Application application) {
        super(application);
        allTeams = appRepository.getHiddenTeams();
    }

    public void unHide(int teamId) {
        appRepository.unHideTeam(teamId);
    }

    public void unHideAll() {
        appRepository.unHideAllTeams();
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }


}
