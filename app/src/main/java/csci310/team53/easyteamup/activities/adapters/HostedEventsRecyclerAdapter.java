package csci310.team53.easyteamup.activities.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import csci310.team53.easyteamup.R;

import java.util.ArrayList;

import csci310.team53.easyteamup.data.Event;

public class HostedEventsRecyclerAdapter extends RecyclerView.Adapter<HostedEventsRecyclerAdapter.MyViewHolder> {

    private ArrayList<Event> notificationList;
    String data1[];
    String data2[];
    String data3[];
    Context context;

    private final RecyclerViewInterface recyclerViewInterface;


    // adapter made to only take in one string of array, may need to edit later
    public HostedEventsRecyclerAdapter(Context ct, String[] s1, String[] s2, String[] s3, RecyclerViewInterface recyclerViewInterface) {
        data1 = s1;
        data2 = s2;
        data3 = s3;
        context = ct;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    //utilizes home_row.xml object, make a bunch and fills them with passed in data
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_row, parent, false);

        return new HostedEventsRecyclerAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    //these two functions just necessary for adaptor to work, small edits for passed
    //in data type later maybe
    @Override
    public void onBindViewHolder(@NonNull HostedEventsRecyclerAdapter.MyViewHolder holder, int position) {
        holder.myTextView1.setText(data1[position]);
        holder.myTextView2.setText(data2[position]);
        holder.myTextView3.setText(data3[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }


    //view holder object goes into adapter object in Home.java
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView myCardView;

        TextView myTextView1;
        TextView myTextView2;
        TextView myTextView3;

        public MyViewHolder(@NonNull View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            // Define click listener for the ViewHolder's View
//            myTextView = (TextView) view.findViewById(R.id.homeView);
            myCardView = (CardView) view.findViewById(R.id.cardView);

            myTextView1 = (TextView) view.findViewById(R.id.homeView);
            myTextView2 = (TextView) view.findViewById(R.id.hostView);
            myTextView3 = (TextView) view.findViewById(R.id.dateView);

            Log.d("----- SETTING ON CLICK ", myCardView+ "   -----------------------------------");

            myCardView.setOnClickListener( new View.OnClickListener(){

                @Override
                public void onClick(View view){

                    if(recyclerViewInterface != null){
                        int position = getAdapterPosition();

                        Log.d(String.valueOf(position), "  DONE!!!");

                        if(position != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(position);
                        }
                    }

                }
            });


        }
    }
}