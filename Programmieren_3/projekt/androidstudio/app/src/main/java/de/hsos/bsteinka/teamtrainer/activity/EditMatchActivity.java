package de.hsos.bsteinka.teamtrainer.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Match;
import de.hsos.bsteinka.teamtrainer.viewmodel.EditMatchViewModel;

public class EditMatchActivity extends AppCompatActivity {

    private DatePicker matchDate;
    private TextView matchOpponent;
    private TextView matchReferee;
    private TextView matchLocation;
    private Switch matchHomeGame;

    private boolean backPressedOnce = false;

    private EditMatchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);

        matchOpponent = findViewById(R.id.matchOpponent);
        matchDate = findViewById(R.id.matchDate);
        matchReferee = findViewById(R.id.matchReferee);
        matchLocation = findViewById(R.id.matchLocation);
        matchHomeGame = findViewById(R.id.matchHomegame);

        final Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this::confirmButtonOnClick);

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this::cancelButtonOnClick);


        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(EditMatchViewModel.class);
        viewModel.setMatchId(getIntent().getIntExtra(Match.class.getName(), 0));
        viewModel.getMatch().observe(this, this::onMatchChanged);

        matchOpponent.setOnFocusChangeListener((v, hasFocus) -> ((EditText) v).setHintTextColor(getResources().getColor(R.color.colorTextAccent)));
    }

    private void confirmButtonOnClick(View v) {
        EditText opponentEditText = findViewById(R.id.matchOpponent);
        String opponent = opponentEditText.getText().toString();
        String referee = ((EditText) findViewById(R.id.matchReferee)).getText().toString();
        String location = ((EditText) findViewById(R.id.matchLocation)).getText().toString();
        boolean isHomeGame = ((Switch) findViewById(R.id.matchHomegame)).isChecked();
        DatePicker datePicker = findViewById(R.id.matchDate);
        if (viewModel.update(
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

    private void onMatchChanged(Match match) {
        matchOpponent.setText(match.opponent);
        if (match.referee != null) {
            matchReferee.setText(match.referee);
        } else {
            matchReferee.setText("");
        }
        if (match.location != null) {
            matchLocation.setText(match.location);
        } else {
            matchLocation.setText("");
        }
        matchHomeGame.setChecked(match.isHomeGame);
        matchDate.updateDate(match.dateYear, match.dateMonth, match.dateDay);
    }


}
