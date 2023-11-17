package com.comp1786.cw1.obsDetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.comp1786.cw1.object.Observation;
import com.comp1786.cw1.obsList.ObservationListActivity;
import com.comp1786.cw1.R;
import com.comp1786.cw1.dbHelper.HikeDbHelper;

import java.text.ParseException;

public class ObservationDetailsForm extends AppCompatActivity {
    public long obsID;
    TextView textObsName;
    TextView textDate;
    TextView textComment;
    TextView textType;
    Button saveButton;
    TextView textTime;
    ImageView btnBack;
    Button deleteObsButton;
    Button editObsButton;
    private HikeDbHelper hikeDbHelper;
    private Observation observation;
    private TextView textLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observation_details_form);
        textLocation = findViewById(R.id.obsLocation);
        textDate = findViewById(R.id.obsDate);
        textObsName = findViewById(R.id.obsName);
        textTime = findViewById(R.id.obsTime);
        textType = findViewById(R.id.obsType);
        textComment = findViewById(R.id.obsComment);
        editObsButton = findViewById(R.id.btnEditObs);
        deleteObsButton = findViewById(R.id.btnDeleteObs);
        btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toObsList();
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            obsID = extras.getLong("DATA");
        }

        hikeDbHelper = new HikeDbHelper(getApplicationContext());
        try {
            observation = hikeDbHelper.getObservationByID(obsID);
        } catch (IllegalAccessException | ParseException e) {
            throw new RuntimeException(e);
        }

        textLocation.setText(observation.getLocation());
        textObsName.setText(observation.getName());
        textTime.setText(observation.getTime());
        textDate.setText(observation.getDate());
        textComment.setText(observation.getComment());
        textType.setText(observation.getType().toString().replace("_", " "));
        deleteObsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (observation != null) {
                    deleteObs();
                }
            }
        });
        editObsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (observation != null) {
                    editObs();
                }

            }
        });
    }

    private void toObsList() {
        Intent intent = new Intent(this, ObservationListActivity.class);
        intent.putExtra("DATA", obsID);
        startActivity(intent);
    }

    public void deleteObs() {
        hikeDbHelper.deleteObservationById(observation.getId());
        Toast.makeText(this, "Deleted Observation successfully with id: " + observation.getId(), Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, ObservationListActivity.class);
        startActivity(i);
    }

    public void editObs() {
        Intent intent = new Intent(this, ObservationEditForm.class);
        intent.putExtra("DATA", obsID);
        startActivity(intent);
    }
}