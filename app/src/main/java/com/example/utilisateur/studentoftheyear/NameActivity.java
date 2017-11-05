package com.example.utilisateur.studentoftheyear;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class NameActivity extends AppCompatActivity implements View.OnClickListener{

    CheckBox pepe,meme,homme,femme,garcon,fille;
    EditText editText;
    String name;
    private int choice;
    int test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        getSupportActionBar().hide();

        editText = (EditText) findViewById(R.id.et_name);
        pepe = (CheckBox) findViewById(R.id.cb_oldm);
        pepe.setOnClickListener(this);
        homme = (CheckBox) findViewById(R.id.cb_man);
        homme.setOnClickListener(this);
        garcon = (CheckBox) findViewById(R.id.cb_boy);
        garcon.setOnClickListener(this);
        meme = (CheckBox) findViewById(R.id.cb_oldw);
        meme.setOnClickListener(this);
        femme = (CheckBox) findViewById(R.id.cb_woman);
        femme.setOnClickListener(this);
        fille = (CheckBox) findViewById(R.id.cb_girl);
        fille.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        test = 0;
        if (pepe.isChecked()){
            choice =1;
            test++;
        }
        if (homme.isChecked()){
            choice =2;
            test++;
        }
        if (garcon.isChecked()){
            choice =3;
            test++;
        }
        if (meme.isChecked()){
            choice =4;
            test++;
        }
        if (femme.isChecked()){
            choice =5;
            test++;
        }
        if (fille.isChecked()){
            choice =6;
            test++;
        }
    }

    public void validerChoix(View view){
        if(editText.length()==0){
            name = "Unknown";
            Toast.makeText(getApplicationContext(), "Veuillez entrer un pseudo!", Toast.LENGTH_LONG).show();
        } else {
            name = editText.getText().toString();
            if (onlyOneChecked()){
                Intent intent = new Intent(getApplicationContext(),GameActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("choice",choice);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Cochez un et un seul avatar!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean onlyOneChecked(){
        if (test == 1) {
            return true;
        } else {
            return false;
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
