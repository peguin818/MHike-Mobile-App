package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.comp1786.cw1.hikeList.HikeList;
import com.comp1786.cw1.obsList.ObservationList;
import com.comp1786.cw1.database.DatabaseHelper;
import com.comp1786.cw1.hikeLayouts.NewHike;
import com.comp1786.cw1.obsDetails.NewObservation;

public class Homepage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    public void toHikeList(View view) {
        Intent i = new Intent(this, HikeList.class);
        startActivity(i);
    }
    public void toHikeForm(View view) {
        Intent i = new Intent(this, NewHike.class);
        startActivity(i);
    }
    public void toObvForm (View view)
    {
        Intent i = new Intent(this, NewObservation.class);
        startActivity(i);
    }
    public void toObvList(View view) {
        Intent i = new Intent(this, ObservationList.class);
        startActivity(i);
    }
}