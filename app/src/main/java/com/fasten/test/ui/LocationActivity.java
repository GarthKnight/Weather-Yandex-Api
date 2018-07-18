package com.fasten.test.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.fasten.test.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


public class LocationActivity extends MvpAppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;

    private Location myLocation;
    private GoogleApiClient googleApiClient;
    private Handler gpsDetectHandler = new Handler();

    private View tvGpsConnectionState;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //todo check GPD state

    }

    @Override
    protected void onStart() {
        super.onStart();
        tvGpsConnectionState = findViewById(R.id.tvGpsConnectionState);
        buildGoogleApiClient();
    }

    private void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            googleApiClient.connect();
        } else if (!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkLocationPermission()) {
                startLocationUpdates();
            }
        } else {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        gpsDetectHandler.removeCallbacks(gpsDetectRunnable);
        gpsDetectHandler.postDelayed(gpsDetectRunnable, 8 * 1000);
        myLocation = location;
        tvGpsConnectionState.setVisibility(View.GONE);

    }

    @SuppressWarnings({"MissingPermission"})
    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, createLocationRequest(), this);
    }

    protected LocationRequest createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        return mLocationRequest;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkLocationPermission() {
        boolean result = true;
        boolean isM = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (isM && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            result = false;
        }
        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_FINE_LOCATION) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates();
            }
        }
    }

    private Runnable gpsDetectRunnable = () -> onGpsDisable();

    public void onGpsDisable() {
        tvGpsConnectionState.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {
        removeLocationUpdates();
        super.onStop();
    }

    private void removeLocationUpdates() {

        if (googleApiClient != null && googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
            googleApiClient.disconnect();
        }
    }

    @SuppressWarnings({"MissingPermission"})
    @Nullable
    public Location getMyLocation() {
        if (myLocation == null) {
            myLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        }
        return myLocation;
    }

    @Override
    protected void onResume() {
        super.onResume();
        gpsDetectHandler.postDelayed(gpsDetectRunnable, 3 * 1000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gpsDetectHandler.removeCallbacks(gpsDetectRunnable);
    }
}
