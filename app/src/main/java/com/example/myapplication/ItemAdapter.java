package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ItemAdapter extends ArrayAdapter{
    Context mContext;
    List<Item> myItemArrayList;
    Activity activity;

    public ItemAdapter(@NonNull Context context, int resource, @NonNull List objects, Activity activity) {
        super(context, resource,  objects);
        this.myItemArrayList = objects;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item,null);

        TextView tv_title = convertView.findViewById(R.id.title);
        TextView tv_desc = convertView.findViewById(R.id.describe);
        TextView tv_date = convertView.findViewById(R.id.tv_date);

        Item item = myItemArrayList.get(position);
        tv_title.setText(item.getTitle());
        tv_desc.setText(item.getDesc());
        tv_date.setText(item.getDate());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,edActivity.class);
                intent.putExtra("item",item);
                activity.startActivity(intent);
            }
        });

        return convertView;
    }

}
