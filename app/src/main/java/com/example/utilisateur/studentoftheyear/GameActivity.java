package com.example.utilisateur.studentoftheyear;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private TextView score, start;
    private ImageView avatar, zero, note1, note2, note3, note4, note5, note6, note7, note8, note9, note10, bonus, temps;
    private SensorManager sensorManager;
    private Sensor mAccelerometer;

    private int avatarY;
    private int zeroX, zeroY;
    private int note1X, note1Y;
    private int note2X, note2Y;
    private int note3X, note3Y;
    private int note4X, note4Y;
    private int note5X, note5Y;
    private int note6X, note6Y;
    private int note7X, note7Y;
    private int note8X, note8Y;
    private int note9X, note9Y;
    private int note10X, note10Y;
    private int bonusX, bonusY;
    private int tempsX, tempsY;

    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private boolean action_flag, start_flag = false;

    private int frameH;
    private int avatarSize, noteSize;
    private int scrw;

    private float point = 0;
    private float moyenne;
    private int nbNote = 0;

    LocationManager locationManager;
    float longitude, latitude;

    String name;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().hide();

        score = (TextView) findViewById(R.id.tv_score);
        start = (TextView) findViewById(R.id.tv_start);
        avatar = (ImageView) findViewById(R.id.iv_avatar);
        zero = (ImageView) findViewById(R.id.iv_zero);
        note1 = (ImageView) findViewById(R.id.iv_un);
        note2 = (ImageView) findViewById(R.id.iv_deux);
        note3 = (ImageView) findViewById(R.id.iv_trois);
        note4 = (ImageView) findViewById(R.id.iv_quatre);
        note5 = (ImageView) findViewById(R.id.iv_cinq);
        note6 = (ImageView) findViewById(R.id.iv_six);
        note7 = (ImageView) findViewById(R.id.iv_sept);
        note8 = (ImageView) findViewById(R.id.iv_huit);
        note9 = (ImageView) findViewById(R.id.iv_neuf);
        note10 = (ImageView) findViewById(R.id.iv_dix);
        bonus = (ImageView) findViewById(R.id.iv_plus);
        temps = (ImageView) findViewById(R.id.iv_temps);

        //Cacher les notes
        zero.setY(-100.0f);
        zero.setX(-100.0f);
        note1.setY(-100.0f);
        note1.setX(-100.0f);
        note2.setY(-100.0f);
        note2.setX(-100.0f);
        note3.setY(-100.0f);
        note3.setX(-100.0f);
        note4.setY(-100.0f);
        note4.setX(-100.0f);
        note5.setY(-100.0f);
        note5.setX(-100.0f);
        note6.setY(-100.0f);
        note6.setX(-100.0f);
        note7.setY(-100.0f);
        note7.setX(-100.0f);
        note8.setY(-100.0f);
        note8.setX(-100.0f);
        note9.setY(-100.0f);
        note9.setX(-100.0f);
        note10.setY(-100.0f);
        note10.setX(-100.0f);
        bonus.setY(-100.0f);
        bonus.setX(-100.0f);
        temps.setY(-100.0f);
        temps.setX(-100.0f);

        //Sensor OK
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Taille écran
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        scrw = size.x;

        //Afficher message de départ
        start.setVisibility(View.VISIBLE);

        //Obtenir position
        getPosition();

        //Obtenir infos départ
        name = getIntent().getStringExtra("name");
        score.setText("Bonne Chance " + name + "!");
        choice = getIntent().getIntExtra("choice", 0);
        switch (choice) {
            case 1:
                avatar.setImageResource(R.drawable.oldm);
                break;
            case 2:
                avatar.setImageResource(R.drawable.man);
                break;
            case 3:
                avatar.setImageResource(R.drawable.boy);
                break;
            case 4:
                avatar.setImageResource(R.drawable.oldw);
                break;
            case 5:
                avatar.setImageResource(R.drawable.woman);
                break;
            case 6:
                avatar.setImageResource(R.drawable.girl);
                break;

        }

    }

    //Lancer le jeu au toucher
    public boolean onTouchEvent(MotionEvent me) {

        if (start_flag == false) {
            start_flag = true;

            FrameLayout frame = (FrameLayout) findViewById(R.id.fl_frame);
            frameH = frame.getHeight();

            avatarSize = avatar.getHeight();
            noteSize = note1.getHeight();
            avatarY = frameH / 2;

            start.setVisibility(View.GONE);
            avatar.setVisibility(View.VISIBLE);

            score.setText("Moyenne = 0");

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            startGame();
                        }
                    });

                }
            }, 0, 20);
        }
        return super.onTouchEvent(me);
    }

    //Gerer les hits
    public void hitCheck() {
        if (zeroX < avatarSize && zeroY < avatarY + avatarSize && zeroY > avatarY - noteSize) {
            point += 0;
            zeroX = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note1X < avatarSize && note1Y < avatarY + avatarSize && note1Y > avatarY - noteSize) {
            point += 1;
            note1X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note2X < avatarSize && note2Y < avatarY + avatarSize && note2Y > avatarY - noteSize) {
            point += 2;
            note2X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note3X < avatarSize && note3Y < avatarY + avatarSize && note3Y > avatarY - noteSize) {
            point += 3;
            note3X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note4X < avatarSize && note4Y < avatarY + avatarSize && note4Y > avatarY - noteSize) {
            point += 4;
            note4X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note5X < avatarSize && note5Y < avatarY + avatarSize && note5Y > avatarY - noteSize) {
            point += 5;
            note5X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note6X < avatarSize && note6Y < avatarY + avatarSize && note6Y > avatarY - noteSize) {
            point += 6;
            note6X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note7X < avatarSize && note7Y < avatarY + avatarSize && note7Y > avatarY - noteSize) {
            point += 7;
            note7X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note8X < avatarSize && note8Y < avatarY + avatarSize && note8Y > avatarY - noteSize) {
            point += 8;
            note8X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note9X < avatarSize && note9Y < avatarY + avatarSize && note9Y > avatarY - noteSize) {
            point += 9;
            note9X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (note10X < avatarSize && note10Y < avatarY + avatarSize && note10Y > avatarY - noteSize) {
            point += 10;
            note10X = -10;
            nbNote++;
            moyenne = point / nbNote;
        }

        if (bonusX < avatarSize && bonusY < avatarY + avatarSize && bonusY > avatarY - noteSize) {
            point += 1;
            bonusX = -10;
            moyenne = point / nbNote;
        }

        if (tempsX < avatarSize && tempsY < avatarY + avatarSize && tempsY > avatarY - noteSize) {
            moyenne = point / 30;
            gameOver();
        }
    }

    //Deroulement du jeu
    //Placer les notes aleatoirement et le deplacer
    public void startGame() {
        if (nbNote == 30) gameOver();

        hitCheck();

        zeroX -= 5;
        if (zeroX < 0) {
            zeroX = scrw + 50;
            zeroY = (int) Math.floor(Math.random() * (frameH - zero.getHeight()));
        }
        zero.setX(zeroX);
        zero.setY(zeroY);

        note1X -= (int) Math.floor((Math.random() * 30) + 20);
        if (note1X < 0) {
            note1X = scrw + (int) Math.floor((Math.random() * 1000) + 500);
            note1Y = (int) Math.floor(Math.random() * (frameH - note1.getHeight()));
        }
        note1.setX(note1X);
        note1.setY(note1Y);

        note2X -= (int) Math.floor((Math.random() * 10) + 1);
        if (note2X < 0) {
            note2X = scrw + (int) Math.floor((Math.random() * 1000) + 700);
            note2Y = (int) Math.floor(Math.random() * (frameH - note2.getHeight()));
        }
        note2.setX(note2X);
        note2.setY(note2Y);

        note3X -= (int) Math.floor((Math.random() * 15) + 5);
        if (note3X < 0) {
            note3X = scrw + (int) Math.floor((Math.random() * 1000) + 500);
            note3Y = (int) Math.floor(Math.random() * (frameH - note3.getHeight()));
        }
        note3.setX(note3X);
        note3.setY(note3Y);

        note4X -= (int) Math.floor((Math.random() * 20) + 10);
        if (note4X < 0) {
            note4X = scrw + (int) Math.floor((Math.random() * 1000) + 700);
            note4Y = (int) Math.floor(Math.random() * (frameH - note4.getHeight()));
        }
        note4.setX(note4X);
        note4.setY(note4Y);

        note5X -= (int) Math.floor((Math.random() * 25) + 15);
        if (note5X < 0) {
            note5X = scrw + (int) Math.floor((Math.random() * 1000) + 500);
            note5Y = (int) Math.floor(Math.random() * (frameH - note5.getHeight()));
        }
        note5.setX(note5X);
        note5.setY(note5Y);

        note6X -= (int) Math.floor((Math.random() * 10) + 1);
        if (note6X < 0) {
            note6X = scrw + (int) Math.floor((Math.random() * 1000) + 700);
            note6Y = (int) Math.floor(Math.random() * (frameH - note6.getHeight()));
        }
        note6.setX(note6X);
        note6.setY(note6Y);

        note7X -= (int) Math.floor((Math.random() * 15) + 5);
        if (note7X < 0) {
            note7X = scrw + (int) Math.floor((Math.random() * 1000) + 500);
            note7Y = (int) Math.floor(Math.random() * (frameH - note7.getHeight()));
        }
        note7.setX(note7X);
        note7.setY(note7Y);

        note8X -= (int) Math.floor((Math.random() * 20) + 10);
        if (note8X < 0) {
            note8X = scrw + (int) Math.floor((Math.random() * 1000) + 500);
            note8Y = (int) Math.floor(Math.random() * (frameH - note8.getHeight()));
        }
        note8.setX(note8X);
        note8.setY(note8Y);

        note9X -= (int) Math.floor((Math.random() * 25) + 15);
        if (note9X < 0) {
            note9X = scrw + (int) Math.floor((Math.random() * 1000) + 700);
            note9Y = (int) Math.floor(Math.random() * (frameH - note9.getHeight()));
        }
        note9.setX(note9X);
        note9.setY(note9Y);

        note10X -= (int) Math.floor((Math.random() * 30) + 1);
        if (note10X < 0) {
            note10X = scrw + (int) Math.floor((Math.random() * 1000) + 500);
            note10Y = (int) Math.floor(Math.random() * (frameH - note10.getHeight()));
        }
        note10.setX(note10X);
        note10.setY(note10Y);

        bonusX -= 10;
        if (bonusX < 0) {
            bonusX = scrw + (int) Math.floor((Math.random() * 1000) + 700);
            bonusY = (int) Math.floor(Math.random() * (frameH - bonus.getHeight()));
        }
        bonus.setX(bonusX);
        bonus.setY(bonusY);

        tempsX -= 5;
        if (tempsX < 0) {
            tempsX = scrw + (int) Math.floor((Math.random() * 100) + 50);
            tempsY = (int) Math.floor(Math.random() * (frameH - temps.getHeight()));
        }
        temps.setX(tempsX);
        temps.setY(tempsY);

       /* if (action_flag == true){
           avatarY-=20;
        } else {
            avatarY+=20;
        }

        if (avatarY < 0) avatarY = 0;
        if (avatarY > frameH - avatarSize) avatarY = frameH - avatarSize;

        avatar.setY(avatarY);*/

        score.setText("Moyenne = " + String.format("%.2f", moyenne) + "   n° " + nbNote + "/30");

    }

    //Arreter le jeu et envoyer les resultats au ScoreActivity
    public void gameOver() {
        timer.cancel();
        timer = null;
        Intent intent = new Intent(this,ScoreActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("choice",choice);
        intent.putExtra("moyenne",moyenne);
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude",latitude);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this, mAccelerometer);
        super.onPause();
    }

    //Deplacer l'avatar avec l'accelerometre et gerer le depassement d'ecran
    @Override
    public void onSensorChanged(SensorEvent event) {
        avatarY = avatarY + (int) event.values[1] * 16 - 60;
        if (avatarY < 0) avatarY = 0;
        if (avatarY > frameH - avatarSize) avatarY = frameH - avatarSize;
        avatar.setY(avatarY);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //Obtenir les coordonnées du joueur
    public void getPosition() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(this, "Allumez votre GPS!", Toast.LENGTH_LONG).show();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(GameActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    longitude = (float) location.getLongitude();
                    latitude = (float) location.getLatitude();
                } else {
                    Toast.makeText(this, "Impossible de trouver votre position!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e){
        if ( e.getAction() == KeyEvent.ACTION_DOWN){
            switch (e.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    timer.cancel();
                    timer=null;
                    startActivity(new Intent(this, NameActivity.class));
            }
        }
        return super.dispatchKeyEvent(e);
    }
}