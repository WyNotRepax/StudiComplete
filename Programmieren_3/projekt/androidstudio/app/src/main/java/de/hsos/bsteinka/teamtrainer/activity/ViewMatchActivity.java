package de.hsos.bsteinka.teamtrainer.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Match;
import de.hsos.bsteinka.teamtrainer.data.Team;
import de.hsos.bsteinka.teamtrainer.viewmodel.ViewMatchViewModel;

public class ViewMatchActivity extends AppCompatActivity {

    private static final LinearLayout.LayoutParams VISIBLE = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private static final LinearLayout.LayoutParams INVISIBLE = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
    private ViewMatchViewModel viewModel;
    private TextView teamDescription;
    private TextView matchOpponent;
    private EditText matchScore;
    private EditText matchOpponentScore;
    private TextView matchDate;
    private TextView matchReferee;
    private TextView matchLocation;
    private TextView matchHomeGame;
    private LinearLayout matchDateContainer;
    private LinearLayout matchRefereeContainer;
    private LinearLayout matchLocationContainer;

    private static void updateVisibility(LinearLayout l, TextView t, String s) {
        if (s == null) {
            makeInvisible(l, t);
        } else {
            makeVisible(l, t, s);
        }
    }

    private static void updateVisibility(LinearLayout l, TextView t, int dateYear, int dateMonth, int dateDay) {
        updateVisibility(l, t, String.format(Locale.getDefault(), Match.DATEFORMAT, dateDay, dateMonth + 1, dateYear));
    }

    private static void makeInvisible(LinearLayout l, TextView t) {
        l.setVisibility(View.INVISIBLE);
        l.setLayoutParams(INVISIBLE);
        t.setText("");
    }

    private static void makeVisible(LinearLayout l, TextView t, String s) {
        l.setVisibility(View.VISIBLE);
        l.setLayoutParams(VISIBLE);
        t.setText(s);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_match);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        teamDescription = findViewById(R.id.teamDescription);
        matchOpponent = findViewById(R.id.matchOpponent);
        matchScore = findViewById(R.id.matchScore);
        matchOpponentScore = findViewById(R.id.matchOpponentScore);
        matchDate = findViewById(R.id.matchDate);
        matchReferee = findViewById(R.id.matchReferee);
        matchLocation = findViewById(R.id.matchLocation);
        matchHomeGame = findViewById(R.id.matchHomegame);
        matchDateContainer = findViewById(R.id.matchDateContainer);
        matchRefereeContainer = findViewById(R.id.matchRefereeContainer);
        matchLocationContainer = findViewById(R.id.matchLocationContainer);

        int matchId = getIntent().getIntExtra(Match.class.getName(), 0);
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(ViewMatchViewModel.class);
        viewModel.setMatchId(matchId);
        viewModel.getMatch().observe(this, this::onMatchDataChanged);
        viewModel.getTeam().observe(this, this::onTeamDataChanged);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_view_match, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionDeleteMatch:
                viewModel.deleteMatch();
                finish();
                return true;
            case R.id.actionEditMatch:
                editMatch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editMatch() {
        /*
        Intent intent = new Intent(this, EditMatchActivity.class);
        intent.putExtra(Match.class.getName(), viewModel.getMatchId());
        startActivity(intent);*/
    }

    private void onMatchDataChanged(Match match) {
        if (match != null) {
            matchOpponent.setText(match.opponent);
            matchScore.setText(String.valueOf(match.score));
            matchOpponentScore.setText(String.valueOf(match.opponentScore));
            if (match.isHomeGame) {
                matchHomeGame.setText(R.string.match_homegame_true);
            } else {
                matchHomeGame.setText(R.string.match_homegame_false);
            }
            updateVisibility(matchDateContainer, matchDate, match.dateYear, match.dateMonth, match.dateDay);
            updateVisibility(matchLocationContainer, matchLocation, match.location);
            updateVisibility(matchRefereeContainer, matchReferee, match.referee);
        }
    }

    private void onTeamDataChanged(Team team) {
        teamDescription.setText((team != null) ? team.description : "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        int score;
        try {
            score = Integer.parseInt(matchScore.getText().toString());
        } catch (NumberFormatException e) {
            score = 0;
        }

        int opponentScore;
        try {
            opponentScore = Integer.parseInt(matchOpponentScore.getText().toString());
        } catch (NumberFormatException e) {
            opponentScore = 0;
        }

        viewModel.update(score, opponentScore);
        super.onBackPressed();
    }
}
