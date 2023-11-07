package com.comp1786.cw1.HikeList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.comp1786.cw1.Entity.Hike;
import com.comp1786.cw1.HikeForm;
import com.comp1786.cw1.R;
import com.comp1786.cw1.dbHelper.HikeDbHelper;

import java.util.ArrayList;
import java.util.List;

public class HikeListActivity extends AppCompatActivity {
    List<Hike> testList = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);

        HikeDbHelper hikeDbHelper = new HikeDbHelper(getApplicationContext());

        try {
            testList = hikeDbHelper.getHikeDetails();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        listView = (ListView) findViewById(R.id.hikeListView);
        HikeListViewAdapter hikeListViewAdapter = new HikeListViewAdapter(getApplicationContext(), testList);
        listView.setAdapter(hikeListViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView = (ListView) findViewById(R.id.hikeListView);
        HikeListViewAdapter hikeListViewAdapter = new HikeListViewAdapter(getApplicationContext(), testList);
        listView.setAdapter(hikeListViewAdapter);
    }

    public void toHikeAddForm(View view) {
        Intent i = new Intent(this, HikeForm.class);
        startActivity(i);
    }

/*
    public void toHikeDetails(View view) {
        Intent j = new Intent(this, HikeDetails.class);
        startActivity(j);
    }
*/

}