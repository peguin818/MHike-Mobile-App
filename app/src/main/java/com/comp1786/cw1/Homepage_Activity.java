package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.comp1786.cw1.HikeList.HikeListActivity;
import com.comp1786.cw1.ObservationList.ObservationListActivity;
import com.comp1786.cw1.dbHelper.HikeDbHelper;
import com.comp1786.cw1.hikeDetails.HikeAddForm;
import com.comp1786.cw1.obsDetails.ObservationAddForm;

public class Homepage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        HikeDbHelper hikeDbHelper = new HikeDbHelper(getApplicationContext());
    }

    public void toHikeList(View view) {
        Intent i = new Intent(this, HikeListActivity.class);
        startActivity(i);
    }
    public void toHikeForm(View view) {
        Intent i = new Intent(this, HikeAddForm.class);
        startActivity(i);
    }
    public void toObvForm (View view)
    {
        Intent i = new Intent(this, ObservationAddForm.class);
        startActivity(i);
    }
    public void toObvList(View view) {
        Intent i = new Intent(this, ObservationListActivity.class);
        startActivity(i);
    }
}