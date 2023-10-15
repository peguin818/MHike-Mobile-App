package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.comp1786.cw1.constant.Difficulty;
import com.comp1786.cw1.constant.TrailType;
import com.comp1786.cw1.dbHelper.Hike;
import com.comp1786.cw1.dbHelper.HikeDbHelper;

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
    RadioButton radioButtonPark;

    RadioGroup groupDifficulty;
    RadioButton radioButtonDifficulty;

    Spinner spinnerType;

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

        spinnerType = findViewById(R.id.spinType);
        TrailType[] items = TrailType.values();
        String[] typeStrings = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            typeStrings[i] = items[i].name().replace("_"," ");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeStrings);
        spinnerType.setAdapter(adapter);

        Button saveButton = findViewById(R.id.btnTest);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDetails();
            }
        });
    }

    private void saveDetails() {
        HikeDbHelper hikeDbHelper = new HikeDbHelper(getApplicationContext());

        Hike hike = new Hike();

        editHikeName = findViewById(R.id.editHikeName);
        editLocation = findViewById(R.id.editLocation);
        editDate = findViewById(R.id.editDate);
        editLength = findViewById(R.id.editLength);
        editDescription = findViewById(R.id.editDescription);
        editEContact = findViewById(R.id.editEContact);

        groupPark = findViewById(R.id.rGroupParking);
        groupDifficulty = findViewById(R.id.rGroupDifficulty);

        hike.setHikeName(editHikeName.getText().toString());
        hike.setLocation(editLocation.getText().toString());
        hike.setDate(editDate.getText().toString());

        int selectedParkId = groupPark.getCheckedRadioButtonId();
        radioButtonPark = findViewById(selectedParkId);
        if (radioButtonPark != null) {
            if (radioButtonPark.getId() == R.id.radioParkYes) {
                hike.setParking(true);
            } else if (radioButtonPark.getId() == R.id.radioParkNo){
                hike.setParking(false);
            } else {
                Toast.makeText(this, "Please select at least an option", Toast.LENGTH_LONG).show();
                return;
            }
        }

        hike.setLength(Long.parseUnsignedLong(editLength.getText().toString()));

        if (spinnerType.getSelectedItem().equals(TrailType.RETURN.toString())) {
            hike.setType(TrailType.RETURN);;
        } else if (spinnerType.getSelectedItem() == TrailType.LOOP.toString()) {
            hike.setType(TrailType.LOOP);
        } else if (spinnerType.getSelectedItem() == TrailType.PACK_CARRY.toString().replace("_", " ")) {
            hike.setType(TrailType.PACK_CARRY);
        } else if (spinnerType.getSelectedItem() == TrailType.STAGE.toString()) {
            hike.setType(TrailType.STAGE);
        } else if(spinnerType.getSelectedItem() == TrailType.POINT_TO_POINT.toString().replace("_", " ")){
            hike.setType(TrailType.POINT_TO_POINT);
        } else {
            Toast.makeText(this, "Please select at least an option", Toast.LENGTH_LONG).show();
            return;
        }


        int selectedDifficultyId = groupDifficulty.getCheckedRadioButtonId();
        radioButtonDifficulty = findViewById(selectedDifficultyId);
        if (radioButtonDifficulty.getId() == R.id.radioHard) {
            hike.setDifficulty(Difficulty.HARD);
        } else if ( radioButtonDifficulty.getId() == R.id.radioNormal) {
            hike.setDifficulty(Difficulty.NORMAL);
        } else if (radioButtonDifficulty.getId() == R.id.radioEasy) {
            hike.setDifficulty(Difficulty.EASY);
        } else {
            Toast.makeText(this, "Please select at least an option", Toast.LENGTH_LONG).show();
            return;
        }

        hike.setDescription(editDescription.getText().toString());
        hike.setContact(editEContact.getText().toString());

        long id = hikeDbHelper.insertHikeDetails(hike);
        Toast.makeText(this, "Added successfully with id: " + id, Toast.LENGTH_LONG).show();
    }

    private void updateDateLabel(){
        String myFormat="dd/MM/yyyy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.ROOT);
        editDate.setText(dateFormat.format(myCalendar.getTime()).toString());
    }
}