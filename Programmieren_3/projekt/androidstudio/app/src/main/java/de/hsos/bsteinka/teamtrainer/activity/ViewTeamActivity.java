package de.hsos.bsteinka.teamtrainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Match;
import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.data.Team;
import de.hsos.bsteinka.teamtrainer.view.MatchListAdapter;
import de.hsos.bsteinka.teamtrainer.view.PlayerListAdapter;
import de.hsos.bsteinka.teamtrainer.viewmodel.ViewTeamViewModel;

public class ViewTeamActivity extends AppCompatActivity {

    private ViewTeamViewModel viewModel;

    private TextView teamDescription;
    private TextView teamClub;
    private TextView teamSport;
    private PopupWindow deletePopup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_team);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int teamId = getIntent().getIntExtra(Team.class.getName(), 0);

        teamDescription = findViewById(R.id.teamDescription);
        teamClub = findViewById(R.id.teamClub);
        teamSport = findViewById(R.id.teamSport);

        TabHost tabs = findViewById(R.id.tabs);
        tabs.setup();
        TabHost.TabSpec spec = tabs.newTabSpec("playerList");
        spec.setContent(R.id.playerList);
        spec.setIndicator(getResources().getString(R.string.player_list_indicator));
        tabs.addTab(spec);
        spec = tabs.newTabSpec("matchList");
        spec.setContent(R.id.matchList);
        spec.setIndicator(getResources().getString(R.string.match_list_indicator));
        tabs.addTab(spec);


        RecyclerView playerList = findViewById(R.id.playerList);
        PlayerListAdapter playerListAdapter = new PlayerListAdapter(this);
        playerListAdapter.setOnViewHolderClickHandler(this::onPlayerListClicked);
        playerList.setAdapter(playerListAdapter);
        playerList.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView matchList = findViewById(R.id.matchList);
        MatchListAdapter matchListAdapter = new MatchListAdapter(this);
        matchListAdapter.setOnViewHolderClickHandler(this::onMatchListClicked);
        matchList.setAdapter(matchListAdapter);
        matchList.setLayoutManager(new LinearLayoutManager(this));

        final DividerItemDecoration playerListItemDecoration = new DividerItemDecoration(playerList.getContext(), DividerItemDecoration.VERTICAL);
        playerListItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        playerList.addItemDecoration(playerListItemDecoration);

        final DividerItemDecoration matchListItemDecoration = new DividerItemDecoration(playerList.getContext(), DividerItemDecoration.VERTICAL);
        matchListItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        matchList.addItemDecoration(matchListItemDecoration);


        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(ViewTeamViewModel.class);
        viewModel.setTeamId(teamId);
        viewModel.getTeam().observe(this, this::onTeamDataChanged);
        viewModel.getPlayers().observe(this, playerListAdapter::setPlayerList);
        viewModel.getMatches().observe(this, matchListAdapter::setMatchList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_view_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionDeleteTeam:
                showDeletePopup();
                return true;
            case R.id.actionAddPlayer:
                addPlayer();
                return true;
            case R.id.actionEditTeam:
                editTeam();
                return true;
            case R.id.actionAddMatch:
                addMatch();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addPlayer() {
        Intent intent = new Intent(ViewTeamActivity.this, NewPlayerActivity.class);
        intent.putExtra(Team.class.getName(), viewModel.getTeamId());
        startActivity(intent);
    }

    private void editTeam() {
        Intent intent = new Intent(ViewTeamActivity.this, EditTeamActivity.class);
        intent.putExtra(Team.class.getName(), viewModel.getTeamId());
        startActivity(intent);
    }

    private void onTeamDataChanged(Team team) {
        if (team != null) {
            teamDescription.setText(team.description);
            teamSport.setText(team.sport);
            teamClub.setText(team.club);
        }
        Log.i(this.getClass().getName(), "TeamId: " + viewModel.getTeamId());
    }

    private void onPlayerListClicked(PlayerListAdapter.ViewHolder viewHolder) {
        Intent intent = new Intent(this, ViewPlayerActivity.class);
        intent.putExtra(Player.class.getName(), viewHolder.getPlayerId());
        startActivity(intent);
    }

    private void onMatchListClicked(MatchListAdapter.ViewHolder viewHolder) {
        Intent intent = new Intent(this, ViewMatchActivity.class);
        intent.putExtra(Match.class.getName(), viewHolder.getMatchId());
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void showDeletePopup() {
        ViewGroup root = findViewById(R.id.root);
        View popupView = getLayoutInflater().inflate(R.layout.popup_delete_team, null);
        deletePopup = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        deletePopup.setFocusable(true);
        deletePopup.showAtLocation(root, Gravity.CENTER, 0, 0);
        popupView.findViewById(R.id.cancelButton).setOnClickListener(v -> deletePopup.dismiss());
        popupView.findViewById(R.id.actionDelete).setOnClickListener(this::delete);
        popupView.findViewById(R.id.actionHide).setOnClickListener(this::hide);
    }

    private void delete(View v) {
        viewModel.deleteTeam();
        deletePopup.dismiss();
        finish();
    }

    private void hide(View v) {
        viewModel.hideTeam();
        deletePopup.dismiss();
        finish();
    }

    private void addMatch() {
        Log.i(this.getClass().getName(), "TeamId intent:" + viewModel.getTeamId());
        Intent intent = new Intent(this, NewMatchActivity.class);
        intent.putExtra(Match.class.getName(), viewModel.getTeamId());
        startActivity(intent);
    }

}
