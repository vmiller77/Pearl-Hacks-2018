package com.example.application;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;

public class SignUpActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    private EditText ConfirmPassword;
    private Button SignUp;
    private TextView Invalid;
    DatabaseHelperUsers myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        myDb = new DatabaseHelperUsers(this);

        //setting elements to values
        Username = (EditText)findViewById(R.id.etSUUsername);
        Password = (EditText)findViewById(R.id.etSUPassword);
        ConfirmPassword = (EditText)findViewById(R.id.etSUConfirmPassword);
        SignUp=(Button)findViewById(R.id.btnSignUp);
        Invalid=(TextView)findViewById(R.id.tvInvalid);

        //sign up click listener
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Username.getText().toString(),Password.getText().toString(),ConfirmPassword.getText().toString());
            }
        });

    }

    private void validate(String username,String password, String confirmPassword){
        if(password.equals(confirmPassword)){
            if(!userExist(username)){
                addUser(username,password);
                Bundle bundle=new Bundle();
                bundle.putString("name",username);
                Intent intent = new Intent(SignUpActivity.this, InstructionsActivity.class).putExtras(bundle);
                startActivity(intent);
            }else{
                Invalid.setText("Username already taken! Try again.");
            }

        }else{
            //passwords dont match do dont let through
            Invalid.setText("Passwords do not match!");
        }
    }

    public void addUser(String un, String pw){
        myDb.insertUser(un,pw);
    }

    private boolean userExist (String un){
        Cursor res =myDb.getAllUsers();
        if(res.getCount()!=0){
            while(res.moveToNext()){
                String user=res.getString(1);
                if(un.equals(user)){
                    return true;
                }
            }
        }
        return false;
    }


}
