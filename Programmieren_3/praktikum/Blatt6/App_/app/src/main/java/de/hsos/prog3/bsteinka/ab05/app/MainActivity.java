package de.hsos.prog3.bsteinka.ab05.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.ImageView;

import de.hsos.prog3.bsteinka.ab05.app.game.Game;

public class MainActivity extends Activity {

    private Game game;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = new ImageView(this);
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        game = new Game(size,imageView,getResources());
        this.imageView.setBackgroundColor(Color.WHITE);
        this.setContentView(imageView);
        game.init();
    }


}
