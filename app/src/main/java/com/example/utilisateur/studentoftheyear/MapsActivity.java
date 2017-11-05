package com.example.utilisateur.studentoftheyear;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String name;
    float longitude;
    float latitude;
    float score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        name = getIntent().getStringExtra("name");
        score = getIntent().getFloatExtra("score",0);
        longitude = getIntent().getFloatExtra("longitude",0);
        latitude = getIntent().getFloatExtra("latitude",0);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng joueur = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(joueur).title("Le joueur "+name+" a eu "+score+ " de moyenne ici!"));
        float zoomLevel = 8.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(joueur, zoomLevel));
    }
}
