package com.comp1786.cw1.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.comp1786.cw1.Entity.Hike;

import java.util.Date;

public class HikeDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mhike";
    private static final String ID_COLUMN_NAME = "id";
    private static final String NAME_COLUMN_NAME = "name";
    private static final String LOCATION_COLUMN_NAME = "location";
    private static final String DATEOFHIKE_COLUMN_NAME = "date_of_hike";
    private static final String PARKINGAVAILABILITY_COLUMN_NAME = "parking_availability";
    private static final String HIKELENGTH_COLUMN_NAME = "hike_length";
    private static final String DIFFICULTY_COLUMN_NAME = "difficulty";
    private static final String DESCRIPTION_COLUMN_NAME = "description";
    private static final String TRAILTYPE_COLUMN_NAME = "trail_type";
    private static final String EMERGENCY_COLUMN_NAME = "emergency_contact";
    private static final String CREATEDAT_COLUMN_NAME = "created_at";
    private static final String UPDATEDAT_COLUMN_NAME = "updated_at";

    private SQLiteDatabase database;
    //SQLiteDatabase: a built-in class to represent a database, to help database manipulation

    private static final String DATABASE_CREATE_QUERY = String.format(
         "CREATE TABLE %s (" +
         "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
         "%s TEXT NOT NULL, " +
         "%s TEXT NOT NULL, " +
         "%s TEXT NOT NULL, " +
         "%s INTEGER NOT NULL, " +
         "%s INTEGER NOT NULL, " +
         "%s TEXT NOT NULL, " +
         "%s TEXT, "  +
         "%s TEXT NOT NULL, " +
         "%s TEXT NOT NULL, " +
         "%s TEXT NOT NULL, " +
         "%s TEXT NOT NULL)",
         DATABASE_NAME, ID_COLUMN_NAME, NAME_COLUMN_NAME, LOCATION_COLUMN_NAME, DATEOFHIKE_COLUMN_NAME,
            PARKINGAVAILABILITY_COLUMN_NAME, HIKELENGTH_COLUMN_NAME, DIFFICULTY_COLUMN_NAME, DESCRIPTION_COLUMN_NAME,
            TRAILTYPE_COLUMN_NAME, EMERGENCY_COLUMN_NAME, CREATEDAT_COLUMN_NAME, UPDATEDAT_COLUMN_NAME
            //Create Database query
    );

    public HikeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
        //get database object
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_QUERY);
        //create DB by executing query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        Log.w(this.getClass().getName(), DATABASE_NAME + " database upgrade to version "
                + newVersion + " - old data lost");
        onCreate(db);
    }

    public long insertHikeDetails(Hike hike) {
        ContentValues rowValues = new ContentValues(); //Create a new row

        rowValues.put(NAME_COLUMN_NAME, hike.getHikeName());
        rowValues.put(LOCATION_COLUMN_NAME, hike.getLocation());
        rowValues.put(DATEOFHIKE_COLUMN_NAME, hike.getDate().toString());
        rowValues.put(PARKINGAVAILABILITY_COLUMN_NAME, hike.isParking());
        rowValues.put(HIKELENGTH_COLUMN_NAME, hike.getLength());
        rowValues.put(DIFFICULTY_COLUMN_NAME, hike.getDifficulty().toString());
        rowValues.put(DESCRIPTION_COLUMN_NAME, hike.getDescription());
        rowValues.put(TRAILTYPE_COLUMN_NAME, hike.getType().toString());
        rowValues.put(EMERGENCY_COLUMN_NAME, hike.getContact());
        rowValues.put(CREATEDAT_COLUMN_NAME, new Date().toString());
        rowValues.put(UPDATEDAT_COLUMN_NAME, new Date().toString());

        return database.insertOrThrow(DATABASE_NAME, null, rowValues); //insert row into database
    }

    public String getDetails() {
        Cursor results = database.query("mhike",
                new String[] {"name", "location", "date_of_hike", "parking_availability", "hike_length", "difficulty",
                        "description", "trail_type", "emergency_contact", "created_at", "updated_at"},
                        null, null, null, null, "name");

        String resultText = "";
        results.moveToFirst();
        while (!results.isAfterLast()) {
            long id = results.getLong(0);
            String name = results.getString(1);
            String location = results.getString(2);
            String date_of_hike = results.getString(3);
            int parking_availability = results.getInt(4);
            long hike_length = results.getLong(5);
            String difficulty = results.getString(6);
            String description = results.getString(7);
            String trail_type = results.getString(8);
            String emergency_contact = results.getString(9);
            String created_at = results.getString(10);
            String updated_at = results.getString(11);

            resultText += id + " " + name + " " + location + " " + date_of_hike + " " + parking_availability + " "
                    + hike_length + " " + difficulty + " " + description + " " + trail_type + " " + emergency_contact
                    + " " + created_at + " " + updated_at + "\n";

            results.moveToNext();
        }
        return resultText;
    }
}
