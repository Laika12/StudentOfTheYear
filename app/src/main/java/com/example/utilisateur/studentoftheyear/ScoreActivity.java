package com.example.utilisateur.studentoftheyear;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private String name;
    private float moyenne,latitude,longitude;
    private Button score1,score2,score3,rejouer;
    private TextView message;
    Personne temp, premier, second, troisieme;
    int choice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getSupportActionBar().show();

        score1 = (Button)findViewById(R.id.btn_un);
        score1.setOnClickListener(this);
        score2 = (Button)findViewById(R.id.btn_deux);
        score2.setOnClickListener(this);
        score3 = (Button)findViewById(R.id.btn_trois);
        score3.setOnClickListener(this);
        rejouer = (Button)findViewById(R.id.btn_restart);
        rejouer.setOnClickListener(this);

        message = (TextView) findViewById(R.id.tv_message);

        name = getIntent().getStringExtra("name");
        choice = getIntent().getIntExtra("choice",0);
        moyenne = getIntent().getFloatExtra("moyenne",0);
        latitude =  getIntent().getFloatExtra("latitude",0);
        longitude = getIntent().getFloatExtra("longitude",0);

        temp = new Personne(name,moyenne,latitude,longitude);

        SharedPreferences preferences = getSharedPreferences("Game", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        String name1 = preferences.getString("name1","name1");
        float highscore1 = preferences.getFloat("highscore1",0);
        float latitude1 = preferences.getFloat("lat1",0.0f);
        float longitude1 = preferences.getFloat("long1",0.0f);

        premier = new Personne(name1,highscore1,latitude1,longitude1);

        String name2 = preferences.getString("name2","name2");
        float highscore2 = preferences.getFloat("highscore2",0);
        float latitude2 = preferences.getFloat("lat2",0.0f);
        float longitude2 = preferences.getFloat("long2",0.0f);

        second = new Personne(name2,highscore2,latitude2,longitude2);

        String name3 = preferences.getString("name3","name3");
        float highscore3 = preferences.getFloat("highscore3",0);
        float latitude3 = preferences.getFloat("lat3",0.0f);
        float longitude3 = preferences.getFloat("long3",0.0f);

        troisieme = new Personne(name3,highscore3,latitude3,longitude3);

        if (moyenne > highscore1){
            message.setText("Avec votre moyenne de: "+moyenne+ ", vous êtes premier!");
            // deux dans trois
            remplacerPersonne(troisieme,second);
            // un dans deux
            remplacerPersonne(second,premier);
            // temp dans un
            remplacerPersonne(premier,temp);

            editor.putString("name3", troisieme.name);
            editor.putFloat("highscore3", troisieme.moyenne);
            editor.putFloat("lat3", troisieme.latitude);
            editor.putFloat("long3", troisieme.longitude);

            editor.putString("name2", second.name);
            editor.putFloat("highscore2", second.moyenne);
            editor.putFloat("lat2", second.latitude);
            editor.putFloat("long2", second.longitude);

            editor.putString("name1", premier.name);
            editor.putFloat("highscore1", premier.moyenne);
            editor.putFloat("lat1", premier.latitude);
            editor.putFloat("long1", premier.longitude);

            editor.commit();

            score1.setText(premier.name +": "+ String.format("%.2f", premier.moyenne));
            score2.setText(second.name +": "+ String.format("%.2f", second.moyenne));
            score3.setText(troisieme.name +": "+ String.format("%.2f", troisieme.moyenne));
        } else {
            if (moyenne > highscore2){
                message.setText("Avec votre moyenne de: "+moyenne+ ", vous êtes deuxième!");
                remplacerPersonne(troisieme,second);
                remplacerPersonne(second,temp);

                editor.putString("name3", troisieme.name);
                editor.putFloat("highscore3", troisieme.moyenne);
                editor.putFloat("lat3", troisieme.latitude);
                editor.putFloat("long3", troisieme.longitude);

                editor.putString("name2", second.name);
                editor.putFloat("highscore2", second.moyenne);
                editor.putFloat("lat2", second.latitude);
                editor.putFloat("long2", second.longitude);

                editor.commit();

                score1.setText(premier.name +": "+ String.format("%.2f", premier.moyenne));
                score2.setText(second.name +": "+ String.format("%.2f", second.moyenne));
                score3.setText(troisieme.name +": "+ String.format("%.2f", troisieme.moyenne));
            } else {
                if (moyenne > highscore3){
                    message.setText("Avec votre moyenne de: "+moyenne+ ", vous êtes troisième!");
                    remplacerPersonne(troisieme,temp);

                    editor.putString("name3", troisieme.name);
                    editor.putFloat("highscore3", troisieme.moyenne);
                    editor.putFloat("lat3", troisieme.latitude);
                    editor.putFloat("long3", troisieme.longitude);

                    editor.commit();

                    score1.setText(premier.name +": "+ String.format("%.2f", premier.moyenne));
                    score2.setText(second.name +": "+ String.format("%.2f", second.moyenne));
                    score3.setText(troisieme.name +": "+ String.format("%.2f", troisieme.moyenne));

                } else {
                    message.setText("Vous avez eu "+moyenne+"! Essayez secret story!");
                    score1.setText(premier.name +": "+ String.format("%.2f", premier.moyenne));
                    score2.setText(second.name +": "+ String.format("%.2f", second.moyenne));
                    score3.setText(troisieme.name +": "+ String.format("%.2f", troisieme.moyenne));
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mn_accueil:
                startActivity(new Intent(this,AccueilActivity.class));
                return true;
            case R.id.mn_rejouer:
                startActivity(new Intent(this, NameActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mapMe(View v, Personne personne){
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        intent.putExtra("name",personne.name);
        intent.putExtra("score",personne.moyenne);
        intent.putExtra("longitude",personne.longitude);
        intent.putExtra("latitude",personne.latitude);
        startActivity(intent);
    }

    public void remplacerPersonne(Personne un, Personne deux){
        un.setName(deux.name);
        un.setMoyenne(deux.moyenne);
        un.setLatitude(deux.latitude);
        un.setLongitude(deux.longitude);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_un:
                mapMe(view, premier);
                break;
            case R.id.btn_deux:
                mapMe(view, second);
                break;
            case R.id.btn_trois:
                mapMe(view, troisieme);
                break;
            case R.id.btn_restart:
                if (name == null)  {
                    startActivity(new Intent(this, NameActivity.class));
                } else {
                    Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("choice", choice);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e){
        if ( e.getAction() == KeyEvent.ACTION_DOWN){
            switch (e.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    startActivity(new Intent(this, AccueilActivity.class));
            }
        }
        return super.dispatchKeyEvent(e);
    }
}
