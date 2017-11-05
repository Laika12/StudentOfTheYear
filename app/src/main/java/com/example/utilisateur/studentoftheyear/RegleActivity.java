package com.example.utilisateur.studentoftheyear;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

public class RegleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regle);
    }

    public void goToGame(View v){
        startActivity(new Intent(this, NameActivity.class));
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
