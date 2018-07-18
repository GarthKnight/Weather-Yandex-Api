package com.fasten.test.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasten.test.R;
import com.fasten.test.mvp.data.InformationForDayTime;
import com.fasten.test.mvp.data.WeatherData;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeatherAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    private static final int HEADER = 0;
    private static final int OTHER = 1;

    private WeatherData weatherData;

    public WeatherAdapter(WeatherData weatherData) {
        this.weatherData = weatherData;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v;

        if (viewType == HEADER) {
            v = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.item_weather_today, viewGroup, false);
            return new HeaderVH(v);
        } else {
            v = LayoutInflater
                    .from(viewGroup.getContext())
                    .inflate(R.layout.item_weather_forecast, viewGroup, false);
            return new OtherElementsVH(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder vh, int position) {
        if (getItemViewType(position) == HEADER) {
            HeaderVH headerVH = ((HeaderVH) vh);

            loadImage(weatherData.getFact().getIcon(), vh.ivIcon);

            vh.tvDescription.setText(weatherData.getFact().getCondition());
            headerVH.tvTemperature.setText(String.valueOf(weatherData.getFact().getTemp()));
//            headerVH.tvPlace.setText(weatherData.getGeoObject().getName());
            headerVH.tvPressure.setText(weatherData.getFact().getPressureMm());
            headerVH.tvHumidity.setText(weatherData.getFact().getHumidity());
            headerVH.tvWind.setText(weatherData.getFact().getWindSpeed());
        } else {
            OtherElementsVH otherElemetnsVH = ((OtherElementsVH) vh);

            InformationForDayTime day = weatherData
                    .getForecasts()
                    .get(position)
                    .getParts()
                    .getDay();

            InformationForDayTime night = weatherData
                    .getForecasts()
                    .get(position)
                    .getParts()
                    .getNight();

            String dayTemperature = "Day: " + day.getTemp();
            String nightTemperature = "Night: " + night.getTemp();

            vh.tvDescription.setText(day.getCondition());
            otherElemetnsVH.tvTemperatureDay.setText(dayTemperature);
            otherElemetnsVH.tvTemperatureNight.setText(nightTemperature);
            otherElemetnsVH.tvDate.setText(weatherData.getForecasts().get(position).getDate());

            loadImage(day.getIcon(), vh.ivIcon);
        }


    }

    public void loadImage(String icon, ImageView ivIcon) {
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER;
        } else {
            return OTHER;
        }
    }

    @Override
    public int getItemCount() {
        return weatherData.getForecasts().size();
    }

    public static class HeaderVH extends WeatherViewHolder {
        @BindView(R.id.tvTemperature)
        TextView tvTemperature;
        @BindView(R.id.tvPressure)
        TextView tvPressure;
        @BindView(R.id.tvWind)
        TextView tvWind;
        @BindView(R.id.tvHumidity)
        TextView tvHumidity;
        @BindView(R.id.tvPlace)
        TextView tvPlace;

        public HeaderVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class OtherElementsVH extends WeatherViewHolder {
        @BindView(R.id.tvDate)
        TextView tvDate;
        @BindView(R.id.tvTemperatureDay)
        TextView tvTemperatureDay;
        @BindView(R.id.tvTemperatureNight)
        TextView tvTemperatureNight;

        public OtherElementsVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
