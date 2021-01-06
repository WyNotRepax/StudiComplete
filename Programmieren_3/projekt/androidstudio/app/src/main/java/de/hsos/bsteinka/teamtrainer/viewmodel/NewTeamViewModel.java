package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import de.hsos.bsteinka.teamtrainer.data.Team;

public class NewTeamViewModel extends TeamTrainerViewModel {


    public NewTeamViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean insert(String description, String club, String sport) {
        if (description.isEmpty()) {
            return false;
        }
        Team team = new Team(description, club, sport);
        appRepository.insertTeam(team);
        return true;
    }


}
