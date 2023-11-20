package com.comp1786.cw1.obsDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.comp1786.cw1.object.Observation;
import com.comp1786.cw1.obsList.ObservationList;
import com.comp1786.cw1.R;
import com.comp1786.cw1.constant.ObservationType;
import com.comp1786.cw1.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditObservation extends AppCompatActivity {
    public long obsID;
    final Calendar myCalendar = Calendar.getInstance();
    EditText editObsName;
    EditText editTime;
    EditText editDate;
    Spinner spinnerObvType;
    EditText editComment;
    EditText editLocation;
    Button saveButton;
    private DatabaseHelper databaseHelper;
    private Observation observation;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_edit_form);
        editObsName= findViewById(R.id.editObsName);
        editLocation = findViewById(R.id.editObsLocation);
        editTime = findViewById(R.id.editTime);
        editDate = (EditText) findViewById(R.id.editDate);
        editComment = findViewById(R.id.editObsComment);
        spinnerObvType = findViewById(R.id.spinType);
        saveButton = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack);

        int y = myCalendar.get(Calendar.YEAR);
        int m = myCalendar.get(Calendar.MONTH);
        int d = myCalendar.get(Calendar.DAY_OF_MONTH);
        int h = myCalendar.get(Calendar.HOUR_OF_DAY);
        int mi = myCalendar.get(Calendar.MINUTE);

        editDate.setText(d + "/" + (m + 1) + "/" + y);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            obsID = extras.getLong("DATA");
        }

        //get Obs from database
        databaseHelper = new DatabaseHelper(getApplicationContext());
        try {
            observation = databaseHelper.getObservationByID(obsID);
        } catch (IllegalAccessException | ParseException e) {
            throw new RuntimeException(e);
        }

        editLocation.setText(observation.getLocation());
        editObsName.setText(observation.getName());
        editTime.setText(observation.getTime());
        editDate.setText(observation.getDate());
        editComment.setText(observation.getComment());
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                updateDateLabel();
            }
        };
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toObsDetails();
            }
        });
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditObservation.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

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
                new TimePickerDialog(EditObservation.this, time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true)
                        .show();
            }
        });

        ObservationType[] items = ObservationType.values();
        String[] obvTypeStrings = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            obvTypeStrings[i] = items[i].name().replace("_", " ");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, obvTypeStrings);
        spinnerObvType.setAdapter(adapter);



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

    private void toObsDetails() {
        Intent intent = new Intent(this, ObservationDetails.class);
        intent.putExtra("DATA", obsID);
        startActivity(intent);
    }

    private void saveDetails() throws ParseException, IllegalAccessException {


        Observation ob = new Observation();

        ob.setHikeId(observation.getHikeId());
        ob.setId(observation.getId());
        ob.setName(editObsName.getText().toString());
        ob.setDate(editDate.getText().toString());
        ob.setTime(editTime.getText().toString());
        ob.setComment(editComment.getText().toString());
        ob.setLocation(editLocation.getText().toString());

        boolean hasError = false;

        if (!verifyBlankEditText(editObsName, editDate, editTime, editComment)) {
            hasError = true;
        }

        if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Animal_Sighting.toString().replace("_", " "))) {
            ob.setType(ObservationType.Animal_Sighting);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Vegetation_Sighting.toString().replace("_", " "))) {
            ob.setType(ObservationType.Vegetation_Sighting);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Weather_Condition.toString().replace("_", " "))) {
            ob.setType(ObservationType.Weather_Condition);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Trail_Condition.toString().replace("_", " "))) {
            ob.setType(ObservationType.Trail_Condition);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Other.toString().replace("_", " "))) {
            ob.setType(ObservationType.Other);
        } else {
            Toast.makeText(this, "Please select at least an option for Observation Type", Toast.LENGTH_LONG).show();
            hasError = true;
        }

        if (!hasError) {
            long id = databaseHelper.updateObservation(ob);
            if(id >0 ){
                Intent intent = new Intent(this, ObservationList.class);
                intent.putExtra("DATA", observation.getHikeId());
                startActivity(intent);
                Toast.makeText(this, "Added successfully with id: " + id, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ROOT);
        editDate.setText(dateFormat.format(myCalendar.getTime()).toString());
    }
    private void updateTimeLabel() {
        String myFormat = "HH:mm";
        SimpleDateFormat timeFormat = new SimpleDateFormat(myFormat, Locale.ROOT);
        editTime.setText(timeFormat.format(myCalendar.getTime()).toString());
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