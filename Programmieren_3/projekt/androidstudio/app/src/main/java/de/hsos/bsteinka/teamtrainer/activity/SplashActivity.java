package de.hsos.bsteinka.teamtrainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import de.hsos.bsteinka.teamtrainer.R;

public class SplashActivity extends AppCompatActivity {

    private boolean started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(this::startMainActivity, getResources().getInteger(R.integer.timeoutDelay));
        findViewById(R.id.splashscreen).setOnClickListener(this::onClick);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (started) {
            finish();
        }
    }

    public void onClick(View v) {
        startMainActivity();
    }

    private void startMainActivity() {
        if (!started) {
            started = true;
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
