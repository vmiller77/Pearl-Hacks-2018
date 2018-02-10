package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private String name;
    private TextView greeting;
    private TextView added;
    private TextView prize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Elements
        greeting = (TextView)findViewById(R.id.tvGreeting);
        added=(TextView)findViewById(R.id.tvAdded);
        prize=(TextView)findViewById(R.id.tvPrize);

        DatabaseHelperUsers myDb = new DatabaseHelperUsers(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        name = getIntent().getStringExtra("StringName");

        //set greeting
        //greeting.setText("Hi, ");

//
//        added.setText("You have added "+" locations so far! Thank you.");
//
//        //Use name to get number of stars
//
//
//        prize.setText("You are "+" many stars away from getting a free prize!");


    }
}
