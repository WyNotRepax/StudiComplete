package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;

import de.hsos.bsteinka.teamtrainer.data.Team;


public class EditTeamViewModel extends TeamTrainerViewModel {

    private LiveData<Team> team;
    private int teamId;

    public EditTeamViewModel(Application application) {
        super(application);
    }

    public LiveData<Team> getTeam() {
        return team;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
        team = appRepository.getTeam(teamId);
    }

    public boolean update(String description, String club, String sport) {
        if (description.isEmpty()) {
            return false;
        }
        Team team = new Team(description, club, sport);
        team.id = teamId;
        appRepository.updateTeam(team);
        return true;
    }
}
