package csci310.team53.easyteamup.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.databinding.ActivityMapsBinding;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private EasyTeamUp app;

    private Button myHostedEventsButton;
    private Button inboxButton;
    private Button myEventsButton;
    private Button myHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Log.d("inside", "mapsactitivy------------------------------------");





        app = (EasyTeamUp) this.getApplication();

        app.getEventHandler().retrievePublicEvents().getAsync(task -> {
            if (task.isSuccess()) {
                List<Event> events = new ArrayList<Event>();
                while(task.get().hasNext()){
                    Event e = task.get().next();

                    events.add(e);
                    Log.d("!!!loc", e.getLocation() ) ;


                }

                Log.d("!!!SIZXE", String.valueOf(events.size() ) ) ;

                for(Event e :events) {
                        Log.d("EVENT!!!!! ", e.getName());

                        LatLng address2 = getLocationFromAddress(this, e.getLocation());

                        if(address2 == null){
                            continue;
                        }

                        mMap.addMarker(new MarkerOptions().position(address2).title(e.getName()).snippet(e.getId().toString()) );
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(address2));
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));

                        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                            @Override
                            public void onInfoWindowClick(Marker marker) {

                                String snip = marker.getSnippet();
                                marker.setSnippet(null);

                                Intent intent1 = new Intent(MapsActivity.this, EventDetailsActivity.class);
                               intent1.putExtra("from", "map");
                               intent1.putExtra("eventID", snip );


                                startActivity(intent1);
                            }
                        });


                }


            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);





        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        //go to Hosted events
        myHostedEventsButton= (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(MapsActivity.this, HomeActivity.class);
            startActivity(intent);
        });



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


//        LatLng address = getLocationFromAddress(this, "3911 Figueroa St, Los Angeles, CA 90037") ;
//        mMap.addMarker(new MarkerOptions().position(address).title("Marker "));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(address));
//        mMap.animateCamera( CameraUpdateFactory.zoomTo( 11.0f ) );
//
//        LatLng address2 = getLocationFromAddress(this, "1 World Way, Los Angeles, CA 90045") ;
//        mMap.addMarker(new MarkerOptions().position(address2).title("Marker 2 "));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(address2));
//        mMap.animateCamera( CameraUpdateFactory.zoomTo( 11.0f ) );
//
//
//
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Intent intent1 = new Intent( MapsActivity.this, EventDetailsActivity.class);
//                String title = marker.getTitle();
//                intent1.putExtra("eventTitle", title);
//                startActivity(intent1);
//            }
//        });
    }

    //credit: https://stackoverflow.com/questions/24352192/android-google-maps-add-marker-by-address
    public LatLng getLocationFromAddress(Context context, String strAddress)
    {

        Geocoder coder= new Geocoder(context);
        Log.d("!!!!!!!!!!!!!!CCC", String.valueOf(context));
        List<Address> address;
        LatLng p1 = null;


        try
        {
            address = coder.getFromLocationName(strAddress, 5);
            if(address==null)
            {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return p1;

    }
}

