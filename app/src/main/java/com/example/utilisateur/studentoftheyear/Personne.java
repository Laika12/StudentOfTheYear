package com.example.utilisateur.studentoftheyear;

import android.util.Log;
import android.widget.Button;

/**
 * Created by Futloo on 31/10/2017.
 */

public class Personne {
    String name;
    float moyenne;
    float latitude, longitude;

    public Personne(String name, float moyenne, float latitude, float longitude) {
        this.name = name;
        this.moyenne = moyenne;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void remplacerPersonne(Personne un, Personne deux){
        un.setName(deux.name);
        un.setMoyenne(deux.moyenne);
        un.setLatitude(deux.latitude);
        un.setLongitude(deux.longitude);
    }

    public void afficherPersonne(Personne personne){
        Log.v("log","Nom "+personne.name+" ,moyenne: "+personne.moyenne+", coordonnées: "+ personne.latitude+" "+personne.longitude);
    }

    public void renommeButton(Button button, Personne personne){
        button.setText(personne.name+": "+ String.format("%.2f", personne.moyenne));
    }
}