package com.example.myapplication;

import android.app.usage.UsageEvents;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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

    CompactCalendarView calendarView;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = v.findViewById(R.id.compactcalendar_view);
        listView = v.findViewById(R.id.listView_2);
        Sqlhelper sqlhelper = new Sqlhelper(getContext());

        calendarView.setUseThreeLetterAbbreviation(true);
        SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMM - yyyy", Locale.getDefault());
        TextView tv_month = v.findViewById(R.id.month);
        tv_month.setText(dateFormatForMonth.format(calendarView.getFirstDayOfCurrentMonth()));

        List<Long> epoch = sqlhelper.getEventDate();
        for(int i =0 ; i<epoch.size();i++){
            Event evt = new Event(Color.RED, epoch.get(i),"");
            calendarView.addEvent(evt);
        }

        calendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                Calendar c = Calendar.getInstance();
                c.setTime(dateClicked);
                int mYear=c.get(Calendar.YEAR);
                int mMonth=c.get(Calendar.MONTH)+1;
                int mDay=c.get(Calendar.DAY_OF_MONTH);

                List<Item> itemList = sqlhelper.getByDate(mDay+"/"+mMonth+"/"+mYear);
                ItemAdapter adapter = new ItemAdapter(getContext(),R.layout.item,itemList,getActivity());
                listView.setAdapter(adapter);
            }
            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                tv_month.setText(dateFormatForMonth.format(firstDayOfNewMonth));
            }
        });



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        calendarView.removeAllEvents();
        Sqlhelper sqlhelper = new Sqlhelper(getContext());
        List<Long> epoch = sqlhelper.getEventDate();
        for(int i =0 ; i<epoch.size();i++){
            Event evt = new Event(Color.RED, epoch.get(i),"");
            calendarView.addEvent(evt);
        }
    }
}