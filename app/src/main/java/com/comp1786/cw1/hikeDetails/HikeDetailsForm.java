package com.comp1786.cw1.hikeDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.comp1786.cw1.Entity.Hike;
import com.comp1786.cw1.HikeList.HikeListActivity;
import com.comp1786.cw1.R;
import com.comp1786.cw1.dbHelper.HikeDbHelper;

public class HikeDetailsForm extends AppCompatActivity {

    private HikeDbHelper hikeDbHelper;
    private Hike hike;

    TextView hikeName;
    TextView location;
    TextView date;
    TextView parking;
    TextView length;
    TextView type;
    TextView difficulty;
    TextView eContact;
    TextView description;
    Button deleteHikeButton;
    Button editHikeButton;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_details_form);
        hikeName = findViewById(R.id.hikeName);
        location = findViewById(R.id.location);
        date = findViewById(R.id.date);
        parking = findViewById(R.id.parking);
        length = findViewById(R.id.length);
        type = findViewById(R.id.type);
        difficulty = findViewById(R.id.difficulty);
        eContact = findViewById(R.id.eContact);
        description = findViewById(R.id.desciprtion);
        deleteHikeButton = findViewById(R.id.btnDeleteHike);
        editHikeButton = findViewById(R.id.editHikeName);

        //extract data form list
        Bundle extras = getIntent().getExtras();
        long id = 0;
        if (extras != null) {
            id = extras.getLong("DATA");
        }
        //get Hike from database
        hikeDbHelper = new HikeDbHelper(getApplicationContext());
        try {
            hike = hikeDbHelper.getHikeById(id);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(hike);

        // Load data to view
        hikeName.setText(hike.getHikeName());
        location.setText(hike.getLocation());
        date.setText(hike.getDate());
        parking.setText(String.valueOf(hike.isParking()));
        length.setText(String.valueOf(hike.getLength()));
        type.setText(String.valueOf(hike.getType()));
        difficulty.setText(String.valueOf(hike.getDifficulty()));
        eContact.setText(hike.getContact());
        description.setText(hike.getDescription());

        deleteHikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(hike != null){
                    deleteHike();
                }

            }
        });
    }

    public void deleteHike(){
        hikeDbHelper.deleteHikeById(hike.getId());
        Toast.makeText(this, "Deleted HIKE successfully with id: " + hike.getId(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, HikeListActivity.class);
        startActivity(i);
    }
}