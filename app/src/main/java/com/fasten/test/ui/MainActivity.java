package com.fasten.test.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fasten.test.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends LocationActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tvToday)
    public void tvTodayClick(View v) {
        if (getMyLocation() != null) {
            Intent i = new Intent(this, TodayActivity.class);
            i.putExtra(TodayActivity.EXTRAS_LAT, getMyLocation().getLatitude());
            i.putExtra(TodayActivity.EXTRAS_LNG, getMyLocation().getLongitude());
            startActivity(i);
        } else {
            Toast.makeText(this, "Местоположение не определенно", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.tvChooseCity)
    public void tvChooseCity(View v) {
        Intent i = new Intent(this, ChooseCityActivity.class);
//            i.putExtra(TodayActivity.EXTRAS_LAT, getMyLocation().getLatitude());
//            i.putExtra(TodayActivity.EXTRAS_LNG, getMyLocation().getLongitude());
        startActivity(i);
    }
}
