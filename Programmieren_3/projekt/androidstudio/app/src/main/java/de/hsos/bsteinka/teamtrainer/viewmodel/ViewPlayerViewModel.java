package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.data.Team;

public class ViewPlayerViewModel extends TeamTrainerViewModel {

    private LiveData<Player> player;
    private LiveData<Team> team;
    private int playerId;

    public ViewPlayerViewModel(Application application) {
        super(application);
    }


    public LiveData<Player> getPlayer() {
        return player;
    }


    public void deletePlayer() {
        appRepository.deletePlayer(playerId);
    }


    public void setPlayerId(int playerId) {
        this.playerId = playerId;
        player = appRepository.getPlayer(playerId);
        team = Transformations.switchMap(player, (player) -> appRepository.getTeam((player != null) ? player.teamId : 0));
    }


    public int getPlayerId() {
        return playerId;
    }

    public LiveData<Team> getTeam() {
        return team;

    }
}
