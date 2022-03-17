package com.example.csci310;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button homeButton;
    String s1[];

    RecyclerView homeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setAlpha(.5f);

        s1 = getResources().getStringArray(R.array.testEvents);

       homeRecyclerView = findViewById(R.id.homeRecyclerView);

        HomeRecyclerAdapter myAdapter = new HomeRecyclerAdapter (this, s1);
        homeRecyclerView.setAdapter(myAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
