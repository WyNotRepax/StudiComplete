package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.data.Team;

public class MainViewModel extends TeamTrainerViewModel {
    private LiveData<List<Team>> allTeams;
    private LiveData<List<Player>> allPlayers;

    public MainViewModel(@NonNull Application application) {
        super(application);
        allTeams = appRepository.getAllTeams();
        allPlayers = appRepository.getAllPlayers();
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }
}
