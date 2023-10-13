package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Dm mày phúc ơi
    }

    public void handleButtonClick(View v) {
        EditText editHikeName = (EditText) findViewById(R.id.editHikeName);
        EditText editLocation = (EditText) findViewById(R.id.editLocation);
        EditText editDate = (EditText) findViewById(R.id.editDate);
        EditText editLength = (EditText) findViewById(R.id.editLength);
        EditText editDescription = (EditText) findViewById(R.id.editDescription);

        RadioGroup groupPark = (RadioGroup) findViewById(R.id.radioParking);
        RadioGroup groupDifficulty = (RadioGroup) findViewById(R.id.radioDifficulty);
        RadioGroup groupVehicle = (RadioGroup) findViewById(R.id.radioGroupVehicle);

        String strHikeName = editHikeName.getText().toString();
        String strLocation = editLocation.getText().toString();
        String strDate = editDate.getText().toString();
        String strLength = editLength.getText().toString();
        String strDescription = editDescription.getText().toString();

        verifyIfEditTextIsFilled(editHikeName, editLocation, editDate, editLength, editDescription);
    }

    private boolean verifyIfEditTextIsFilled(EditText... editText) {

        boolean result = true;

        for (EditText text : editText) {

            if (TextUtils.isEmpty(text.getText().toString().trim())) { //text.getText().toString().isEmpty() || text.getText().toString().length() == 0 || text.getText().toString().equals("")
                text.setError("Field cannot be empty");
                result = false;
            }
        }
        return result;
    }
}