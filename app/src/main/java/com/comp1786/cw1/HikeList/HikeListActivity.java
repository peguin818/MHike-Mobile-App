package com.comp1786.cw1.HikeList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.comp1786.cw1.Entity.Hike;
import com.comp1786.cw1.HikeForm;
import com.comp1786.cw1.R;
import com.comp1786.cw1.dbHelper.HikeDbHelper;
import com.comp1786.cw1.hikeDetails.HikeDetailsForm;

import java.util.ArrayList;
import java.util.List;

public class HikeListActivity extends AppCompatActivity {
    List<Hike> hikeList = new ArrayList<>();
    ArrayList<Hike> filteredHikeList = new ArrayList<>();
    ListView listView;
    SearchView searchView;
    HikeListViewAdapter hikeListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list);

        HikeDbHelper hikeDbHelper = new HikeDbHelper(getApplicationContext());

        try {
            hikeList = hikeDbHelper.getHikeDetails();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        listView = (ListView) findViewById(R.id.hikeListView);
        hikeListViewAdapter = new HikeListViewAdapter(getApplicationContext(), hikeList);
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

        searchView = findViewById(R.id.hikeSearchView);
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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView = (ListView) findViewById(R.id.hikeListView);
        hikeListViewAdapter = new HikeListViewAdapter(getApplicationContext(), hikeList);
        listView.setAdapter(hikeListViewAdapter);
    }

    public void toHikeAddForm(View view) {
        Intent i = new Intent(this, HikeAddForm.class);
        startActivity(i);
    }

    private void filter(String text) {
        filteredHikeList = new ArrayList<Hike>();

        for (Hike item : hikeList) {
            if (item.getHikeName().toLowerCase().contains(text.toLowerCase())) {
                filteredHikeList.add(item);
            }
        }
        if (filteredHikeList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            hikeListViewAdapter.filterList(filteredHikeList);
        }
    }

/*
    public void toHikeDetails(View view) {
        Intent j = new Intent(this, HikeDetails.class);
        startActivity(j);
    }
*/

}