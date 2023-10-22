package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.comp1786.cw1.constant.ObservationType;
import com.comp1786.cw1.constant.Weather;

import java.util.Calendar;

public class ObservationForm extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText editObvName;
    EditText editTrail;
    EditText editDate2;
    EditText editTime;
    EditText editComment;
    Spinner spinnerWeather;
    Spinner spinnerObvType;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_form);
        /*editDate2 = (EditText) findViewById(R.id.editDate2);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
*//*
                updateDateLabel();
*//*
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
        });*/
        spinnerWeather = findViewById(R.id.spinWeather);
        Weather[] items = Weather.values();
        String[] weatherStrings = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            weatherStrings[i] = items[i].name();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, weatherStrings);
        spinnerWeather.setAdapter(adapter);

        spinnerObvType = findViewById(R.id.spinObvType);
        ObservationType[] items2 = ObservationType.values();
        String[] obvTypeStrings = new String[items2.length];
        for (int i = 0; i < items2.length; i++) {
            obvTypeStrings[i] = items2[i].name();
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, obvTypeStrings);
        spinnerWeather.setAdapter(adapter2);

/*
        saveButton = findViewById(R.id.saveButton);
*/

    }
}