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

import java.util.ArrayList;
import java.util.Collections;
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
public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.MyViewHolder> {

    private final EasyTeamUp app;
    private List<Event> events;
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
    public EventsRecyclerAdapter(EasyTeamUp app, Context ct, MongoCursor<Event> events, RecyclerViewInterface recyclerViewInterface) {
        this.app = app;
        this.events= Collections.synchronizedList(new ArrayList<Event>());
        while (events.hasNext()) {
            this.events.add(events.next());
        }
        context = ct;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public List<Event> getEvents(){
        return events;
    }

    /**
     * utilizes home_row.xml object, make a bunch and fills them with passed in data
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_row, parent, false);
        return new EventsRecyclerAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    /**
     * This is where the actual event data is displayed on the screen!
     */
    @Override
    public void onBindViewHolder(@NonNull EventsRecyclerAdapter.MyViewHolder holder, int position) {
        Event e = events.get(position);
        app.getDatabase().users.findOne(new Document("_id", e.getHost())).getAsync(task -> {
            if (task.isSuccess()) {
                User user = task.get();
                holder.eventName.setText(e.getName());
                holder.host.setText(user.getUsername());
                holder.date.setText(e.getDate());
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    /**
     * View holder object goes into adapter object in HomeActivity.java
     *
     * @author Justin Nakama
     */
    protected static class MyViewHolder extends RecyclerView.ViewHolder {

        private final CardView myCardView;
        private final TextView eventName;
        private final TextView host;
        private final TextView date;

        public MyViewHolder(@NonNull View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            // Define click listener for the ViewHolder's View
            //myTextView = (TextView) view.findViewById(R.id.homeView);
            myCardView = (CardView) view.findViewById(R.id.cardView);
            eventName = (TextView) view.findViewById(R.id.event_name);
            host = (TextView) view.findViewById(R.id.host);
            date = (TextView) view.findViewById(R.id.date);

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

