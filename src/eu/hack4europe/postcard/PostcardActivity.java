package eu.hack4europe.postcard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import eu.hack4europe.europeana4j.EuropeanaConnection;
import eu.hack4europe.europeana4j.EuropeanaItem;
import eu.hack4europe.europeana4j.EuropeanaQuery;
import eu.hack4europe.europeana4j.EuropeanaResults;
import eu.hack4europe.geo.GeoParseJson;

import java.util.ArrayList;
import java.util.List;

public class PostcardActivity extends Activity
        implements View.OnClickListener, OnItemSelectedListener {


    // This is secret, do not share
    private static final String API_KEY = "HTMQFSCKKB";

    public static final String MIME_TYPE = "image/jpeg";
    public static final int RESULT_SIZE = 20;

    private Gallery gallery;
    private EditText editText;
    private Button shareButton;
    private Button findButton;
    private ImageView selectedPostcard;

    private final PostcardModel model = new PostcardModel();
    private final PostcardBitmapLoader loader = new PostcardBitmapLoader();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Getting the views
        selectedPostcard = (ImageView) findViewById(R.id.bigImage);
        gallery = (Gallery) findViewById(R.id.gallery1);
        editText = (EditText) findViewById(R.id.editText1);
        findButton = (Button) findViewById(R.id.button1);
        shareButton = (Button) findViewById(R.id.share);

        // Attaching event listeners
        findButton.setOnClickListener(this);
        shareButton.setOnClickListener(this);
        selectedPostcard.setOnClickListener(this);

        gallery.setOnItemSelectedListener(this);

        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyLocationListener();
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mlocListener);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        List<EuropeanaItem> europeanaItems = model.getEuropeanaItems();
        EuropeanaItem item = europeanaItems.get(position);
        Bitmap bitmap = loader.load(item);
        selectedPostcard.setImageBitmap(bitmap);
        model.setSelectedItemPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onClick(View view) {
        if (view == shareButton) {
            shareIt();
        } else if (view == findButton) {
            findIt();
        } else if (view == selectedPostcard) {
            clickIt();
        }
    }

    private void clickIt() {
        EuropeanaItem selectedItem = model.getSelectedItem();
        String title = selectedItem.getTitle();
        Log.i("postcard", "clicked on " + title);
    }

    private void findIt() {
        Editable text = editText.getText();
        
        String city = GeoParseJson.getCity(model.getLatitude(), model.getLongitude());
        
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

                ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), loadedItems, loader);
                gallery.setAdapter(imageAdapter);
            }

        } catch (Exception e) {
            Log.e("postcard", "failed", e);
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), 5000).show();
        }
    }

    private void shareIt() {
        EuropeanaItem item = model.getSelectedItem();

        Bitmap bitmap = loader.load(item);
        Context context = getApplicationContext();
        String uri = MediaStore.Images.Media.insertImage(
                context.getContentResolver(),
                bitmap,
                item.getTitle(),
                item.getDescription()
        );

        Log.i("postcard", "sharing image " + uri);

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(MIME_TYPE);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Sharing this postcard");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(uri));

        startActivity(Intent.createChooser(sharingIntent, "Select a Way to Share"));
    }

    public class MyLocationListener implements LocationListener{

	    @Override
	    public void onLocationChanged(Location loc){
		    loc.getLatitude();
		    loc.getLongitude();
		    String Text = "My current location is: " +
		    "Latitude = " + loc.getLatitude() +
		    "Longitude = " + loc.getLongitude();
		    Log.i("card", Text);
		    model.setLatitude(loc.getLatitude());
		    model.setLongitude(loc.getLongitude());
	    }

	    @Override
	    public void onProviderDisabled(String provider){
	    	Toast.makeText( getApplicationContext(), "Gps Disabled", Toast.LENGTH_SHORT ).show();
	    }

	    @Override
	    public void onProviderEnabled(String provider){
	    	Toast.makeText( getApplicationContext(), "Gps Enabled", Toast.LENGTH_SHORT).show();
	    }

	    @Override
	    public void onStatusChanged(String provider, int status, Bundle extras){

	    }

    }/* End of Class MyLocationListener */
}
