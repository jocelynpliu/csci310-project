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

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.Message;
import csci310.team53.easyteamup.data.User;
import io.realm.mongodb.mongo.iterable.MongoCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MyViewHolder> {

    private final EasyTeamUp app;
    private final List<Message> messages;
    private final Context context;
    private final RecyclerViewInterface recyclerViewInterface;

    /**
     * Constructor that takes a iterator of retrieved events and converts to list.
     *
     * @param app reference to EasyTeamUp main application class
     * @param ct context
     * @param messages iterator of retrieved messages
     * @param recyclerViewInterface recycler view interface
     */
    public MessageRecyclerAdapter(EasyTeamUp app, Context ct, MongoCursor<Message> messages, RecyclerViewInterface recyclerViewInterface) {
        this.app = app;
        this.messages = Collections.synchronizedList(new ArrayList<Message>());
        while (messages.hasNext()) {
            this.messages.add(messages.next());
        }
        context = ct;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public List<Message> getMessages() {
        return messages;
    }

    /**
     * utilizes home_row.xml object, make a bunch and fills them with passed in data
     */
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.home_row, parent, false);
        return new MessageRecyclerAdapter.MyViewHolder(view, recyclerViewInterface);
    }

    /**
     * This is where the actual event data is displayed on the screen!
     */
    @Override
    public void onBindViewHolder(@NonNull MessageRecyclerAdapter.MyViewHolder holder, int position) {
        Message msg = messages.get(position);
        app.getDatabase().users.findOne(new Document("_id", new ObjectId(msg.getSender()))).getAsync(task -> {
            if (task.isSuccess()) {
                User sender = task.get();
                holder.myTextView1.setText(sender.getUsername());
                holder.myTextView2.setText(msg.getContent());
            }
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    /** view holder object goes into adapter object in InboxActivity.java */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final CardView myCardView;
        private final TextView myTextView1;
        private final TextView myTextView2;
        private final TextView myTextView3;

        public MyViewHolder(@NonNull View view, RecyclerViewInterface recyclerViewInterface) {
            super(view);
            // Define click listener for the ViewHolder's View
            // myTextView = (TextView) view.findViewById(R.id.homeView);
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