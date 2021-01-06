package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;

import de.hsos.bsteinka.teamtrainer.data.Player;

public class NewPlayerViewModel extends TeamTrainerViewModel {

    private int teamId;

    public NewPlayerViewModel(@NonNull Application application) {
        super(application);
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public boolean insert(String firstName, String lastName, String number, String address, String phoneNumber, String email) {
        if (firstName.isEmpty() || lastName.isEmpty()) {
            return false;
        }
        Player player = new Player(firstName, lastName);
        player.teamId = teamId;
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
        appRepository.insertPlayer(player);
        return true;
    }
}
