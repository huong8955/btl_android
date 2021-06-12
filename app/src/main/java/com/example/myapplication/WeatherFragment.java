package com.example.myapplication;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.kwabenaberko.openweathermaplib.constant.Languages;
import com.kwabenaberko.openweathermaplib.constant.Units;
import com.kwabenaberko.openweathermaplib.implementation.OpenWeatherMapHelper;
import com.kwabenaberko.openweathermaplib.implementation.callback.CurrentWeatherCallback;
import com.kwabenaberko.openweathermaplib.implementation.callback.ThreeHourForecastCallback;
import com.kwabenaberko.openweathermaplib.model.currentweather.CurrentWeather;
import com.kwabenaberko.openweathermaplib.model.threehourforecast.ThreeHourForecast;
import com.kwabenaberko.openweathermaplib.model.threehourforecast.ThreeHourForecastWeather;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {
    TextView tv_temp,tv_desc,tv_doam,tv_gio;
    ImageView iv_icon;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);

        tv_temp = v.findViewById(R.id.temp);
        tv_desc = v.findViewById(R.id.desc);
        tv_doam = v.findViewById(R.id.doam);
        tv_gio = v.findViewById(R.id.gio);
        iv_icon = v.findViewById(R.id.iv_icon);

        OpenWeatherMapHelper helper = new OpenWeatherMapHelper(getString(R.string.OPEN_WEATHER_MAP_API_KEY));
        helper.setLanguage(Languages.VIETNAMESE);
        helper.setUnits(Units.METRIC);

        helper.getCurrentWeatherByCityName("Hanoi", new CurrentWeatherCallback() {
            @Override
            public void onSuccess(CurrentWeather currentWeather) {
                tv_temp.setText(currentWeather.getMain().getTempMax()+"°C");
                tv_desc.setText(currentWeather.getWeather().get(0).getDescription());
                tv_gio.setText("Tốc độ gió: "+currentWeather.getWind().getSpeed()+"km/h");
                tv_doam.setText("Độ ẩm: "+currentWeather.getMain().getHumidity()+"%");
                int id = Math.toIntExact(currentWeather.getWeather().get(0).getId());
                if( id >=200 || id < 300 ){
                    iv_icon.setImageResource(R.drawable.muagiong);
                }
                if( id >=300 || id <400 ){
                    iv_icon.setImageResource(R.drawable.muaphun);
                }
                if( id >=500 || id <600 ){
                    iv_icon.setImageResource(R.drawable.mua);
                }
                if( id >=700 || id <800 ){
                    iv_icon.setImageResource(R.drawable.mist);
                }
                if( id == 800 ){
                    iv_icon.setImageResource(R.drawable.binhthuong);
                }
                if( id > 800 ){
                    iv_icon.setImageResource(R.drawable.cloud);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                tv_temp.setText("Lỗi");
                tv_desc.setText("Lỗi");
                tv_gio.setText("Lỗi");
                tv_doam.setText("Lỗi");
            }
        });


        recyclerView = v.findViewById(R.id.recyclerView);
        List<WeatherItem> weatherItemList = new ArrayList<>();
            //Lay weather list
        try{

            WeatherItemAdapter weatherItemAdapter = new WeatherItemAdapter(weatherItemList);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(weatherItemAdapter);

            helper.getThreeHourForecastByCityName("Hanoi", new ThreeHourForecastCallback() {
                @Override
                public void onSuccess(ThreeHourForecast threeHourForecast) {
                    List<ThreeHourForecastWeather> list = threeHourForecast.getList();
                    for(int i = 0 ; i<list.size();i++ ){
                        ThreeHourForecastWeather item  = list.get(i);

                        int image = 0;
                        int id = Math.toIntExact(item.getWeather().get(0).getId());
                        if( id >=200 && id < 300 ){image = R.drawable.muagiong;}
                        if( id >=300 && id <400 ){ image = R.drawable.muaphun ;}
                        if( id >=500 && id <600 ){image = R.drawable.mist;}
                        if( id >=700 && id <800 ){image = R.drawable.mua;}
                        if( id == 800 ){image = R.drawable.binhthuong;}
                        if( id > 800 ){image = R.drawable.cloud;}

                        WeatherItem weatherItem = new WeatherItem(item.getDt(),
                                image,
                                item.getWeather().get(0).getDescription(),
                                item.getMain().getTempMax()+"°C"
                        );
                        weatherItemList.add(weatherItem);

                    }
                    weatherItemAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Throwable throwable) { }
            });


        }
        catch (Exception e){
            Toast.makeText(getActivity(), ""+e,
                    Toast.LENGTH_LONG).show();
        }



        return v;
    }
}