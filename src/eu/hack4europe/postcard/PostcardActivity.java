package eu.hack4europe.postcard;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import eu.hack4europe.europeana4j.EuropeanaConnection;
import eu.hack4europe.europeana4j.EuropeanaItem;
import eu.hack4europe.europeana4j.EuropeanaQuery;
import eu.hack4europe.europeana4j.EuropeanaResults;
import eu.hack4europe.postcard.geo.GeoParseJson;

import java.util.ArrayList;
import java.util.List;


public class PostcardActivity extends Activity
        implements View.OnClickListener, OnItemSelectedListener, LocationListener {

    private static final int ENTER_CITY_NAME = 0;
    private static final int ABOUT = 1;
    private static final int REFRESH = 2;

    // This is secret, do not share
    private static final String API_KEY = "HTMQFSCKKB";

    public static final int RESULT_SIZE = 50;

    private TextView locationText;
    private Gallery gallery;
    private ImageView selectedPostcard;

    private final PostcardModel model = PostcardApplication.getInstance().getModel();
    private final PostcardBitmapLoader loader = new PostcardBitmapLoader();

    private String bestProvider;
    private LocationManager locationManager;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, ENTER_CITY_NAME, 0, "Enter Name");
        menu.add(0, REFRESH, 2, "Refresh");
        menu.add(0, ABOUT, 3, "About");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case ENTER_CITY_NAME:
                return true;
            case REFRESH:
                findIt();
                return true;
            case ABOUT:
                return true;
        }

        return false;
    }
    protected Dialog mSplashDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        MyStateSaver data = (MyStateSaver) getLastNonConfigurationInstance();
        if (data != null) {
            // Show splash screen if still loading
            if (data.showSplashScreen) {
                showSplashScreen();
            }
            setContentView(R.layout.main);        
     
            // Rebuild your UI with your saved state here
        } else {
            showSplashScreen();
            setContentView(R.layout.main);
            // Do your heavy loading here on a background thread
        }
        // Getting the views
        selectedPostcard = (ImageView) findViewById(R.id.bigImage);
        gallery = (Gallery) findViewById(R.id.gallery1);
        locationText = (TextView) findViewById(R.id.location);

        // Attaching event listeners
        selectedPostcard.setOnClickListener(this);
        gallery.setOnItemSelectedListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        bestProvider = locationManager.getBestProvider(criteria, true);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        model.setLocation(location);
        findIt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(bestProvider, 5 * 60 * 1000, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      findIt();
    }

    
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<EuropeanaItem> europeanaItems = model.getEuropeanaItems();
        final EuropeanaItem item = europeanaItems.get(position);

        model.setSelectedItemPosition(position);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                EuropeanaItem selectedItem = model.getSelectedItem();
                if (selectedItem == item) { // making smooth scrolling
                    loader.loadAsync(item, selectedPostcard);
                }
            }
        }, 400);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onClick(View view) {
        if (view == selectedPostcard) {
            clickIt();
        }
    }

    private void clickIt() {
        EuropeanaItem selectedItem = model.getSelectedItem();
        String title = selectedItem.getTitle();
        Log.i("postcard", "clicked on " + title);
        Intent navigate = new Intent(PostcardActivity.this, DescriptionActivity.class);
        startActivity(navigate);
    }

    private void findIt() {
        Location location = model.getLocation();

        // Hardcoded for emulator
        String city;
        if (location == null) {
            Location defaultLocation = new Location("");
            defaultLocation.setLatitude(56.9471);
            defaultLocation.setLongitude(23.6192);
            city = GeoParseJson.getCity(defaultLocation);
        } else {
            city = GeoParseJson.getCity(location);
        }

        if (city == null) {
            Toast.makeText(
                    getApplicationContext(),
                    "Location name is not available, check your internet connection",
                    1000).show();
            return;
        }

        if (city.equals(model.getLoadedCity())) {
            Log.i("postcard", "no need to refresh already in " + city);
            return;
        }

        model.reset();

        try {
            EuropeanaConnection europeana = new EuropeanaConnection(API_KEY);

            EuropeanaQuery query = new EuropeanaQuery();
            query.setType("IMAGE");
            query.setSubject("postcard");
            query.setLocation(city);

            EuropeanaResults res = europeana.search(query, RESULT_SIZE);

            if (res.getItemCount() > 0) {
                List<EuropeanaItem> items = res.getAllItems();
                List<EuropeanaItem> loadedItems = new ArrayList<EuropeanaItem>();
                for (EuropeanaItem item : items) {
                    loadedItems.add(item);
                }
                model.setEuropeanaItems(loadedItems);

                Drawable spinner = getResources().getDrawable(R.drawable.spinner);
                ImageAdapter imageAdapter = new ImageAdapter(
                        getApplicationContext(),
                        loadedItems,
                        loader,
                        spinner);
                gallery.setAdapter(imageAdapter);
            }

            model.setLoadedCity(city);
            locationText.setText(getString(R.string.location) + " " + city);
        } catch (Exception e) {
            Log.e("postcard", "failed", e);
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), 5000).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        model.setLocation(location);
        findIt();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
    
    public Object onRetainNonConfigurationInstance() {
        MyStateSaver data = new MyStateSaver();
        // Save your important data here
     
        if (mSplashDialog != null) {
            data.showSplashScreen = true;
            removeSplashScreen();
        }
        return data;
    }
     
    protected void removeSplashScreen() {
        if (mSplashDialog != null) {
            mSplashDialog.dismiss();
            mSplashDialog = null;
        }
    }
     
    protected void showSplashScreen() {
        mSplashDialog = new Dialog(this, R.style.SplashScreen);
        mSplashDialog.setContentView(R.layout.splashscreen);
        mSplashDialog.setCancelable(false);
        mSplashDialog.show();
     
        // Set Runnable to remove splash screen just in case
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
          @Override
          public void run() {
            removeSplashScreen();
          }
        }, 13000);
    }
 
    private class MyStateSaver {
        public boolean showSplashScreen = true;
        // Your other important fields here
    }
}
