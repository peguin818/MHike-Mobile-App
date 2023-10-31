package com.comp1786.cw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.comp1786.cw1.HikeList.HikeListActivity;

public class Homepage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }

    public void toHikeList(View view) {
        Intent i = new Intent(this, HikeListActivity.class);
        startActivity(i);
    }
}