package com.humber.n01414195_nguyenanhtuanle_owm;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.GsonBuilder;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private FusedLocationProviderClient fusedLocationClient;
    private ActivityResultLauncher<String[]> locationPermissionRequest;
    private GoogleMap googleMap;
    private FragmentManager fragmentManager;
    private SupportMapFragment supportMapFragment;
    private Marker currentLocationMarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fragmentManager = getSupportFragmentManager();
        supportMapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.fragment_container_view);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
        locationPermissionRequest = registerForActivityResult(new ActivityResultContracts
                        .RequestMultiplePermissions(), result -> {
                    Boolean fineLocationGranted = result.getOrDefault(
                            android.Manifest.permission.ACCESS_FINE_LOCATION, false);
                    Boolean coarseLocationGranted = result.getOrDefault(
                            android.Manifest.permission.ACCESS_COARSE_LOCATION, false);
                    if (fineLocationGranted != null && fineLocationGranted) {
                        Toast.makeText(this, "Precise location access granted.", Toast.LENGTH_SHORT).show();
                        getLastLocation();
                    } else if (coarseLocationGranted != null && coarseLocationGranted) {
                        Toast.makeText(this, "Only approximate location access granted.", Toast.LENGTH_SHORT).show();
                        getLastLocation();
                    } else {
                        Toast.makeText(this, "No location access granted.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapClickListener(this::addMarker);
        googleMap.setOnMarkerClickListener(marker -> {
            WeatherForecastFragment weatherForecastFragment = new WeatherForecastFragment(marker.getPosition(), supportMapFragment);
            fragmentManager.beginTransaction().hide(supportMapFragment).add(R.id.fragment_container_view, weatherForecastFragment).show(weatherForecastFragment).commit();
            return false;
        });
        getLastLocation();
    }

    public void getCurrentLocation(View view) {
        getLastLocation();
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
            });
            return;
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null).addOnSuccessListener(this, location -> {
            if (location != null) {
                System.out.println(location);
                if (googleMap != null) {
                    addMarker(new LatLng(location.getLatitude(), location.getLongitude()));
                }
            } else {
                Toast.makeText(MainActivity.this, "Location is null", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addMarker(LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
        if (currentLocationMarker == null) {
            currentLocationMarker = googleMap.addMarker(new MarkerOptions().position(latLng));
        } else {
            currentLocationMarker.setPosition(latLng);
        }
        String url = "https://api.openweathermap.org/data/2.5/weather?lat=" + latLng.latitude + "&lon=" + latLng.longitude + "&appid=68daad360224c7c13919c9d08222e87f&units=metric";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(url, response -> {
            System.out.println(response);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            WeatherInfo weatherInfo = builder.create().fromJson(response, WeatherInfo.class);
            assert currentLocationMarker != null;
            currentLocationMarker.setTitle(weatherInfo.getName());
            currentLocationMarker.setSnippet(weatherInfo.getWeather()[0].getDescription() + " " + weatherInfo.getMain().getTemp() + "°C" + " " + weatherInfo.getMain().getHumidity() + "%");
            currentLocationMarker.showInfoWindow();
        }, error -> System.out.println(error.getMessage()));
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }
}