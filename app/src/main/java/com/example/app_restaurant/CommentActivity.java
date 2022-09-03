package com.example.app_restaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getActionBar().setDisplayHomeAsUpEnabled(true);

    }
}