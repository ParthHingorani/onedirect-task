package com.parthhingorani.flyasia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.parthhingorani.flyasia.Flights.Flight;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "onedirect.db";
    public static final String TABLE_FLIGHTS = "flights";
    public static final String TABLE_AIRPORTS = "airports";
    public static final String TABLE_BOOKED_FLIGHTS = "booked_flights";
    List<String> airportsList;
    List<Flight> flightsList;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_FLIGHTS + " (flight_id integer primary key autoincrement, airline_url text, airline_name text, duration text, dep_time text, arr_time text, halt text, cost text, source text, destination text, date text)");
        db.execSQL("create table " + TABLE_AIRPORTS + " (airport_id text primary key, airport_name text)");
        db.execSQL("create table " + TABLE_BOOKED_FLIGHTS + " (flight_id integer primary key autoincrement, airline_url text, airline_name text, duration text, dep_time text, arr_time text, halt text, cost text, source text, destination text, date text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_FLIGHTS);
        db.execSQL("drop table if exists " + TABLE_AIRPORTS);
        db.execSQL("drop table if exists " + TABLE_BOOKED_FLIGHTS);
        onCreate(db);
    }

    public void insertIntoFlight()  {
        insertFlight("https://airastana.com/Portals/_default/Skins/AirAstana//img/AirAstana-logo.png",
                "Air Astana ","11h 55m","11:35", "21:00", "Via ALA", "₹ 34,138",
                "DEL", "SVO", "24-8-2018");
        insertFlight("https://upload.wikimedia.org/wikipedia/commons/7/7b/AirAstanaNewLogo.png",
                "Air Astana ","11h 55m","11:35", "21:00", "Via ALA", "₹ 34,138",
                "DEL", "SVO", "24-8-2018");
    }

    public void insertIntoAirport() {
        insertAirport("DEL", "Delhi");
        insertAirport("SVO", "Moscow");
    }

    public void insertFlight(String url, String name, String duration, String departure, String arrival, String halt, String cost, String source, String destination, String date)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("airline_url", url);
        values.put("airline_name", name);
        values.put("duration", duration);
        values.put("dep_time", departure);
        values.put("arr_time", arrival);
        values.put("halt", halt);
        values.put("cost", cost);
        values.put("source", source);
        values.put("destination", destination);
        values.put("date", date);
        db.insert(TABLE_FLIGHTS, null, values);
    }

    public void insertAirport(String id, String name)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("airport_id", id);
        values.put("airport_name", name);
        db.insert(TABLE_AIRPORTS, null, values);
    }

    public void insertBookedFlight(String url, String name, String duration, String departure, String arrival, String halt, String cost, String source, String destination, String date)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("airline_url", url);
        values.put("airline_name", name);
        values.put("duration", duration);
        values.put("dep_time", departure);
        values.put("arr_time", arrival);
        values.put("halt", halt);
        values.put("cost", cost);
        values.put("source", source);
        values.put("destination", destination);
        values.put("date", date);
        db.insert(TABLE_BOOKED_FLIGHTS, null, values);
    }

    public List<Flight> getFlights(String source, String destination, String date)    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_FLIGHTS + " where source=\"" +
                source + "\" and destination=\"" + destination + "\" and date=\"" + date + "\"", null);

        if (result.getCount() == 0) {
            result.close();
            return null;
        }
        else
            flightsList = new ArrayList<>();

        result.moveToFirst();
        while (!result.isAfterLast()) {
            flightsList.add(new Flight(String.valueOf(result.getInt(0)),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10)));
            result.moveToNext();
        }
        result.close();

        return flightsList;
    }

    public List<String> getAirports()    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_AIRPORTS + " order by airport_name", null);

        if (result.getCount() == 0) {
            result.close();
            return null;
        }
        else
            airportsList = new ArrayList<>();

        result.moveToFirst();
        while (!result.isAfterLast()) {
            airportsList.add(result.getString(0) + " - " + result.getString(1));
            result.moveToNext();
        }
        result.close();

        return airportsList;
    }

    public List<Flight> getBookedFlights()    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from " + TABLE_BOOKED_FLIGHTS, null);

        if (result.getCount() == 0) {
            result.close();
            return null;
        }
        else
            flightsList = new ArrayList<>();

        result.moveToFirst();
        while (!result.isAfterLast()) {
            flightsList.add(new Flight(String.valueOf(result.getInt(0)),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7),
                    result.getString(8),
                    result.getString(9),
                    result.getString(10)));
            result.moveToNext();
        }
        result.close();

        return flightsList;
    }
}
