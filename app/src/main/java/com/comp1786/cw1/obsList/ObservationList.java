package com.comp1786.cw1.obsList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.comp1786.cw1.object.Observation;

import com.comp1786.cw1.hikeLayouts.HikeDetails;
import com.comp1786.cw1.obsDetails.ObservationDetails;
import com.comp1786.cw1.R;
import com.comp1786.cw1.database.DatabaseHelper;
import com.comp1786.cw1.obsDetails.NewObservation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ObservationList extends AppCompatActivity {
    List<Observation> observationList = new ArrayList<>();
/*    ArrayList<Observation> filteredObservationList = new ArrayList<>();
    SearchView searchView;*/
    ListView listView;
    ObservationListViewAdapter observationListViewAdapter;
    ImageView btnBack;
    ImageView btnAdd;
    long hikeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_list);

        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        btnBack= findViewById(R.id.btnBack);
        btnAdd= findViewById(R.id.obsListAdd);

        //extract data form list
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hikeID = extras.getLong("DATA");
        }

        try {
            observationList = databaseHelper.getObservationList(hikeID);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHikeDetailsPage();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toObsAdd();
            }
        });
        listView = (ListView) findViewById(R.id.obsListView);
        observationListViewAdapter = new ObservationListViewAdapter(getApplicationContext(), observationList);
        listView.setAdapter(observationListViewAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the data associated with the clicked item
                long data = listView.getAdapter().getItemId(position);

                // Create an Intent to start the ViewDetailsActivity
                Intent intent = new Intent(ObservationList.this, ObservationDetails.class);

                // Pass the data to the ViewDetailsActivity
                intent.putExtra("DATA", data);

                // Start the activity
                startActivity(intent);
            }
        });

/*        searchView = findViewById(R.id.obsSearchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });*/
    }
    @Override
    protected void onResume() {
        super.onResume();
        listView = (ListView) findViewById(R.id.obsListView);
        observationListViewAdapter = new ObservationListViewAdapter(getApplicationContext(), observationList);
        listView.setAdapter(observationListViewAdapter);
    }

    public void toObsAdd() {
        Intent i = new Intent(this, NewObservation.class);
        i.putExtra("DATA", hikeID);
        startActivity(i);
    }
    private void toHikeDetailsPage(){
        Intent intent = new Intent(this, HikeDetails.class);
        intent.putExtra("DATA", hikeID);
        startActivity(intent);
    }

/*    private void filter(String text) {
        filteredObservationList = new ArrayList<Observation>();

        for (Observation item : observationList) {
            if (item.getType().toLowerCase().contains(text.toLowerCase())) {
                filteredObservationList.add(item);
            }
        }
        if (filteredObservationList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            observationListViewAdapter.filterList(filteredObservationList);
        }
    }*/
}