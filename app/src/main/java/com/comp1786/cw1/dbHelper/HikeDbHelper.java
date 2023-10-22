package com.comp1786.cw1.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.comp1786.cw1.Entity.Hike;
import com.comp1786.cw1.Entity.Observation;
import com.comp1786.cw1.constant.Difficulty;
import com.comp1786.cw1.constant.TrailType;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HikeDbHelper extends SQLiteOpenHelper {
    //General
    private static final String DATABASE_NAME = "mhike";
    private static final String ID_COLUMN_NAME = "id";
    private static final String CREATEDAT_COLUMN = "created_at";
    private static final String UPDATEDAT_COLUMN = "updated_at";

    // HIKE TABLE
    private static final String HIKE_TABLE = "hike";
    private static final String HIKE_TABLE_NAME = "name";
    private static final String HIKE_TABLE_LOCATION = "location";
    private static final String HIKE_TABLE_DATEOFHIKE = "date_of_hike";
    private static final String HIKE_TABLE_PARKINGAVAILABILITY = "parking_availability";
    private static final String HIKE_TABLE_HIKELENGTH = "hike_length";
    private static final String HIKE_TABLE_DIFFICULTY = "difficulty";
    private static final String HIKE_TABLE_DESCRIPTION = "description";
    private static final String HIKE_TABLE_TRAILTYPE = "trail_type";
    private static final String HIKE_TABLE_EMERGENCY = "emergency_contact";


    // OBSERVATION TABLE
    private static final String OBSERVATION_TABLE = "observation";
    private static final String OBSERVATION_TABLE_HIKE_ID = "hike_id";
    private static final String OBSERVATION_TABLE_OBSERVATION = "observation";
    private static final String OBSERVATION_TABLE_COMMENT = "comment";
    private static final String DATABASE_CREATE_QUERY = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s INTEGER NOT NULL, " +
                    "%s INTEGER NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL)",
            HIKE_TABLE, ID_COLUMN_NAME, HIKE_TABLE_NAME, HIKE_TABLE_LOCATION, HIKE_TABLE_DATEOFHIKE,
            HIKE_TABLE_PARKINGAVAILABILITY, HIKE_TABLE_HIKELENGTH, HIKE_TABLE_DIFFICULTY, HIKE_TABLE_DESCRIPTION,
            HIKE_TABLE_TRAILTYPE, HIKE_TABLE_EMERGENCY, CREATEDAT_COLUMN, UPDATEDAT_COLUMN
            //Create Database query
    );
    //SQLiteDatabase: a built-in class to represent a database, to help database manipulation
    private static final String CREATE_TABLE_OBSERVATION = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "FOREIGN KEY (%s) REFERENCES %s (%s) )",
            OBSERVATION_TABLE, ID_COLUMN_NAME, OBSERVATION_TABLE_HIKE_ID, OBSERVATION_TABLE_OBSERVATION, OBSERVATION_TABLE_COMMENT
            , CREATEDAT_COLUMN, UPDATEDAT_COLUMN, OBSERVATION_TABLE_HIKE_ID, HIKE_TABLE, ID_COLUMN_NAME
            //Create Observation query
    );
    private SQLiteDatabase database;

    public HikeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
        //get database object
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_QUERY);
        db.execSQL(CREATE_TABLE_OBSERVATION);
        //create DB by executing query
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HIKE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OBSERVATION_TABLE);

        Log.w(this.getClass().getName(), HIKE_TABLE + " database upgrade to version "
                + newVersion + " - old data lost");
        Log.w(this.getClass().getName(), OBSERVATION_TABLE + " database upgrade to version "
                + newVersion + " - old data lost");

        onCreate(db);
    }

    public long insertHikeDetails(Hike hike) {
        ContentValues rowValues = new ContentValues(); //Create a new row

        rowValues.put(HIKE_TABLE_NAME, hike.getHikeName());
        rowValues.put(HIKE_TABLE_LOCATION, hike.getLocation());
        rowValues.put(HIKE_TABLE_DATEOFHIKE, hike.getDate().toString());
        rowValues.put(HIKE_TABLE_PARKINGAVAILABILITY, hike.isParking());
        rowValues.put(HIKE_TABLE_HIKELENGTH, hike.getLength());
        rowValues.put(HIKE_TABLE_DIFFICULTY, hike.getDifficulty().toString());
        rowValues.put(HIKE_TABLE_DESCRIPTION, hike.getDescription());
        rowValues.put(HIKE_TABLE_TRAILTYPE, hike.getType().toString());
        rowValues.put(HIKE_TABLE_EMERGENCY, hike.getContact());
        rowValues.put(CREATEDAT_COLUMN, new Date().toString());
        rowValues.put(UPDATEDAT_COLUMN, new Date().toString());

        return database.insertOrThrow(HIKE_TABLE, null, rowValues); //insert row into database
    }

    public long insertObservationDetails(Observation observation) {
        ContentValues rowValues = new ContentValues(); //Create a new row

        rowValues.put(OBSERVATION_TABLE_HIKE_ID, observation.getHikeId());
        rowValues.put(OBSERVATION_TABLE_OBSERVATION, observation.getObservation());
        rowValues.put(OBSERVATION_TABLE_COMMENT, observation.getComment());
        rowValues.put(CREATEDAT_COLUMN, new Date().toString());
        rowValues.put(UPDATEDAT_COLUMN, new Date().toString());

        return database.insertOrThrow(OBSERVATION_TABLE, null, rowValues); //insert row into database
    }

    public List<Hike> getHikeDetails() throws IllegalAccessException {
        Cursor results = database.query("hike",
                new String[]{"id", "name", "location", "date_of_hike", "parking_availability", "hike_length", "difficulty",
                        "description", "trail_type", "emergency_contact", "created_at", "updated_at"},
                null, null, null, null, "name");

        List<Hike> hikeList = new ArrayList<>();
        results.moveToFirst();
        while (!results.isAfterLast()) {
            long id = results.getLong(0);
            String name = results.getString(1);
            String location = results.getString(2);
            String date_of_hike = results.getString(3);
            Boolean parking_availability = getParkingAvailability(results.getInt(4));
            ;
            long hike_length = results.getLong(5);
            Difficulty difficulty = getDifficulty(results.getString(6));
            String description = results.getString(7);
            TrailType trail_type = getTrailType(results.getString(8));
            String emergency_contact = results.getString(9);
            Date created_at = new Date(results.getString(10));
            Date updated_at = new Date(results.getString(11));


            Hike hike = new Hike(id, name, location, date_of_hike, parking_availability, hike_length, difficulty, description, trail_type, emergency_contact, created_at, updated_at);

            hikeList.add(hike);
            results.moveToNext();
        }
        return hikeList;
    }

    public List<Observation> getObservationDetails() throws ParseException {
        Cursor results = database.query("observation",
                new String[]{"id", "hike_id", "observation", "comment", "created_at", "updated_at"},
                null, null, null, null, "hike_id");

        List<Observation> observationList = new ArrayList<Observation>();

        results.moveToFirst();
        while (!results.isAfterLast()) {
            long id = results.getLong(0);
            long hike_id = results.getLong(1);
            String observation = results.getString(2);
            String comment = results.getString(3);
            Date created_at = new Date(results.getString(4));
            Date updated_at = new Date(results.getString(5));

            Observation ob = new Observation(id, hike_id, observation, comment, created_at, updated_at);
            observationList.add(ob);
            results.moveToNext();
        }
        return observationList;
    }

    public void updateHike(Hike hike) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rowValues = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        rowValues.put(HIKE_TABLE_NAME, hike.getHikeName());
        rowValues.put(HIKE_TABLE_LOCATION, hike.getLocation());
        rowValues.put(HIKE_TABLE_DATEOFHIKE, hike.getDate().toString());
        rowValues.put(HIKE_TABLE_PARKINGAVAILABILITY, hike.isParking());
        rowValues.put(HIKE_TABLE_HIKELENGTH, hike.getLength());
        rowValues.put(HIKE_TABLE_DIFFICULTY, hike.getDifficulty().toString());
        rowValues.put(HIKE_TABLE_DESCRIPTION, hike.getDescription());
        rowValues.put(HIKE_TABLE_TRAILTYPE, hike.getType().toString());
        rowValues.put(HIKE_TABLE_EMERGENCY, hike.getContact());
        rowValues.put(CREATEDAT_COLUMN, new Date().toString());
        rowValues.put(UPDATEDAT_COLUMN, new Date().toString());

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(HIKE_TABLE, rowValues, "id=?", new String[]{String.valueOf(hike.getId())});
        db.close();
    }

    public void updateObservation(Observation observation) {

        // calling a method to get writable database.
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues rowValues = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        rowValues.put(OBSERVATION_TABLE_HIKE_ID, observation.getHikeId());
        rowValues.put(OBSERVATION_TABLE_OBSERVATION, observation.getObservation());
        rowValues.put(OBSERVATION_TABLE_COMMENT, observation.getComment());
        rowValues.put(CREATEDAT_COLUMN, new Date().toString());
        rowValues.put(UPDATEDAT_COLUMN, new Date().toString());

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        db.update(OBSERVATION_TABLE, rowValues, "id=?", new String[]{String.valueOf(observation.getId())});
        db.close();
    }

    public void deleteHikeById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(HIKE_TABLE, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void deleteObservationById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        db.delete(OBSERVATION_TABLE, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    private TrailType getTrailType(String type) throws IllegalAccessException {
        if (type.equals(TrailType.RETURN.toString())) {
            return TrailType.RETURN;
        } else if (type.equals(TrailType.LOOP.toString())) {
            return TrailType.LOOP;
        } else if (type.equals(TrailType.PACK_CARRY.toString().replace("_", " "))) {
            return TrailType.PACK_CARRY;
        } else if (type.equals(TrailType.STAGE.toString())) {
            return TrailType.STAGE;
        } else if (type.equals(TrailType.POINT_TO_POINT.toString().replace("_", " "))) {
            return TrailType.POINT_TO_POINT;
        } else {
            throw new IllegalAccessException("Unknown type");
        }
    }

    private Difficulty getDifficulty(String difficulty) throws IllegalAccessException {
        if (difficulty.equals(Difficulty.HARD.toString())) {
            return Difficulty.HARD;
        } else if (difficulty.equals(Difficulty.NORMAL.toString())) {
            return Difficulty.NORMAL;
        } else if (difficulty.equals(Difficulty.EASY.toString())) {
            return Difficulty.EASY;
        } else {
            throw new IllegalAccessException("Unknown type");
        }
    }

    private Boolean getParkingAvailability(int isParkingAvailability) {
        if (isParkingAvailability == 1) {
            return true;
        } else {
            return false;
        }
    }
}
