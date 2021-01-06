package de.hsos.bsteinka.teamtrainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import de.hsos.bsteinka.teamtrainer.R;
import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.data.Team;
import de.hsos.bsteinka.teamtrainer.viewmodel.ViewPlayerViewModel;

public class ViewPlayerActivity extends AppCompatActivity {

    private static final LinearLayout.LayoutParams VISIBLE = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    private static final LinearLayout.LayoutParams INVISIBLE = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
    private ViewPlayerViewModel viewModel;
    private TextView playerTeam;
    private TextView playerFirstName;
    private TextView playerLastName;
    private TextView playerNumber;
    private TextView playerAddress;
    private TextView playerEmail;
    private TextView playerPhone;
    private LinearLayout playerNumberContainer;
    private LinearLayout playerAddressContainer;
    private LinearLayout playerEmailContainer;
    private LinearLayout playerPhoneContainer;

    private static void updateVisibility(LinearLayout l, TextView t, String s) {
        if (s == null) {
            makeInvisible(l, t);
        } else {
            makeVisible(l, t, s);
        }
    }

    private static void updateVisibility(LinearLayout l, TextView t, Integer i) {
        if (i == null) {
            makeInvisible(l, t);
        } else {
            makeVisible(l, t, String.valueOf(i));
        }
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
        setContentView(R.layout.activity_view_player);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        playerTeam = findViewById(R.id.playerTeam);
        playerFirstName = findViewById(R.id.playerFirstName);
        playerLastName = findViewById(R.id.playerLastName);
        playerNumber = findViewById(R.id.playerNumber);
        playerAddress = findViewById(R.id.playerAddress);
        playerEmail = findViewById(R.id.playerEmail);
        playerPhone = findViewById(R.id.playerPhone);
        playerNumberContainer = findViewById(R.id.playerNumberContainer);
        playerAddressContainer = findViewById(R.id.playerAddressContainer);
        playerEmailContainer = findViewById(R.id.playerEmailContainer);
        playerPhoneContainer = findViewById(R.id.playerPhoneContainer);

        int playerId = getIntent().getIntExtra(Player.class.getName(), 0);
        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(ViewPlayerViewModel.class);
        viewModel.setPlayerId(playerId);
        viewModel.getPlayer().observe(this, this::onPlayerDataChanged);
        viewModel.getTeam().observe(this, this::onTeamDataChanged);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_view_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionDeletePlayer:
                viewModel.deletePlayer();
                finish();
                return true;
            case R.id.actionMovePlayer:
                movePlayer();
                return true;
            case R.id.actionEditPlayer:
                editPlayer();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void movePlayer() {
        Intent intent = new Intent(this, MovePlayerActivity.class);
        intent.putExtra(Player.class.getName(), viewModel.getPlayerId());
        startActivity(intent);
    }

    private void editPlayer() {
        Intent intent = new Intent(this, EditPlayerActivity.class);
        intent.putExtra(Player.class.getName(), viewModel.getPlayerId());
        startActivity(intent);
    }

    private void onPlayerDataChanged(Player player) {
        if (player != null) {
            playerFirstName.setText(player.firstName);
            playerLastName.setText(player.lastName);
            updateVisibility(playerNumberContainer, playerNumber, player.number);
            updateVisibility(playerAddressContainer, playerAddress, player.address);
            updateVisibility(playerEmailContainer, playerEmail, player.email);
            updateVisibility(playerPhoneContainer, playerPhone, player.phoneNumber);
        }
    }

    private void onTeamDataChanged(Team team) {
        playerTeam.setText((team != null) ? team.description : "");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
