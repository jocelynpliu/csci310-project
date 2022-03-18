package com.example.csci310;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {

    private ArrayList<Event> publicEventList;
    String data1[];
    Context context;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        public void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    //view holder object goes into adapter object in Home.java
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        public MyViewHolder(View view, final OnItemClickListener listener) {
            super(view);
            // Define click listener for the ViewHolder's View
            myTextView = (TextView) view.findViewById(R.id.homeEvent);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }





// adapter made to only take in one string of array, may need to edit later
    public HomeRecyclerAdapter(Context ct, String[] s1) {
        data1  = s1;
        context = ct;
    }

    //utilizes home_row.xml object, make a bunch and fills them with passed in data
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_row, parent, false);

        MyViewHolder evh = new MyViewHolder(view, mListener);

        return evh;
    }

    //these two functions just necessary for adaptor to work, small edits for passed
    //in data type later maybe
    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.MyViewHolder holder, int position) {
        holder.myTextView.setText(data1[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }
}
