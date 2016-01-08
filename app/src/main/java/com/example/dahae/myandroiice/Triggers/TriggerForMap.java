package com.example.dahae.myandroiice.Triggers;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dahae.myandroiice.MainActivity;
import com.example.dahae.myandroiice.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TriggerForMap extends AppCompatActivity {

    private GoogleMap map;
    TextView contentsText;

    Geocoder gc;

    EditText edit01;

    Double latitudeForRegister = 0.0;
    Double longitudeForRegister = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.d(MainActivity.TAG, "hi map");

        edit01 = (EditText) findViewById(R.id.edit01);
        contentsText = (TextView) findViewById(R.id.contentsText);

        // Searching where I want
        Button showGeocod = (Button) findViewById(R.id.show_btn);
        showGeocod.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String searchStr = edit01.getText().toString();
                searchLocation(searchStr);
            }
        });

        Button showProximity = (Button) findViewById(R.id.show_but3);
        showProximity.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                intentReturn();
            }
        });
        startLocationService();
        gc = new Geocoder(this, Locale.KOREAN);
        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

    }

    @Override
    protected void onStart() {
        super.onStart();
        map.setMyLocationEnabled(true);
    }

    public void intentReturn() {
        Intent intent = getIntent();
        intent.setAction("Map");
        intent.putExtra("mTriggerInfo", latitudeForRegister + "+" + longitudeForRegister);

        setResult(RESULT_OK, intent);
        finish();
    }

    private void searchLocation(String searchStr) {
        List<Address> addressList = null;

        try {
            addressList = gc.getFromLocationName(searchStr, 3);

            if (addressList != null) {
                contentsText.append("[" + searchStr + "]");
                for (int i = 0; i < addressList.size(); i++) {
                    Address outAddr = addressList.get(i);
                    int addrCount = outAddr.getMaxAddressLineIndex() + 1;
                    StringBuffer outAddrStr = new StringBuffer();
                    for (int k = 0; k < addrCount; k++) {
                        outAddrStr.append(outAddr.getAddressLine(k));
                    }
                    latitudeForRegister = outAddr.getLatitude();
                    longitudeForRegister = outAddr.getLongitude();
                    contentsText.append("\n" + outAddrStr.toString());
                    Log.d(MainActivity.TAG, latitudeForRegister+ "latitudeForRegister /" +longitudeForRegister+"longitudeForRegister" );
                }
            }
        } catch (IOException ex) {
            Log.i(MainActivity.TAG, "ERROR : " + ex.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startLocationService() {

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location lastLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        GPSListener gpsListener = new GPSListener();

        long minTime = 1000000;
        float minDistance = 0;

        try {

            if (lastLocation != null) {
                Double latitude = lastLocation.getLatitude();
                Double longitude = lastLocation.getLongitude();
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, gpsListener);
                showCurrentLocation(latitude, longitude);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private class GPSListener implements LocationListener {

        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            Log.i(MainActivity.TAG, "Latitude : " + latitude + ", Longitude:" + longitude + " InGPS");
        }

        public void onProviderDisabled(String provider) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

    private void showCurrentLocation(Double latitude, Double longitude) {

        Log.d(MainActivity.TAG, "showCurrentLocation");

        LatLng curPoint = new LatLng(latitude, longitude);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 15));

        // 지도 유형 설정. 지형도인 경우에는 GoogleMap.MAP_TYPE_TERRAIN, 위성 지도인 경우에는 GoogleMap.MAP_TYPE_SATELLITE
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(latitude+0.001, longitude+0.001));
        marker.draggable(true);
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_cast_light));

        map.addMarker(marker);
    }
}
//    /**
//     * register the proximity intent receiver
//     */
//
////
////    public class ActionForLocation extends BroadcastReceiver {
////        public ActionForLocation() {
////        }
////
////        private String mExpectedAction;
////        private Intent mLastReceivedIntent;
////
////        public ActionForLocation(String expectedAction) {
////            mExpectedAction = expectedAction;
////            mLastReceivedIntent = null;
////        }
////
////        public IntentFilter getFilter() {
////            IntentFilter filter = new IntentFilter(mExpectedAction);
////            return filter;
////        }
////
////        @Override
////        public void onReceive(Context context, Intent intent) {
////
////            Log.d(TAG,"Location onReceive");
////
////            if (intent != null) {
////                mLastReceivedIntent = intent;
////
////                int id = intent.getIntExtra("id", 0);
////                double latitude = intent.getDoubleExtra("latitude", 0.0D);
////                double longitude = intent.getDoubleExtra("longitude", 0.0D);
////
////                Log.d(TAG, "Location : " + id + ", " + latitude + ", " + longitude);
////            }
////            // TODO: This method is called when the BroadcastReceiver is receiving
////            // an Intent broadcast.
////            throw new UnsupportedOperationException("Not yet implemented");
////        }
////        public Intent getLastReceivedIntent() {
////            return mLastReceivedIntent;
////        }
////
////        public void clearReceivedIntents() {
////            mLastReceivedIntent = null;
////        }
////    }
////