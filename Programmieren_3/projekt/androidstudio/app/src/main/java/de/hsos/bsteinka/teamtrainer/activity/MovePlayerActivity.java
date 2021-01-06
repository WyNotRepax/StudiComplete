package de.hsos.bsteinka.teamtrainer.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.view.TeamListAdapter;
import de.hsos.bsteinka.teamtrainer.viewmodel.MovePlayerViewModel;

public class MovePlayerActivity extends AppCompatActivity {

    MovePlayerViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_team);

        int playerId = getIntent().getIntExtra(Player.class.getName(), 0);

        RecyclerView teamList = findViewById(R.id.teamList);
        TeamListAdapter adapter = new TeamListAdapter(this);
        adapter.setOnViewHolderClickHandler(this::onTeamListClicked);
        teamList.setAdapter(adapter);
        teamList.setLayoutManager(new LinearLayoutManager(this));

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(MovePlayerViewModel.class);
        viewModel.setPlayerId(playerId);
        viewModel.getAllTeams().observe(this, adapter::setTeamList);
    }

    private void onTeamListClicked(TeamListAdapter.ViewHolder viewHolder) {
        viewModel.moveTo(viewHolder.getTeamId());
        finish();
    }
}
