package com.example.application;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter=5;
    private TextView SignUp;
    DatabaseHelperUsers myDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelperUsers(this);

        //All elements
        Name = (EditText)findViewById(R.id.etName);
        Password=(EditText)findViewById(R.id.etPassword);
        Info =(TextView)findViewById(R.id.tvInfo);
        Login=(Button)findViewById(R.id.btnLogin);
        SignUp=(TextView)findViewById(R.id.tvSignUp);


        //button click listener
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //validate input
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        //sign up click listener
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }

    private void validate(String userName, String userPassword){
        if(checkUser(userName,userPassword)
               // (userName.equals("admin")) && (userPassword.equals("1234"))
                ){
         //go to next activity
            Bundle bundle=new Bundle();
            bundle.putString("name",userName);
            Intent intent = new Intent(MainActivity.this, InstructionsActivity.class).putExtras(bundle);
            startActivity(intent);
        }else{
            counter--;
            Info.setText("Invalid credentials. No of attempts remaining: "+String.valueOf(counter));
            if(counter==0){
                //disable button
                Login.setEnabled(false);
            }

        }
    }

    private boolean checkUser(String un, String pw){
        //go through db and see if un and pw are in there
        //Get all points from sql and add as points onto map
        Cursor res =myDb.getAllUsers();
        if(res.getCount()!=0){
            while(res.moveToNext()){
                String user=res.getString(1);
                String pass=res.getString(2);
                if(un.equals(user)&&pw.equals((pass))){
                    return true;
                }
            }
        }
        return false;
    }

}
