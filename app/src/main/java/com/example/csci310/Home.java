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

        //just for testing purposes
//        s1 = getResources().getStringArray(R.array.testEvents);
        s1 = new String[]{"event 1", "event 2", "event3", "event4",
                "event 5", "event 6", "event 7", "event 8", "event 9",
        "event 10", "event 11"};

       homeRecyclerView = findViewById(R.id.homeRecyclerView);
       homeRecyclerView.setNestedScrollingEnabled(false);

        HomeRecyclerAdapter myAdapter = new HomeRecyclerAdapter (this, s1);
        homeRecyclerView.setAdapter(myAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
