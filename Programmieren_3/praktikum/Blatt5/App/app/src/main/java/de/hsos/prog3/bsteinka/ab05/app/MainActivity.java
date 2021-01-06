package de.hsos.prog3.bsteinka.ab05.app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint paint;
    private Timer timer = new Timer();


    private final int width = 800;
    private final int height = 800;
    private final int textsize = 50;

    private final int grenzeLinks = 30;
    private final int grenzeRechts = 770;
    private final int grenzeOben = 400;
    private final int grenzeUnten = 770;

    private final int ballRadius = 20;
    private float ballX = 100f;
    private float ballY = 700f;
    private float velociteX = 0.3f;
    private float velociteY = 4.5f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(this.bitmap);
        this.imageView = new ImageView(this);
        this.imageView.setImageBitmap(this.bitmap);
        this.paint = new Paint();
        this.setContentView(imageView);
        this.canvas.drawColor(Color.BLUE);
        this.paint.setTextSize(textsize);
        this.halloWelt();
        this.halloNachbar();
        this.zeichneSmiley(100);
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                derSpringendePunkt();
            }
        }, 0, 17);
    }

    private void halloWelt() {
        String text = "Hallo Welt!";
        float textWidth = this.paint.measureText(text);
        this.paint.setColor(Color.WHITE);
        this.canvas.drawText(text, this.width / 2f - textWidth / 2f, 100, this.paint);
    }

    private void halloNachbar() {
        textZentrieren("Hallo Nachbar!", 3 * textsize);
    }

    private void textZentrieren(String text, int y) {
        float textWidth = this.paint.measureText(text);
        this.canvas.drawText(text, this.width / 2f - textWidth / 2f, y, this.paint);
    }


    private void zeichneSmiley(int r) {
        // Hintergrund
        float centerX = this.width / 2f;
        float centerY = this.height / 2f - r -20;
        this.paint.setColor(Color.GREEN);
        this.canvas.drawCircle(centerX, centerY, r, this.paint);

        // Mund
        float smileAng = 120;
        float smileSizeFactor = .8f;
        RectF smileBox = new RectF(
                centerX - (r * smileSizeFactor),
                centerY - (r * smileSizeFactor),
                centerX + (r * smileSizeFactor),
                centerY + (r * smileSizeFactor)
        );

        this.paint.setColor(Color.BLACK);
        this.canvas.drawArc(
                smileBox,
                90 - smileAng / 2f,
                smileAng,
                false,
                this.paint
        );

        //Augen
        float eyeDistanceFactor = .5f;
        float eyeSize = 10;
        this.canvas.drawCircle(
                centerX + r * eyeDistanceFactor,
                centerY - r * eyeDistanceFactor,
                eyeSize, this.paint
        );
        this.canvas.drawCircle(
                centerX - r * eyeDistanceFactor,
                centerY - r * eyeDistanceFactor,
                eyeSize, this.paint
        );
    }

    private void derSpringendePunkt() {
        //Log.i("MainActivity", LocalDateTime.now() + ": der springende Punkt");
        this.paint.setColor(Color.BLUE);
        this.canvas.drawCircle(this.ballX, this.ballY, this.ballRadius, this.paint);
        this.ballX += this.velociteX;
        this.ballY += this.velociteY;
        if (this.ballX > this.grenzeRechts || this.ballX < this.grenzeLinks) {
            this.velociteX *= -1;
        }
        if (this.ballY > this.grenzeUnten || this.ballY < this.grenzeOben) {
            this.velociteY *= -1;
        }
        //Log.i(this.toString(),String.format("ballX:%f, ballY:%f",this.ballX,this.ballY));
        this.paint.setColor(Color.RED);
        this.canvas.drawCircle(this.ballX, this.ballY, this.ballRadius, this.paint);
        this.imageView.invalidate();
    }

}
