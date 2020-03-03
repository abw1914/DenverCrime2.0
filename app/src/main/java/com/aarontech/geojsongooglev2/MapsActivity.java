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
import com.google.maps.android.data.geojson.GeoJsonPointStyle;

import org.json.JSONException;

import java.io.IOException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap mMap;
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
    private static final LatLng DENVER = new LatLng(39.742043, -104.991531);
    private static final LatLng MOUNTAIN_VIEW = new LatLng(39.6, -104.8);

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10), 9000, null);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(DENVER, 12));

        boolean dayStyle = mMap.setMapStyle(new MapStyleOptions(getResources().getString(R.string.style_json)));
        if(!dayStyle){
            Log.e(LOG_TAG, "Styling Error");
        }

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

        //Denver Police Shooting Point GeoJson Layer here
        GeoJsonLayer policeShootings= null;
        try{
            policeShootings = new GeoJsonLayer(mMap, R.raw.denver_police_officer_involved_shootings, this);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        policeShootings.addLayerToMap();

        //Denver Crime Point GeoJson Layer here - this is commented out for the moment because the volume of points
      /*  GeoJsonLayer denverCrime = null;
        try {
            denverCrime = new GeoJsonLayer(mMap, R.raw.denver_crime_data, this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        denverCrime.addLayerToMap();
*/
    }





}


