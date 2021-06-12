package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class edActivity extends AppCompatActivity {

    EditText et_title;
    EditText et_desc;
    EditText et_date;
    Button btn_chooseDay,btnSaveTask,btnCancel,btnDelete;
    Sqlhelper sqlhelper;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ed);
        initView();
        final Intent intent=getIntent();
        String category = "";
        if(intent.getSerializableExtra("item")!=null) {
            Item t = (Item) intent.getSerializableExtra("item");
            id=t.getId();
            et_title.setText(t.getTitle());
            et_date.setText(t.getDate());
            et_desc.setText(t.getDesc());
        }
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
                DatePickerDialog datePickerDialog=new DatePickerDialog(edActivity.this,
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
        //Delete
        sqlhelper = new Sqlhelper(this);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlhelper.deleteItem(id);
                finish();
            }
        });
        //Update
        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlhelper.updateItem(new Item(
                        id,
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
        btnDelete = findViewById(R.id.btnDelete);
    }

}