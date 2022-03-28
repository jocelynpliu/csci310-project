package csci310.team53.easyteamup.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.User;

/**
 * Search menu launched from CreateEventActivity, allows user to search for users to invite to event.
 *
 * @author Jocelyn Liu
 */
public class MenuActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<ObjectId> userIDs;
    private ArrayList<String> users;
    private ArrayList<String> invitedUsers;

    private ArrayAdapter<String> arrayAdapter;

    private Button inviteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        EasyTeamUp app = (EasyTeamUp) this.getApplication();
        userIDs = new ArrayList<ObjectId>();
        users = new ArrayList<String>();

        app.getUserHandler().getAllUsers().getAsync(task -> {
            while (task.get().hasNext()) {
                User user = task.get().next();
                users.add(user.getUsername());
                userIDs.add(user.getId());
            }

            listView = findViewById(R.id.userListView);
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, users);
            listView.setAdapter(arrayAdapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setItemsCanFocus(false);

            listView.setOnItemClickListener((adapterView, view, i, l) -> view.setSelected(true));

            inviteButton = (Button) findViewById(R.id.menuInviteButton);
            inviteButton.setOnClickListener(view -> {
                menuInvite();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("users", users);
                resultIntent.putExtra("invitedUsers", invitedUsers);
                resultIntent.putExtra("userIDs", userIDs);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search for users to invite");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                arrayAdapter.getFilter().filter(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void menuInvite() {
        // getting all checked users
        invitedUsers = new ArrayList<String>();

        int length = listView.getCount();
        SparseBooleanArray checked = listView.getCheckedItemPositions();

        for (int i = 0; i < length; i++) {
            if (checked.get(i)) {
                String user = users.get(i);
                invitedUsers.add(user);
            }
        }

    }
}
