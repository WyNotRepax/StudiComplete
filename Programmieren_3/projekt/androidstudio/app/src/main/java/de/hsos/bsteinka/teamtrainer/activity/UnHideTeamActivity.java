package de.hsos.bsteinka.teamtrainer.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.view.TeamListAdapter;
import de.hsos.bsteinka.teamtrainer.viewmodel.UnHideTeamViewModel;

public class UnHideTeamActivity extends AppCompatActivity {

    UnHideTeamViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unhide_team);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView teamList = findViewById(R.id.teamList);
        TeamListAdapter adapter = new TeamListAdapter(this);
        adapter.setOnViewHolderClickHandler(this::onTeamListClicked);
        teamList.setAdapter(adapter);
        teamList.setLayoutManager(new LinearLayoutManager(this));

        ViewModelProvider.Factory factory = new ViewModelProvider.AndroidViewModelFactory(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(UnHideTeamViewModel.class);
        viewModel.getAllTeams().observe(this, adapter::setTeamList);
    }

    private void onTeamListClicked(TeamListAdapter.ViewHolder viewHolder) {
        viewModel.unHide(viewHolder.getTeamId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_unhide, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionUnHideAll:
                viewModel.unHideAll();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
