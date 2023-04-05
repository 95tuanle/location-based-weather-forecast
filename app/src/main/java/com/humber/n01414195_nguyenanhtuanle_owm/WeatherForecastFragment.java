package com.humber.n01414195_nguyenanhtuanle_owm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.GsonBuilder;

import java.text.MessageFormat;

public class WeatherForecastFragment extends Fragment {
    private final LatLng latLng;
    private final SupportMapFragment supportMapFragment;

    public WeatherForecastFragment(LatLng latLng, SupportMapFragment supportMapFragment) {
        this.latLng = latLng;
        this.supportMapFragment = supportMapFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().getSupportFragmentManager().beginTransaction().remove(WeatherForecastFragment.this).show(supportMapFragment).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        String url = MessageFormat.format("https://api.openweathermap.org/data/2.5/forecast?lat={0}&lon={1}&appid=68daad360224c7c13919c9d08222e87f&units=metric", latLng.latitude, latLng.longitude);
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(url, response -> {
            System.out.println(response);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            WeatherForecast weatherForecast = builder.create().fromJson(response, WeatherForecast.class);
            if (view instanceof RecyclerView) {
                Context context = view.getContext();
                RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
                recyclerView.setAdapter(new MyWeatherForecastRecyclerViewAdapter(weatherForecast.getForecasts()));
            }
        }, error -> System.out.println(error.getMessage()));
        RequestQueue queue = Volley.newRequestQueue(requireContext());
        queue.add(stringRequest);
        return view;
    }
}