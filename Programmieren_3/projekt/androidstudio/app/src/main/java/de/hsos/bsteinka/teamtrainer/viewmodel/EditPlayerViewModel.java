package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import de.hsos.bsteinka.teamtrainer.data.Player;

public class EditPlayerViewModel extends TeamTrainerViewModel {
    private LiveData<Player> player;
    private int playerId;

    public EditPlayerViewModel(Application application) {
        super(application);
    }

    public LiveData<Player> getPlayer() {
        return player;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
        player = appRepository.getPlayer(playerId);
    }


    public boolean update(String firstName, String lastName, String number, String address, String phoneNumber, String email) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            return false;
        }
        Player player = new Player(firstName, lastName);
        player.id = playerId;
        if (this.player.getValue() != null) {
            player.teamId = this.player.getValue().teamId;
        }
        try {
            player.number = Integer.valueOf(number);
        } catch (NumberFormatException e) {
        }
        if (!address.isEmpty()) {
            player.address = address;
        }
        if (!phoneNumber.isEmpty()) {
            player.phoneNumber = phoneNumber;
        }
        if (!email.isEmpty()) {
            player.email = email;
        }
        Log.i(this.getClass().getName(), "Updating player with id:" + player.id);
        appRepository.updatePlayer(player);
        return true;
    }
}
