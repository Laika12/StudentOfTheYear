package com.example.utilisateur.studentoftheyear;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AccueilActivity extends AppCompatActivity implements View.OnClickListener {

    private Button jouer,regle,score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        getSupportActionBar().hide();

        jouer = (Button)findViewById(R.id.btn_play);
        jouer.setOnClickListener(this);
        regle = (Button)findViewById(R.id.btn_rules);
        regle.setOnClickListener(this);
        score = (Button)findViewById(R.id.btn_score);
        score.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_play:
                startActivity(new Intent(this, NameActivity.class));
                break;
            case R.id.btn_rules:
                startActivity(new Intent(this, RegleActivity.class));
                break;
            case R.id.btn_score:
                startActivity(new Intent(this, ScoreActivity.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.boule);
        builder.setMessage("Voulez-vous quitter le jeu?")
                .setCancelable(false)
                .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AccueilActivity.this.finish();
                        moveTaskToBack(true);
                    }
                })
                .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
