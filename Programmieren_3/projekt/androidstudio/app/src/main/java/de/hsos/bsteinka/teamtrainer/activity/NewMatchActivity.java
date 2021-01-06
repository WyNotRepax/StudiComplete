package de.hsos.bsteinka.teamtrainer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Match;
import de.hsos.bsteinka.teamtrainer.viewmodel.NewMatchViewModel;

public class NewMatchActivity extends AppCompatActivity {


    private boolean backPressedOnce = false;

    private NewMatchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        int teamId = getIntent().getIntExtra(Match.class.getName(), 0);

        final Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this::confirmButtonOnClick);

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this::cancelButtonOnClick);

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(NewMatchViewModel.class);
        viewModel.setTeamId(teamId);

        EditText opponentEditText = findViewById(R.id.matchOpponent);
        opponentEditText.setOnFocusChangeListener((v, hasFocus) -> ((EditText) v).setHintTextColor(getResources().getColor(R.color.colorTextAccent)));
    }

    private void confirmButtonOnClick(View v) {
        EditText opponentEditText = findViewById(R.id.matchOpponent);
        String opponent = opponentEditText.getText().toString();
        String referee = ((EditText) findViewById(R.id.matchReferee)).getText().toString();
        String location = ((EditText) findViewById(R.id.matchLocation)).getText().toString();
        boolean isHomeGame = ((Switch) findViewById(R.id.matchHomegame)).isChecked();
        DatePicker datePicker = findViewById(R.id.matchDate);
        if (viewModel.insert(
                opponent,
                referee,
                location,
                isHomeGame,
                datePicker.getYear(),
                datePicker.getMonth(),
                datePicker.getDayOfMonth())
        ) {
            finish();
        } else {
            opponentEditText.setHintTextColor(Color.RED);
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


}
