package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class UserActivity extends AppCompatActivity {
    private String name;
    private TextView greeting;
    private TextView added;
    private TextView prize;
    int stars=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        DatabaseHelperStars myDb = new DatabaseHelperStars(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        //Elements
        greeting =(TextView)findViewById(R.id.tvGreeting);
        added=(TextView)findViewById(R.id.tvAdded);
        prize=(TextView)findViewById(R.id.tvPrize);

        Bundle bundle=getIntent().getExtras();
        String stuff = bundle.getString("name");

        stars = myDb.getStars(name).getCount();

        //set greeting
        greeting.setText("Hello, "+stuff+"!");
        added.setText("You have added "+stars+" locations so far! Thank you.");

        int away = 100-stars;
        prize.setText("You are "+away+" many stars away from getting a free prize!");




    }
}
