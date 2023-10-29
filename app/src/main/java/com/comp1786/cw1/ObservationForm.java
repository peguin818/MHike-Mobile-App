package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.comp1786.cw1.Entity.Observation;
import com.comp1786.cw1.constant.ObservationType;
import com.comp1786.cw1.dbHelper.HikeDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ObservationForm extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText editObvDescription;
    EditText editDate2;
    EditText editComment;
    Spinner spinnerObvType;
    Button saveButton;
    EditText editTime;
/*
    int hour, minute;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_form);
        editDate2 = (EditText) findViewById(R.id.editDate2);
        Calendar c =  Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        int h = c.get(Calendar.HOUR_OF_DAY);
        int mi = c.get(Calendar.MINUTE);


        editDate2.setText(d + "/" + (m+1) + "/" +y);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);

                updateDateLabel();
            }
        };
        editDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ObservationForm.this, date, myCalendar.get(Calendar.YEAR),
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
        editTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                new TimePickerDialog(ObservationForm.this, time,
                        myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE),true)
                        .show();
            }
        });
        spinnerObvType = findViewById(R.id.spinObvType);
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
    private void saveDetails() throws ParseException, IllegalAccessException {

        HikeDbHelper hikeDbHelper = new HikeDbHelper(getApplicationContext());
        Observation observation = new Observation();

        editObvDescription = findViewById(R.id.editObvDescription);
        editDate2 = findViewById(R.id.editDate2);
        editTime = findViewById(R.id.editTime);
        editComment = findViewById(R.id.editComment2);

        observation.setDescription(editObvDescription.getText().toString());
        observation.setDate(editDate2.getText().toString());
        observation.setTime(editTime.getText().toString());
        observation.setComment(editComment.getText().toString());

        boolean hasError = false;

        if (!verifyBlankEditText(editObvDescription, editDate2, editTime, editComment)) {
            hasError = true;
        }

        if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Animal_Sighting.toString())) {
            observation.setType(ObservationType.Animal_Sighting);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Vegetation_Sighting.toString())) {
            observation.setType(ObservationType.Vegetation_Sighting);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Weather_Condition.toString().replace("_", " "))) {
            observation.setType(ObservationType.Weather_Condition);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Trail_Condition.toString())) {
            observation.setType(ObservationType.Trail_Condition);
        } else if (spinnerObvType.getSelectedItem().toString().equals(ObservationType.Other.toString().replace("_", " "))) {
            observation.setType(ObservationType.Other);
        } else {
            Toast.makeText(this, "Please select at least an option for Observation Type", Toast.LENGTH_LONG).show();
            hasError = true;
        }

        if (!hasError) {
            long id = hikeDbHelper.insertObservationDetails(observation);
            Toast.makeText(this, "Added successfully with id: " + id, Toast.LENGTH_LONG).show();
        }
    }

        private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ROOT);
        editDate2.setText(dateFormat.format(myCalendar.getTime()).toString());
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