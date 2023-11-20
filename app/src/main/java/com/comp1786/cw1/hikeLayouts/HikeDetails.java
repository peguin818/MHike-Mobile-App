package com.comp1786.cw1.hikeLayouts;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.comp1786.cw1.object.Hike;
import com.comp1786.cw1.hikeList.HikeList;
import com.comp1786.cw1.obsList.ObservationList;
import com.comp1786.cw1.R;
import com.comp1786.cw1.database.DatabaseHelper;

public class HikeDetails extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
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
    Button toObsButton;
    long hikeID;


    ImageView btnBack;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_details);

        hikeName = findViewById(R.id.hikeName);
        location = findViewById(R.id.hikeLocation);
        date = findViewById(R.id.hikeDate);
        parking = findViewById(R.id.hikeParking);
        length = findViewById(R.id.hikeLength);
        type = findViewById(R.id.hikeType);
        difficulty = findViewById(R.id.hikeDifficulty);
        eContact = findViewById(R.id.hikeEmergency);
        description = findViewById(R.id.hikeDescription);
        deleteHikeButton = findViewById(R.id.btnDeleteHike);
        editHikeButton = findViewById(R.id.btnEditHike);
        toObsButton = findViewById(R.id.btnObservation);
         btnBack= findViewById(R.id.btnBack);
         btnBack.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 toHikeList();
             }
         });
         toObsButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(hike != null){
                     toObsList();
                 }}
         });

        //extract data form list
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            hikeID = extras.getLong("DATA");
        }
        //get Hike from database
        databaseHelper = new DatabaseHelper(getApplicationContext());
        try {
            hike = databaseHelper.getHikeById(hikeID);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Load data to view
        hikeName.setText(hike.getHikeName());
        location.setText(hike.getLocation());
        date.setText(hike.getDate());
        parking.setText(String.valueOf(hike.isParking()));
        length.setText(String.valueOf(hike.getLength()));
        type.setText(hike.getType().toString().replace("_", " "));
        difficulty.setText(String.valueOf(hike.getDifficulty()));
        eContact.setText(hike.getContact());
        description.setText(hike.getDescription());


        deleteHikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(hike != null){
                    deleteHike();
                }}
        });
        editHikeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(hike != null){
                    editHike();
                }

            }
        });
    }

    public void deleteHike(){
        databaseHelper.deleteHikeById(hike.getId());
        Toast.makeText(this, "Deleted HIKE successfully with id: " + hike.getId(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, HikeList.class);
        startActivity(i);
    }
    public void editHike(){
        Intent intent = new Intent(this, EditHike.class);
        intent.putExtra("DATA", hikeID);
        startActivity(intent);
    }
    public void toObsList(){
        Intent intent = new Intent(this, ObservationList.class);
        intent.putExtra("DATA", hikeID);
        startActivity(intent);
    }
    public void toHikeList(){
        Intent intent = new Intent(this, HikeList.class);
        intent.putExtra("DATA", hikeID);
        startActivity(intent);
    }
}