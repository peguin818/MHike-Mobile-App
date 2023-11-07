package com.comp1786.cw1.HikeList;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.comp1786.cw1.Homepage_Activity;
import com.comp1786.cw1.R;

public class HikeListViewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hike_list_view_item);
    }

    public void toHikeHomepage(View view) {
        Intent i = new Intent(this,Homepage_Activity.class);
        startActivity(i);
    }
}