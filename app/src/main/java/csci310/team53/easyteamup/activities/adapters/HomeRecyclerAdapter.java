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

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.data.User;
import io.realm.mongodb.mongo.iterable.MongoCursor;

/**
 * Dynamic data processing for HomeActivity.java
 *
 * @author Justin Nakama, Thomas Peters
 */
public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.MyViewHolder> {

    private final EasyTeamUp app;
    private final List<Event> events;
    private final Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    /**
     * Constructor that takes a iterator of retrieved events and converts to list.
     *
     * @param app reference to EasyTeamUp main application class
     * @param ct context
     * @param events iterator of retrieved events
     * @param recyclerViewInterface recycler view interface
     */
    public HomeRecyclerAdapter(EasyTeamUp app, Context ct, MongoCursor<Event> events, RecyclerViewInterface recyclerViewInterface) {
        this.app = app;
        this.events = new ArrayList<Event>();
        for (; events.hasNext(); ) {
            this.events.add(events.next());
        }
        context = ct;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     * utilizes home_row.xml object, make a bunch and fills them with passed in data
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_row, parent, false);
        return new HomeRecyclerAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    /**
     * these two functions just necessary for adaptor to work, small edits for passed
     * in data type later maybe.
     */
    @Override
    public void onBindViewHolder(@NonNull HomeRecyclerAdapter.MyViewHolder holder, int position) {
        Event e = events.get(position);
        app.getDatabase().users.findOne(new Document("_id", new ObjectId(e.getHost()))).getAsync(task -> {
            if (task.isSuccess()) {
                User user = task.get();
                holder.myTextView1.setText(e.getName());
                holder.myTextView2.setText(user.getUsername());
                // Time and Date not working yet, so just using a placeholder here!!
                holder.myTextView3.setText("10:30 AM");
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    /**
     * View holder object goes into adapter object in Home.java
     *
     * @author Justin Nakama
     */
    protected static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CardView myCardView;
        private final TextView myTextView1;
        private final TextView myTextView2;
        private final TextView myTextView3;

        public MyViewHolder(@NonNull View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            // Define click listener for the ViewHolder's View
            //myTextView = (TextView) view.findViewById(R.id.homeView);
            myCardView = (CardView) view.findViewById(R.id.cardView);
            myTextView1 = (TextView) view.findViewById(R.id.homeView);
            myTextView2 = (TextView) view.findViewById(R.id.hostView);
            myTextView3 = (TextView) view.findViewById(R.id.dateView);

            Log.d("----- SETTING ON CLICK ", myCardView+ "   -----------------------------------");
            myCardView.setOnClickListener(view1 -> {
                if(recyclerViewInterface != null){
                    int position = getAdapterPosition();
                    Log.d(String.valueOf(position), "  DONE!!!");
                    if(position != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}

