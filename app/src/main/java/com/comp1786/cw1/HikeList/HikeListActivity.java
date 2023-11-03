package com.comp1786.cw1.HikeList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.comp1786.cw1.Entity.Hike;
import com.comp1786.cw1.Homepage_Activity;
import com.comp1786.cw1.hikeDetails.HikeAddForm;
import com.comp1786.cw1.ObservationForm;
import com.comp1786.cw1.R;
import com.comp1786.cw1.dbHelper.HikeDbHelper;
import com.comp1786.cw1.hikeDetails.HikeDetailsForm;

import java.util.ArrayList;
import java.util.List;

public class HikeListActivity extends AppCompatActivity {
    List<Hike> testList = new ArrayList<>();

    ListView listView;
    ImageView btnBack;
    ImageView btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);

        HikeDbHelper hikeDbHelper = new HikeDbHelper(getApplicationContext());
        btnBack= findViewById(R.id.btnBack);
        btnAdd= findViewById(R.id.btnAdd);
        try {
            testList = hikeDbHelper.getHikeDetails();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHikeList();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHikeAddForm();
            }
        });


        listView = (ListView) findViewById(R.id.hikeListView);
        HikeListViewAdapter hikeListViewAdapter = new HikeListViewAdapter(getApplicationContext(), testList);
        listView.setAdapter(hikeListViewAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the data associated with the clicked item
                long data = listView.getAdapter().getItemId(position);

                // Create an Intent to start the ViewDetailsActivity
                Intent intent = new Intent(HikeListActivity.this, HikeDetailsForm.class);

                // Pass the data to the ViewDetailsActivity
                intent.putExtra("DATA", data);

                // Start the activity
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView = (ListView) findViewById(R.id.hikeListView);
        HikeListViewAdapter hikeListViewAdapter = new HikeListViewAdapter(getApplicationContext(), testList);
        listView.setAdapter(hikeListViewAdapter);
    }

    private void toHikeAddForm() {
        Intent i = new Intent(this, HikeAddForm.class);
        startActivity(i);
    }
    private void toHikeList(){
        Intent i = new Intent(this, Homepage_Activity.class);
        startActivity(i);
    }
}