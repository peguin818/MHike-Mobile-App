package com.comp1786.cw1.hikeLayouts;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comp1786.cw1.object.Hike;
import com.comp1786.cw1.hikeList.HikeList;
import com.comp1786.cw1.Homepage_Activity;
import com.comp1786.cw1.R;
import com.comp1786.cw1.constant.Difficulty;
import com.comp1786.cw1.constant.TrailType;
import com.comp1786.cw1.database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewHike extends AppCompatActivity {
    final Calendar myCalendar = Calendar.getInstance();
    EditText editHikeName;
    EditText editLocation;
    EditText editDate;
    EditText editLength;
    EditText editEContact;
    EditText editDescription;
    Long parsedLength;
    RadioGroup groupPark;
    RadioButton radioButtonPark;
    RadioGroup groupDifficulty;
    RadioButton radioButtonDifficulty;
    Spinner spinnerType;
    Button saveButton;
    ImageView btnBack;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, HikeList.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_add_form);

        editDate = (EditText) findViewById(R.id.editDate);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //gets current date
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
                new DatePickerDialog(NewHike.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        spinnerType = findViewById(R.id.spinType);
        TrailType[] items = TrailType.values();
        String[] typeStrings = new String[items.length];
        for (int i = 0; i < items.length; i++) {
            typeStrings[i] = items[i].name().replace("_", " ");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeStrings);
        spinnerType.setAdapter(adapter);

        saveButton = findViewById(R.id.btnDeleteHike);
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

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        Hike hike = new Hike();

        editHikeName = findViewById(R.id.btnEditHike);
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
        hike.setDescription(editDescription.getText().toString());
        hike.setContact(editEContact.getText().toString());

        boolean hasError = !verifyBlankEditText(editHikeName, editLocation, editDate, editLength, editDescription, editEContact);

        int selectedParkId = groupPark.getCheckedRadioButtonId();
        radioButtonPark = findViewById(selectedParkId);
        if (radioButtonPark != null) {
            if (radioButtonPark.getId() == R.id.radioParkYes) {
                hike.setParking(true);
            } else if (radioButtonPark.getId() == R.id.radioParkNo) {
                hike.setParking(false);
            }
        } else {
            Toast.makeText(this, "Please select at least an option for Parking Availability", Toast.LENGTH_LONG).show();
            hasError = true;
        }

        if (spinnerType.getSelectedItem().toString().equals(TrailType.Boardwalks.toString())) {
            hike.setType(TrailType.Boardwalks);
        } else if (spinnerType.getSelectedItem().toString().equals(TrailType.Bikeways.toString())) {
            hike.setType(TrailType.Bikeways);
        } else if (spinnerType.getSelectedItem().toString().equals(TrailType.No_Trail.toString().replace("_", " "))) {
            hike.setType(TrailType.No_Trail);
        } else if (spinnerType.getSelectedItem().toString().equals(TrailType.Foot.toString())) {
            hike.setType(TrailType.Foot);
        } else if (spinnerType.getSelectedItem().toString().equals(TrailType.Nature.toString().replace("_", " "))) {
            hike.setType(TrailType.Nature);
        } else {
            Toast.makeText(this, "Please select at least an option for Trail Type", Toast.LENGTH_LONG).show();
            hasError = true;
        }

        int selectedDifficultyId = groupDifficulty.getCheckedRadioButtonId();
        radioButtonDifficulty = findViewById(selectedDifficultyId);
        if (radioButtonDifficulty != null) {
            if (radioButtonDifficulty.getId() == R.id.radioHard) {
                hike.setDifficulty(Difficulty.HARD);
            } else if (radioButtonDifficulty.getId() == R.id.radioNormal) {
                hike.setDifficulty(Difficulty.MEDIUM);
            } else if (radioButtonDifficulty.getId() == R.id.radioEasy) {
                hike.setDifficulty(Difficulty.EASY);
            }
        } else {
            Toast.makeText(this, "Please select at least an option for Difficulty", Toast.LENGTH_LONG).show();
            hasError = true;
        }

        try {
            parsedLength = Long.parseUnsignedLong(editLength.getText().toString());
            hike.setLength(parsedLength);
        } catch (Exception e) {
            editLength.setError("Please enter a number higher than 0");
        }

        if (!hasError) {
            long id = databaseHelper.insertHikeDetails(hike);

            Toast.makeText(this, "Added successfully with id: " + id, Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, HikeList.class);
            startActivity(i);
        }

    }

    private void updateDateLabel() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.ROOT);
        editDate.setText(dateFormat.format(myCalendar.getTime()));
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

    public void toHikeHomepage(View view) {
        Intent i = new Intent(this, Homepage_Activity.class);
        startActivity(i);
    }
}