package com.comp1786.cw1.obsDetails;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.comp1786.cw1.R;
import com.comp1786.cw1.constant.ObservationType;
import com.comp1786.cw1.database.DatabaseHelper;
import com.comp1786.cw1.object.Observation;
import com.comp1786.cw1.obsList.ObservationList;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewObservation extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    private final int REQUEST_PERMISSION_FINE_LOCATION = 1;
    public long id;
    EditText editObsName;
    EditText editDate;
    EditText editComment;
    Spinner spinnerObvType;
    Button saveButton;
    EditText editTime;
    long hikeID;
    ImageView btnBack;
    private DatabaseHelper databaseHelper;
    private Observation observation;
    private TextView editLocation;
    private FusedLocationProviderClient locationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_add_form);


        //location
        editLocation = findViewById(R.id.addObsLocation);
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_FINE_LOCATION);
        } else {
            showLocation();
        }

        editDate = findViewById(R.id.editDate);


        int y = myCalendar.get(Calendar.YEAR);
        int m = myCalendar.get(Calendar.MONTH);
        int d = myCalendar.get(Calendar.DAY_OF_MONTH);
        int h = myCalendar.get(Calendar.HOUR_OF_DAY);
        int mi = myCalendar.get(Calendar.MINUTE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hikeID = extras.getLong("DATA");
        }

        editDate.setText(d + "/" + (m + 1) + "/" + y);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                updateDateLabel();
            }
        };
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toObservationList();
            }
        });
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewObservation.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });
        editTime = findViewById(R.id.editTime);
        editTime.setText(h + ":" + mi);

        TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hour);
                myCalendar.set(Calendar.MINUTE, minute);

                updateTimeLabel();
            }
        };
        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(NewObservation.this, time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true)
                        .show();
            }
        });
        spinnerObvType = findViewById(R.id.spinType);
        ObservationType[] items = ObservationType.values();
        String[] obvTypeStrings = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            obvTypeStrings[i] = items[i].name().replace("_", " ");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, obvTypeStrings);
        spinnerObvType.setAdapter(adapter);


        saveButton = findViewById(R.id.btnSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveDetails();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    private void toObservationList() {
        Intent intent = new Intent(this, ObservationList.class);
        intent.putExtra("DATA", hikeID);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] gratRequest) {
        super.onRequestPermissionsResult(requestCode, permissions, gratRequest);
        if (requestCode == REQUEST_PERMISSION_FINE_LOCATION) {
            if (gratRequest.length > 0 && gratRequest[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                showLocation();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                editLocation.setText("Location permission not granted");
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void showLocation() {
        locationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    editLocation.setText("Latitude: " + location.getLatitude() + "\nLongtitude: " + location.getLongitude());
                } else {
                    editLocation.setText("Cannot find the location");
                }
            }
        });
    }

    private void saveDetails() throws ParseException, IllegalAccessException {

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        Observation observation = new Observation();

        editObsName = findViewById(R.id.editObsName);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);
        editComment = findViewById(R.id.editObsLocation);
        editLocation = findViewById(R.id.addObsLocation);

        observation.setHikeId(hikeID);
        observation.setName(editObsName.getText().toString());
        observation.setDate(editDate.getText().toString());
        observation.setTime(editTime.getText().toString());
        observation.setComment(editComment.getText().toString());
        observation.setLocation(editLocation.getText().toString());

        boolean hasError = !verifyBlankEditText(editObsName, editDate, editTime, editComment);

        if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Animal_Sighting.toString().replace("_", " "))) {
            observation.setType(ObservationType.Animal_Sighting);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Vegetation_Sighting.toString().replace("_", " "))) {
            observation.setType(ObservationType.Vegetation_Sighting);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Weather_Condition.toString().replace("_", " "))) {
            observation.setType(ObservationType.Weather_Condition);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Trail_Condition.toString().replace("_", " "))) {
            observation.setType(ObservationType.Trail_Condition);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Other.toString().replace("_", " "))) {
            observation.setType(ObservationType.Other);
        } else {
            Toast.makeText(this, "Please select at least an option for Observation Type", Toast.LENGTH_LONG).show();
            hasError = true;
        }

        if (!hasError) {
            long id = databaseHelper.insertObservationDetails(observation);
            Toast.makeText(this, "Added successfully with id: " + id, Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, ObservationList.class);
            // Pass the data to the ViewDetailsActivity
            i.putExtra("DATA", hikeID);
            startActivity(i);
        }
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ROOT);
        editDate.setText(dateFormat.format(myCalendar.getTime()));
    }

    private void updateTimeLabel() {
        String myFormat = "HH:mm";
        SimpleDateFormat timeFormat = new SimpleDateFormat(myFormat, Locale.ROOT);
        editTime.setText(timeFormat.format(myCalendar.getTime()));
    }

    private boolean verifyBlankEditText(EditText... editText) {
        boolean result = true;

        for (EditText text : editText) {
            if (TextUtils.isEmpty(text.getText().toString().trim())) {
                text.setError("Field cannot be empty");
                result = false;
            }
        }
        return result;
    }
}