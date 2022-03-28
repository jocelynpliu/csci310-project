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

import java.util.ArrayList;

import csci310.team53.easyteamup.R;


/**
 * Search menu launched from CreateEventActivity, allows user to search for users to invite to event.
 *
 * @author Jocelyn Liu
 */
public class MenuActivity extends AppCompatActivity {

    // TODO: connect this to the backend
    ListView listView;

    ArrayList<String> users;
    ArrayList<String> invitedUsers;

    ArrayAdapter<String> arrayAdapter;

    Button inviteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        users = new ArrayList<String>();
        users.add("Jocelyn");
        users.add("Justin");
        users.add("Lauren");
        users.add("Luke");
        users.add("Thomas");
        users.add("Noa");
        users.add("Viv");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        listView = findViewById(R.id.userListView);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, users);
        listView.setAdapter(arrayAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setItemsCanFocus(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setSelected(true);
            }
        });

        inviteButton = (Button) findViewById(R.id.menuInviteButton);
        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menuInvite();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("users", invitedUsers);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
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
