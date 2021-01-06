package de.hsos.bsteinka.teamtrainer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Team;
import de.hsos.bsteinka.teamtrainer.viewmodel.EditTeamViewModel;

public class EditTeamActivity extends AppCompatActivity {

    private boolean backPressedOnce = false;

    private EditTeamViewModel viewModel;

    private EditText descriptionEditText;
    private EditText clubEditText;
    private EditText sportEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);

        descriptionEditText = findViewById(R.id.teamDescription);
        clubEditText = findViewById(R.id.teamClub);
        sportEditText = findViewById(R.id.teamSport);

        final Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this::confirmButtonOnClick);

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this::cancelButtonOnClick);


        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(EditTeamViewModel.class);
        viewModel.setTeamId(getIntent().getIntExtra(Team.class.getName(), 0));
        viewModel.getTeam().observe(this, this::onTeamChanged);


        EditText descriptionEditText = findViewById(R.id.teamDescription);
        descriptionEditText.setOnFocusChangeListener((v, hasFocus) -> ((EditText) v).setHintTextColor(getResources().getColor(R.color.colorTextAccent)));
    }

    private void confirmButtonOnClick(View v) {
        String description = descriptionEditText.getText().toString();
        String club = clubEditText.getText().toString();
        String sport = sportEditText.getText().toString();
        if (viewModel.update(description, club, sport)) {
            finish();
        } else {
            descriptionEditText.setHintTextColor(Color.RED);
        }
    }

    private void cancelButtonOnClick(View v) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed();
        }
        Toast toast = Toast.makeText(getApplicationContext(), R.string.warning_back, Toast.LENGTH_SHORT);
        toast.show();
        backPressedOnce = true;
        new Handler().postDelayed(() -> backPressedOnce = false, 2000);
    }

    private void onTeamChanged(Team team) {
        descriptionEditText.setText(team.description);
        sportEditText.setText(team.club);
        clubEditText.setText(team.sport);
    }
}