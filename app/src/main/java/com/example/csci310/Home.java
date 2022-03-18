package com.example.csci310;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button homeButton;
    String s1[];

    RecyclerView homeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("----------start", " Home.java");

        //gray out the button to make it look selected and cool
        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setAlpha(.5f);

        //just for testing purposes
        //later, get all event objects titles in a list, sorted order
        s1 = new String[]{"event 1", "event 2", "event3", "event4",
                "event 5", "event 6", "event 7", "event 8", "event 9",
        "event 10", "event 11"};

        //recycler and adapter needed to display a dynamic list on the screen
       homeRecyclerView = findViewById(R.id.homeRecyclerView);
       homeRecyclerView.setNestedScrollingEnabled(false);

        homeRecyclerView.setHasFixedSize(true);

        //specific array of data goes into this adapter
        HomeRecyclerAdapter myAdapter = new HomeRecyclerAdapter (this, s1);


        homeRecyclerView.setAdapter(myAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myAdapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                Log.d(String.valueOf(position), "Clicked!!");
            }
        });

    }
}
