package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InstructionsActivity extends AppCompatActivity {

    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        btn = (Button)findViewById(R.id.btnGot);

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Bundle bundle=getIntent().getExtras();
                Intent intent = new Intent(InstructionsActivity.this,MapsActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
