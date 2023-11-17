package com.comp1786.cw1.obsList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.comp1786.cw1.Homepage_Activity;
import com.comp1786.cw1.R;

public class ObservationListViewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_list_view_item);
    }
    public void toHikeHomepage(View view) {
        Intent i = new Intent(this, Homepage_Activity.class);
        startActivity(i);
    }
}