package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class WeatherItemAdapter  extends RecyclerView.Adapter<WeatherItemAdapter.MyViewHolder>{
    private List<WeatherItem> weatherItemList;

    public WeatherItemAdapter(List<WeatherItem> weatherItemList) {
        this.weatherItemList = weatherItemList;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_date,tv_min,tv_max;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.date_tmp);
            tv_min = itemView.findViewById(R.id.min_temp);
            tv_max = itemView.findViewById(R.id.max_temp);
            img = itemView.findViewById(R.id.iv_icon_weather);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_item, parent, false);
            return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        WeatherItem item = weatherItemList.get(position);

        String transformedDate = new SimpleDateFormat("H:mm  dd/MM/yy").format(new Date(item.getDate()*1000));
        holder.tv_date.setText(transformedDate);
        holder.tv_min.setText(item.getMin_temp());
        holder.tv_max.setText(item.getMax_temp());
        holder.img.setImageResource(item.getImage());
    }

    @Override
    public int getItemCount() {
        return weatherItemList.size();
    }

}
