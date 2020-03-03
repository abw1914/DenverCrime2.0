package com.aarontech.geojsongooglev2;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.data.geojson.GeoJsonLayer;

import org.json.JSONException;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String LOG_TAG = MapsActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        boolean nightStyle = mMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));
        if(!nightStyle){
            Log.e(LOG_TAG, "Styling Error");
        }

        // Add a marker in Denver and move the camera
        LatLng denver = new LatLng(39.742043, -104.991531);
        mMap.addMarker(new MarkerOptions().position(denver).title("Marker Denver"));
        mMap.getCameraPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(denver));


        //Denver Neighborhood Boundries GeoJson Layer
        GeoJsonLayer denverNeighborhoodBoundries = null;
        try {
            denverNeighborhoodBoundries = new GeoJsonLayer(mMap, R.raw.denver_neighborhood_geo,this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        denverNeighborhoodBoundries.addLayerToMap();

        //Denver Policy Shooting Point GoeJson Layer here
        GeoJsonLayer policeShootings= null;
        try{
            policeShootings = new GeoJsonLayer(mMap, R.raw.denver_police_officer_involved_shootings, this);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        policeShootings.addLayerToMap();
    }
}
