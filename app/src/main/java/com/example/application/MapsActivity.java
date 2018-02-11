package com.example.application;

import android.app.Instrumentation;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GPSTracker gpsTracker;
    private Location mLocation;
    double latitude, longitude;
    boolean addedPin = false;
    private Button Button;
    private Marker currentMarker;
    private EditText newLocationName;
    private RadioButton tampons;
    private RadioButton pads;

    DatabaseHelperPoints myDb;
    DatabaseHelperStars myDbStars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        gpsTracker = new GPSTracker(getApplicationContext());
        mLocation=gpsTracker.getLocation();

        latitude=mLocation.getLatitude();
        longitude=mLocation.getLongitude();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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
        myDb = new DatabaseHelperPoints(this);
        myDbStars = new DatabaseHelperStars(this);
        newLocationName = findViewById(R.id.etLocationName);
        tampons=(RadioButton)findViewById(R.id.btnTampon);
        pads=(RadioButton)findViewById(R.id.btnPads);


        //Get all points from sql and add as points onto map
        Cursor res =myDb.getAllPoints();
        if(res.getCount()!=0){
            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                double lon=res.getDouble(1);
                double lat=res.getDouble(2);
                String name=res.getString(3);
                addMarker(new LatLng(lat,lon), name);
            }
        }

        //Button changes to AddActivity once pressed on
        Button = (Button) findViewById(R.id.btnAdd);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentMarker!=null) {
                    if (newLocationName.getText().toString().isEmpty()) {
                        newLocationName.setHint("Please enter a location!");
                    } else {
                        if(tampons.isChecked()||pads.isChecked()){
                            //get info from buttons and appends to newLocationName
                            String locName=newLocationName.getText().toString();
                            locName=locName+": "+whichProducts();
                            addPoint(currentMarker.getPosition().latitude, currentMarker.getPosition().longitude, locName);
                            currentMarker.setTitle(locName);
                            newLocationName.setText("");
                            currentMarker = null;
                            addedPin = false;
                            newLocationName.setHint("Enter Location Name");
                            Bundle bundle=getIntent().getExtras();
                            String stuff = bundle.getString("name");
                            myDbStars.insertStar(stuff);

                            //make radio buttons clear and everything invisible
                            tampons.setChecked(false);
                            pads.setChecked(false);
//                            newLocationName.setVisibility(View.INVISIBLE);
//                            tampons.setVisibility(View.INVISIBLE);
//                            pads.setVisibility(View.INVISIBLE);

                        }else{
                            newLocationName.setHint("Please choose a product!");
                        }

                    }
                }
            }
        });

        //Button changes to AddActivity once pressed on
        Button = (Button) findViewById(R.id.btnUser);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=getIntent().getExtras();
                String stuff = bundle.getString("name");
                bundle.putString("name",stuff);
                Intent intent = new Intent(MapsActivity.this, UserActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });

        //Map listener
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(addedPin){
                    currentMarker.remove();
                    MarkerOptions opts = new MarkerOptions();
                    currentMarker = mMap.addMarker(opts.position(latLng).title("NEW").draggable(true));
                }else {
                    MarkerOptions opts = new MarkerOptions();
                    currentMarker = mMap.addMarker(opts.position(latLng).title("NEW").draggable(true));
                    addedPin=true;
                }

                //Make elements visible
                newLocationName.setVisibility(View.VISIBLE);
                tampons.setVisibility(View.VISIBLE);
                pads.setVisibility(View.VISIBLE);

            }
        });


        // Add a marker in current location and move the camera
        addMarker(new LatLng(latitude,longitude), "You are here!");
        mMap.moveCamera(CameraUpdateFactory.newLatLng( new LatLng(latitude,longitude)));

        //Makes it zoom in!
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    public void addMarker(LatLng latlng, String name){
        MarkerOptions opts = new MarkerOptions();
        Marker current = mMap.addMarker(opts.position(latlng).title(name).draggable(true));
        //put into sql

    }
    public void addPoint(double lon, double lat, String name){
        myDb.insertPoint(lon,lat,name);
    }
    public String whichProducts(){
        if(tampons.isChecked()){
            if(pads.isChecked()){
                return "tampons and pads";
            }else{
                return "tampons";
            }
        }else{
            return "pads";
        }
    }

}
