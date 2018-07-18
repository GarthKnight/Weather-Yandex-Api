package com.fasten.test.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasten.test.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;

    public WeatherViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
