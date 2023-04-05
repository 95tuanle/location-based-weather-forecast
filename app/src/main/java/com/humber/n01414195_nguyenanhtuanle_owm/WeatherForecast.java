package com.humber.n01414195_nguyenanhtuanle_owm;

import java.util.List;

public class WeatherForecast {
    private String code;
    private int message;
    private City city;
    private List<Forecast> list;

    public String getCode() {
        return code;
    }

    public int getMessage() {
        return message;
    }

    public City getCity() {
        return city;
    }

    public List<Forecast> getForecasts() {
        return list;
    }

    public static class City {
        private int id;
        private String name;
        private Coord coord;
        private String country;
        private int timezone;
        private int sunrise;
        private int sunset;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public Coord getCoord() {
            return coord;
        }

        public String getCountry() {
            return country;
        }

        public int getTimezone() {
            return timezone;
        }

        public int getSunrise() {
            return sunrise;
        }

        public int getSunset() {
            return sunset;
        }
    }

    public static class Coord {
        private double lat;
        private double lon;

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }
    }

    public static class Forecast {
        private int dt;
        private Main main;
        private List<Weather> weather;
        private Clouds clouds;
        private Wind wind;
        private Rain rain;
        private Snow snow;
        private Sys sys;
        private String dt_txt;

        public int getDt() {
            return dt;
        }

        public Main getMain() {
            return main;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public Clouds getClouds() {
            return clouds;
        }

        public Wind getWind() {
            return wind;
        }

        public Rain getRain() {
            return rain;
        }

        public Snow getSnow() {
            return snow;
        }

        public Sys getSys() {
            return sys;
        }

        public String getDtTxt() {
            return dt_txt;
        }
    }

    public static class Main {
        private double temp;
        private double feels_like;
        private double temp_min;
        private double temp_max;
        private int pressure;
        private int sea_level;
        private int grnd_level;
        private int humidity;
        private double temp_kf;

        public double getTemp() {
            return temp;
        }

        public double getFeelsLike() {
            return feels_like;
        }

        public double getTempMin() {
            return temp_min;
        }

        public double getTempMax() {
            return temp_max;
        }

        public int getPressure() {
            return pressure;
        }

        public int getSeaLevel() {
            return sea_level;
        }

        public int getGrndLevel() {
            return grnd_level;
        }

        public int getHumidity() {
            return humidity;
        }

        public double getTempKf() {
            return temp_kf;
        }
    }

    public static class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class Clouds {
        private int all;

        public int getAll() {
            return all;
        }
    }

    public static class Wind {
        private double speed;
        private int deg;

        public double getSpeed() {
            return speed;
        }

        public int getDeg() {
            return deg;
        }
    }

    public static class Rain {
        private double threeHourVolume;

        public double getThreeHourVolume() {
            return threeHourVolume;
        }
    }

    public static class Snow {
        private double threeHourVolume;

        public double getThreeHourVolume() {
            return threeHourVolume;
        }
    }

    public static class Sys {
        private String pod;

        public String getPod() {
            return pod;
        }
    }
}