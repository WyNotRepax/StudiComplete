package de.hsos.bsteinka.teamtrainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.data.Team;
import de.hsos.bsteinka.teamtrainer.view.PlayerListAdapter;
import de.hsos.bsteinka.teamtrainer.view.TeamListAdapter;
import de.hsos.bsteinka.teamtrainer.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        final TabHost tabs = findViewById(R.id.tabs);
        tabs.setup();
        {
            TabHost.TabSpec spec = tabs.newTabSpec("teamList");
            spec.setIndicator(getResources().getString(R.string.team_list_indicator));
            spec.setContent(R.id.teamList);
            tabs.addTab(spec);
        }
        {
            TabHost.TabSpec spec = tabs.newTabSpec("playerList");
            spec.setIndicator(getResources().getString(R.string.player_list_indicator));
            spec.setContent(R.id.playerList);
            tabs.addTab(spec);
        }

        final RecyclerView teamList = findViewById(R.id.teamList);
        final TeamListAdapter teamListAdapter = new TeamListAdapter(this);
        teamListAdapter.setOnViewHolderClickHandler(this::onTeamListClicked);
        teamList.setAdapter(teamListAdapter);
        teamList.setLayoutManager(new LinearLayoutManager(this));

        final RecyclerView playerList = findViewById(R.id.playerList);
        final PlayerListAdapter playerListAdapter = new PlayerListAdapter(this);
        playerListAdapter.setOnViewHolderClickHandler(this::onPlayerListClicked);
        playerList.setAdapter(playerListAdapter);
        playerList.setLayoutManager(new LinearLayoutManager(this));


        final DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(teamList.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getDrawable(R.drawable.divider));
        teamList.addItemDecoration(dividerItemDecoration);
        playerList.addItemDecoration(dividerItemDecoration);

        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(MainViewModel.class);
        viewModel.getAllTeams().observe(this, teamListAdapter::setTeamList);
        viewModel.getAllPlayers().observe(this, playerListAdapter::setPlayerList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionAddTeam:
                newTeam();
                return true;
            case R.id.actionUnHideTeam:
                unHideTeam();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void newTeam() {
        Intent intent = new Intent(MainActivity.this, NewTeamActivity.class);
        startActivity(intent);
    }


    private void onPlayerListClicked(PlayerListAdapter.ViewHolder viewHolder) {
        Intent intent = new Intent(this, ViewPlayerActivity.class);
        intent.putExtra(Player.class.getName(), viewHolder.getPlayerId());
        startActivity(intent);
    }

    private void onTeamListClicked(TeamListAdapter.ViewHolder viewHolder) {
        Intent intent = new Intent(this, ViewTeamActivity.class);
        intent.putExtra(Team.class.getName(), viewHolder.getTeamId());
        startActivity(intent);
    }

    private void unHideTeam() {
        Intent intent = new Intent(this, UnHideTeamActivity.class);
        startActivity(intent);
    }


}
