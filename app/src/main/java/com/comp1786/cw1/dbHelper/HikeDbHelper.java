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
import com.comp1786.cw1.constant.ObservationType;
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
    private static final String OBSERVATION_TABLE_HIKE_ID = "hike_id";
    private static final String OBSERVATION_TABLE = "observation";
    private static final String OBSERVATION_TABLE_TYPE = "type";
    private static final String OBSERVATION_TABLE_NAME = "name";
    private static final String OBSERVATION_TABLE_DATE = "date";
    private static final String OBSERVATION_TABLE_TIME = "time";
    private static final String OBSERVATION_TABLE_COMMENT = "comment";
    private static final String OBSERVATION_TABLE_LOCATION = "location";

    private static final String CREATE_TABLE_HIKE = String.format(
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
            HIKE_TABLE, ID_COLUMN_NAME, HIKE_TABLE_NAME, HIKE_TABLE_LOCATION, HIKE_TABLE_DATEOFHIKE, //5 biến mỗi hàng
            HIKE_TABLE_PARKINGAVAILABILITY, HIKE_TABLE_HIKELENGTH, HIKE_TABLE_DIFFICULTY, HIKE_TABLE_DESCRIPTION, HIKE_TABLE_TRAILTYPE,
            HIKE_TABLE_EMERGENCY, CREATEDAT_COLUMN, UPDATEDAT_COLUMN
            //Create Database query
    );
    //SQLiteDatabase: a built-in class to represent a database, to help database manipulation
    private static final String CREATE_TABLE_OBSERVATION = String.format(
            "CREATE TABLE %s (" +
                    "%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "%s INTEGER NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT, " + //comment
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "%s TEXT NOT NULL, " +
                    "FOREIGN KEY (%s) REFERENCES %s (%s) )",
            OBSERVATION_TABLE, ID_COLUMN_NAME, OBSERVATION_TABLE_HIKE_ID, OBSERVATION_TABLE_TYPE, OBSERVATION_TABLE_NAME,
            OBSERVATION_TABLE_DATE, OBSERVATION_TABLE_TIME, OBSERVATION_TABLE_COMMENT, OBSERVATION_TABLE_LOCATION, CREATEDAT_COLUMN, UPDATEDAT_COLUMN,
            OBSERVATION_TABLE_HIKE_ID, HIKE_TABLE, ID_COLUMN_NAME
            //Create Observation query
    );
    private final SQLiteDatabase database;

    public HikeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = this.getWritableDatabase();
        //get database object
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_HIKE);
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
        rowValues.put(HIKE_TABLE_DATEOFHIKE, hike.getDate());
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
        rowValues.put(OBSERVATION_TABLE_TYPE, observation.getType().toString());
        rowValues.put(OBSERVATION_TABLE_NAME, observation.getName());
        rowValues.put(OBSERVATION_TABLE_DATE, observation.getDate());
        rowValues.put(OBSERVATION_TABLE_TIME, observation.getTime());
        rowValues.put(OBSERVATION_TABLE_COMMENT, observation.getComment());
        rowValues.put(OBSERVATION_TABLE_LOCATION, observation.getLocation());
        rowValues.put(CREATEDAT_COLUMN, new Date().toString());
        rowValues.put(UPDATEDAT_COLUMN, new Date().toString());

        return database.insertOrThrow(OBSERVATION_TABLE, null, rowValues); //insert row into database
    }

    public List<Hike> getHikeList() throws IllegalAccessException {
        Cursor results = database.query("hike",
                new String[]{"id", "name", "location", "date_of_hike", "parking_availability", "hike_length", "difficulty",
                        "description", "trail_type", "emergency_contact", "created_at", "updated_at"},
                null, null, null, null, "date_of_hike");

        List<Hike> hikeList = new ArrayList<>();
        results.moveToFirst();
        while (!results.isAfterLast()) {
            long id = results.getLong(0);
            String name = results.getString(1);
            String location = results.getString(2);
            String date_of_hike = results.getString(3);
            Boolean parking_availability = getParkingAvailability(results.getInt(4));
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

    public Hike getHikeById(long hike_id) throws IllegalAccessException {
        Cursor results = database.query("hike",
                new String[]{"id", "name", "location", "date_of_hike", "parking_availability", "hike_length", "difficulty",
                        "description", "trail_type", "emergency_contact", "created_at", "updated_at"},
                "id =?", new String[]{String.valueOf(hike_id)}, null, null, null, "1");
        results.moveToFirst();

        long id = results.getLong(0);
        String name = results.getString(1);
        String location = results.getString(2);
        String date_of_hike = results.getString(3);
        Boolean parking_availability = getParkingAvailability(results.getInt(4));
        long hike_length = results.getLong(5);
        Difficulty difficulty = getDifficulty(results.getString(6));
        String description = results.getString(7);
        TrailType trail_type = getTrailType(results.getString(8));
        String emergency_contact = results.getString(9);
        Date created_at = new Date(results.getString(10));
        Date updated_at = new Date(results.getString(11));

        return new Hike(id, name, location, date_of_hike, parking_availability, hike_length, difficulty, description, trail_type, emergency_contact, created_at, updated_at);

    }

    public List<Observation> getObservationList(Long hikeId) throws ParseException, IllegalAccessException {

        Cursor results = database.query("observation",
                new String[]{"id", "hike_id", "type", "name", "date", "time", "comment", "location", "created_at", "updated_at"},
                "hike_id=?", new String[]{String.valueOf(hikeId)}, null, null, "created_at");

        List<Observation> observationList = new ArrayList<Observation>();

        results.moveToFirst();
        while (!results.isAfterLast()) {

                long id = results.getLong(0);
                long hike_id = results.getLong(1);
                ObservationType type = getObservationType(results.getString(2));
                String name = results.getString(3);
                String date = results.getString(4);
                String time = results.getString(5);
                String comment = results.getString(6);
                String location = results.getString(7);
                Date created_at = new Date(results.getString(8));
                Date updated_at = new Date(results.getString(9));

                Observation ob = new Observation(id, hike_id, type, name, date, time, comment, location, created_at, updated_at);
                observationList.add(ob);

            results.moveToNext();
        }
        return observationList;
    }

    public Observation getObservationByID(Long observationId) throws ParseException, IllegalAccessException {

        Cursor results = database.query("observation",
                new String[]{"id", "hike_id", "type", "name", "date", "time", "comment", "location", "created_at", "updated_at"},
                "id=?", new String[]{String.valueOf(observationId)}, null, null, "created_at", "1");

        results.moveToFirst();

        long id = results.getLong(0);
        long hike_id = results.getLong(1);
        ObservationType type = getObservationType(results.getString(2));
        String name = results.getString(3);
        String date = results.getString(4);
        String time = results.getString(5);
        String comment = results.getString(6);
        String location = results.getString(7);
        Date created_at = new Date(results.getString(8));
        Date updated_at = new Date(results.getString(9));

        Observation ob = new Observation(id, hike_id, type, name, date, time, comment, location, created_at, updated_at);
        return ob;
    }


    public boolean updateHike(Hike hike) {

        // calling a method to get writable database.
        ContentValues rowValues = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        rowValues.put(ID_COLUMN_NAME, hike.getId());
        rowValues.put(HIKE_TABLE_NAME, hike.getHikeName());
        rowValues.put(HIKE_TABLE_LOCATION, hike.getLocation());
        rowValues.put(HIKE_TABLE_DATEOFHIKE, hike.getDate());
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
        int row = database.update(HIKE_TABLE, rowValues, "id=?", new String[]{hike.getId() + ""});
        return (row > 0);
    }

    public int updateObservation(Observation observation) {

        // calling a method to get writable database.
        ContentValues rowValues = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        rowValues.put(ID_COLUMN_NAME, observation.getId());
        rowValues.put(OBSERVATION_TABLE_HIKE_ID, observation.getHikeId());
        rowValues.put(OBSERVATION_TABLE_TYPE, observation.getType().toString());
        rowValues.put(OBSERVATION_TABLE_NAME, observation.getName());
        rowValues.put(OBSERVATION_TABLE_DATE, observation.getDate());
        rowValues.put(OBSERVATION_TABLE_TIME, observation.getTime());
        rowValues.put(OBSERVATION_TABLE_COMMENT, observation.getComment());
        rowValues.put(OBSERVATION_TABLE_LOCATION, observation.getLocation());
        rowValues.put(CREATEDAT_COLUMN, new Date().toString());
        rowValues.put(UPDATEDAT_COLUMN, new Date().toString());

        // on below line we are calling a update method to update our database and passing our values.
        // and we are comparing it with name of our course which is stored in original name variable.
        return database.update(OBSERVATION_TABLE, rowValues, "id=?", new String[]{String.valueOf(observation.getId())});

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

    //for Hike
    private TrailType getTrailType(String type) throws IllegalAccessException {
        if (type.equals(TrailType.RETURN.toString())) {
            return TrailType.RETURN;
        } else if (type.equals(TrailType.LOOP.toString())) {
            return TrailType.LOOP;
        } else if (type.equals(TrailType.PACK_CARRY.toString())) {
            return TrailType.PACK_CARRY;
        } else if (type.equals(TrailType.STAGE.toString())) {
            return TrailType.STAGE;
        } else if (type.equals(TrailType.POINT_TO_POINT.toString())) {
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
        return isParkingAvailability == 1;
    }

    //for Observation
    private ObservationType getObservationType(String type) throws IllegalAccessException {
        if (type.equals(ObservationType.Animal_Sighting.toString())) {
            return ObservationType.Animal_Sighting;
        } else if (type.equals(ObservationType.Vegetation_Sighting.toString())) {
            return ObservationType.Vegetation_Sighting;
        } else if (type.equals(ObservationType.Weather_Condition.toString().replace("_", " "))) {
            return ObservationType.Weather_Condition;
        } else if (type.equals(ObservationType.Trail_Condition.toString())) {
            return ObservationType.Trail_Condition;
        } else if (type.equals(ObservationType.Other.toString().replace("_", " "))) {
            return ObservationType.Other;
        } else {
            throw new IllegalAccessException("Unknown type");
        }
    }
}
