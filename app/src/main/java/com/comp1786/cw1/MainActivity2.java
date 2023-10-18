package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inputform);


    }

    public void handleButtonClick(View v) {
        EditText editAnimal = (EditText) findViewById(R.id.editAnimal);
        EditText editVegetable = (EditText) findViewById(R.id.editVegetable);
        EditText editWeather = (EditText) findViewById(R.id.editWeather);
        EditText editTrail = (EditText) findViewById(R.id.editTrail);
        EditText editTime = (EditText) findViewById(R.id.editTime);
        EditText editComment = (EditText) findViewById(R.id.editComment);

        String strAnimal = editAnimal.getText().toString();
        String strVegetable = editVegetable.getText().toString();
        String strWeather = editWeather.getText().toString();
        String strTrail = editTrail.getText().toString();
        String strTime = editTime.getText().toString();
        String strComment = editComment.getText().toString();

        verifyIfEditTextIsFilled(editAnimal, editVegetable, editWeather, editTrail, editTime);
    }

    private boolean verifyIfEditTextIsFilled(EditText... editText ) {
        boolean result = true;

        for (EditText text : editText) {
            if (TextUtils.isEmpty((text.getText().toString().trim()))) {
                text.setError("This field is required.");
                result = false;
            }
        }
        return result;
    }
}