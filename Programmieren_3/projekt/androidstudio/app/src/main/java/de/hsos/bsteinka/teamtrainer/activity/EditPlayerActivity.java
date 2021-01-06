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
import de.hsos.bsteinka.teamtrainer.data.Player;
import de.hsos.bsteinka.teamtrainer.viewmodel.EditPlayerViewModel;

public class EditPlayerActivity extends AppCompatActivity {

    private EditText playerFirstName;
    private EditText playerLastName;
    private EditText playerNumber;
    private EditText playerAddress;
    private EditText playerPhone;
    private EditText playerEmail;

    private boolean backPressedOnce = false;

    private EditPlayerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);

        playerFirstName = findViewById(R.id.playerFirstName);
        playerLastName = findViewById(R.id.playerLastName);
        playerNumber = findViewById(R.id.playerNumber);
        playerAddress = findViewById(R.id.playerAddress);
        playerPhone = findViewById(R.id.playerPhone);
        playerEmail = findViewById(R.id.playerEmail);

        final Button confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this::confirmButtonOnClick);

        final Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(this::cancelButtonOnClick);


        ViewModelProvider.Factory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider viewModelProvider = new ViewModelProvider(getViewModelStore(), factory);
        viewModel = viewModelProvider.get(EditPlayerViewModel.class);
        viewModel.setPlayerId(getIntent().getIntExtra(Player.class.getName(), 0));
        viewModel.getPlayer().observe(this, this::onPlayerChanged);

        playerFirstName.setOnFocusChangeListener((v, hasFocus) -> ((EditText) v).setHintTextColor(getResources().getColor(R.color.colorTextAccent)));
        playerLastName.setOnFocusChangeListener((v, hasFocus) -> ((EditText) v).setHintTextColor(getResources().getColor(R.color.colorTextAccent)));
    }

    private void confirmButtonOnClick(View v) {
        EditText firstNameEditText = findViewById(R.id.playerFirstName);
        EditText lastNameEditText = findViewById(R.id.playerLastName);
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        String number = ((EditText) findViewById(R.id.playerNumber)).getText().toString();
        String address = ((EditText) findViewById(R.id.playerAddress)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.playerPhone)).getText().toString();
        String email = ((EditText) findViewById(R.id.playerEmail)).getText().toString();
        if (viewModel.update(firstName, lastName, number, address, phoneNumber, email)) {
            finish();
        } else {
            if (firstName.isEmpty()) {
                firstNameEditText.setHintTextColor(Color.RED);
                lastNameEditText.setHintTextColor(Color.RED);
            }
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

    private void onPlayerChanged(Player player) {
        playerFirstName.setText(player.firstName);
        playerLastName.setText(player.lastName);
        if (player.number != null) {
            playerNumber.setText(String.valueOf(player.number));
        } else {
            playerNumber.setText("");
        }
        if (player.address != null) {
            playerAddress.setText(player.address);
        } else {
            playerAddress.setText("");
        }
        if (player.phoneNumber != null) {
            playerPhone.setText(player.phoneNumber);
        } else {
            playerPhone.setText("");
        }
        if (player.email != null) {
            playerEmail.setText(player.email);
        } else {
            playerEmail.setText("");
        }
    }
}
