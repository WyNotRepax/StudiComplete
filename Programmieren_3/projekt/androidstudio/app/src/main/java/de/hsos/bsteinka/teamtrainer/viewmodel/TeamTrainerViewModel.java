package de.hsos.bsteinka.teamtrainer.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import de.hsos.bsteinka.teamtrainer.data.AppRepository;

public abstract class TeamTrainerViewModel extends AndroidViewModel {
    protected final AppRepository appRepository;

    protected TeamTrainerViewModel(Application application) {
        super(application);
        appRepository = new AppRepository(application);
    }
}
