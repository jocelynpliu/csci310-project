package com.example.csci310;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ListView currentEvents;
    private TextView tv;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.create_account);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAccountActivity();
            }
        });


        // for now, clicking login button will automatically let you in
        //need to actually check user id, password later
        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){ openHomeActivity();  }
        });



    }

    public void openCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}