package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editHikeName;
    EditText editLocation;
    EditText editDate;
    EditText editLength;
    EditText editEContact;
    EditText editDescription;

    RadioGroup groupPark;
    RadioGroup groupDifficulty;

    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editDate = (EditText) findViewById(R.id.editDate);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, day);
                updateDateLabel();
            }
        };
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//
//        btnTest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                verifyIfEditTextIsFilled(editHikeName, editLocation, editDate, editLength, editDescription);
//                verifyIfRadioGroupIsChecked(groupPark, groupDifficulty, groupVehicle);
//            }
//        });
    }

    public void handleButtonClick(View v) {
        editHikeName = findViewById(R.id.editHikeName);
        editLocation = findViewById(R.id.editLocation);
        editDate = findViewById(R.id.editDate);
        editLength = findViewById(R.id.editLength);
        editEContact = findViewById(R.id.editEContact);
        editDescription = findViewById(R.id.editDescription);

        groupPark = findViewById(R.id.radioParking);
        groupDifficulty = findViewById(R.id.radioDifficulty);

        verifyIfEditTextIsFilled(editHikeName, editLocation, editDate, editLength, editEContact, editDescription);
        verifyIfRadioGroupIsChecked(groupPark, groupDifficulty);
    }

    private void updateDateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ROOT);
        editDate.setText(dateFormat.format(myCalendar.getTime()).toString());
    }

    private boolean verifyIfEditTextIsFilled(EditText... editText) {

        boolean result = true;

        for (EditText text : editText) {
            if (TextUtils.isEmpty(text.getText().toString().trim())) {
                text.setError("Field cannot be empty");
                result = false;
            }
        }
        return result;
    }

    private boolean verifyIfRadioGroupIsChecked(RadioGroup... radioGroups) {
        boolean result = true;
        int[] selectedId = new int[0];

        for (RadioGroup radioGroup : radioGroups) {
            if (radioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getApplicationContext(), "Please select an option", Toast.LENGTH_LONG).show();
                result = false;
            }
        }
        return result;
    }
}