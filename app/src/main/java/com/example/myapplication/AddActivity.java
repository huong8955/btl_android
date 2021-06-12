package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    EditText et_title;
    EditText et_desc;
    EditText et_date;
    Button btn_chooseDay,btnSaveTask,btnCancel;
    Sqlhelper sqlhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
        //Cancel btn
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Chon ngay
        btn_chooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c=Calendar.getInstance();
                int mYear=c.get(Calendar.YEAR);
                int mMonth=c.get(Calendar.MONTH);
                int mDay=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = dayOfMonth+"/"+(month+1)+"/"+year;
                                et_date.setText(date.trim());
                            }
                        },mYear, mMonth, mDay);
                final long time = System.currentTimeMillis() - 1;
                datePickerDialog.getDatePicker().setMinDate(time);
                datePickerDialog.show();
            }
        });
        //Add btn
        sqlhelper = new Sqlhelper(this);
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlhelper.addItem(new Item(
                        et_title.getText().toString(),
                        et_date.getText().toString(),
                        et_desc.getText().toString()
                ));
                finish();
            }
        });
    }


    public void initView(){
        et_title = findViewById(R.id.title);
        et_desc = findViewById(R.id.describe);
        et_date = findViewById(R.id.date_input);
        btn_chooseDay = findViewById(R.id.btnChosseDay);
        btnSaveTask = findViewById(R.id.btnSaveTask);
        btnCancel = findViewById(R.id.btnCancel);
    }
}