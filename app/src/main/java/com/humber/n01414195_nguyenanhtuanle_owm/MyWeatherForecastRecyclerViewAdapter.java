package com.humber.n01414195_nguyenanhtuanle_owm;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.humber.n01414195_nguyenanhtuanle_owm.databinding.FragmentItemBinding;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyWeatherForecastRecyclerViewAdapter extends RecyclerView.Adapter<MyWeatherForecastRecyclerViewAdapter.ViewHolder> {
    private final List<WeatherForecast.Forecast> forecastList;

    public MyWeatherForecastRecyclerViewAdapter(List<WeatherForecast.Forecast> items) {
        forecastList = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.forecast = forecastList.get(position);
        holder.dateTextView.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault()).format(new Date((long) holder.forecast.getDt() * 1000)));
        holder.weatherTextView.setText(MessageFormat.format("Temp: {0}°C\nFeels like: {1}°C\nDescription: {2}\nHumidity: {3}%\nWind: {4}m/s\nClouds: {5}%\nPressure: {6}hPa\nTemp min: {7}°C\nTemp max: {8}°C", holder.forecast.getMain().getTemp(), holder.forecast.getMain().getFeelsLike(), holder.forecast.getWeather().get(0).getDescription(), holder.forecast.getMain().getHumidity(), holder.forecast.getWind().getSpeed(), holder.forecast.getClouds().getAll(), holder.forecast.getMain().getPressure(), holder.forecast.getMain().getTempMin(), holder.forecast.getMain().getTempMax()));
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView dateTextView;
        public final TextView weatherTextView;
        public WeatherForecast.Forecast forecast;

        public ViewHolder(FragmentItemBinding binding) {
            super(binding.getRoot());
            dateTextView = binding.itemNumber;
            weatherTextView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + weatherTextView.getText() + "'";
        }
    }
}