package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.comp1786.cw1.constant.ObservationType;

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

    Button editTime;
    int hour, minute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_form);
        editDate2 = (EditText) findViewById(R.id.editDate2);
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
        spinnerObvType = findViewById(R.id.spinObvType);
        ObservationType[] items = ObservationType.values();
        String[] obvTypeStrings = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            obvTypeStrings[i] = items[i].name().replace("_", " ");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, obvTypeStrings);
        spinnerObvType.setAdapter(adapter);

        editTime = findViewById(R.id.btnTime);

        saveButton = findViewById(R.id.btnSave);

    }
    public void popTimePicker(View view){
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                editTime.setText(String.format(Locale.getDefault(), "%02d:%02d", hour,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);
        timePickerDialog.show();
    }
    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ROOT);
        editDate2.setText(dateFormat.format(myCalendar.getTime()).toString());
    }
}