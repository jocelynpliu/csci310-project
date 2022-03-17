package com.example.csci310;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {

    private ArrayList<Event> publicEventList;
    String data1[];
    Context context;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        public MyViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            myTextView = (TextView) view.findViewById(R.id.homeEvent);
        }

        public TextView getTextView() {
            return myTextView;
        }
    }



    public HomeRecyclerAdapter(Context ct, String[] s1) {

        data1  = s1;
        context = ct;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_row, parent, false);

        return new MyViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.MyViewHolder holder, int position) {
        holder.myTextView.setText(data1[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }
}
