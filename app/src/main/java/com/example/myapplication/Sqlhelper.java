package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Sqlhelper extends SQLiteOpenHelper  {
    private static final String DATABASE_NAME = "Todo.db";
    private static int DATABASE_VERSION = 1;

    public Sqlhelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateDB = "CREATE TABLE itemdb("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title TEXT,"+
                "describe TEXT,"+
                "cre_date TEXT)";
        db.execSQL(sqlCreateDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

        public long addItem(Item i){
            ContentValues values = new ContentValues();
            values.put("title", i.getTitle());
            values.put("describe", i.getDesc());
            values.put("cre_date", i.getDate());
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            return sqLiteDatabase.insert("itemdb",null, values);
        }

        public List<Item> getAll() {
            List<Item> list = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            Cursor rs = sqLiteDatabase.query("itemdb",
                    null, null, null,
                    null, null, null);
            while ((rs != null) && (rs.moveToNext())) {
                int id= rs.getInt(0);
                String title = rs.getString(1);
                String describe = rs.getString(2);
                String cre_date = rs.getString(3);
                list.add(new Item(id,title,cre_date,describe));
            }
            return list;
        }

    public int deleteItem(int id) {
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("itemdb",
                whereClause, whereArgs);
    }

    public int updateItem(Item i) {
        ContentValues values = new ContentValues();
        values.put("title", i.getTitle());
        values.put("describe", i.getDesc());
        values.put("cre_date", i.getDate());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "id = ?";
        String[] whereArgs = {Integer.toString(i.getId())};
        return sqLiteDatabase.update("itemdb",
                values, whereClause, whereArgs);
    }

    public List<Long> getEventDate(){
        List<Long> epoch = new ArrayList<>();
        String sql = String.format("SELECT DISTINCT cre_date FROM itemdb");
        SQLiteDatabase db = getReadableDatabase();
        Cursor rs = db.rawQuery(sql,null);
        while ((rs != null) && (rs.moveToNext())) {
            String date = rs.getString(0);
            try {
                Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                long epoch_value = date1.getTime();
                epoch.add(epoch_value);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return epoch;
    }

    public List<Item> getByDate(String date){
        List<Item> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String whereClause = "cre_date = ?";
        String[] whereArgs = {date.trim()};
        Cursor rs = sqLiteDatabase.query("itemdb",
                null, whereClause, whereArgs,
                null, null, null);
        while ((rs != null) && (rs.moveToNext())) {
            int id= rs.getInt(0);
            String title = rs.getString(1);
            String describe = rs.getString(2);
            String cre_date = rs.getString(3);
            list.add(new Item(id,title,cre_date,describe));
        }
        return list;
    }


}
