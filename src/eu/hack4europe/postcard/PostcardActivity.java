package eu.hack4europe.postcard;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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

    private final List<EuropeanaItem> europeanaItems = new ArrayList<EuropeanaItem>(RESULT_SIZE);
    private int selectedItemPosition;

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
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        try {
            EuropeanaItem item = europeanaItems.get(position);
            URL url = new URL(item.getEnclosure());
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            selectedPostcard.setImageBitmap(bitmap);
            selectedItemPosition = position;
        } catch (IOException ioe) {
            Log.e("postcard", "URL broken", ioe);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    @Override
    public void onClick(View view) {
        if (view == shareButton) {
            shareThis();
        } else if (view == findButton) {
            findIt();
        } else if (view == selectedPostcard) {
            clickIt();
        }
    }

    private void clickIt() {
        EuropeanaItem selectedItem = europeanaItems.get(selectedItemPosition);
        String title = selectedItem.getTitle();
        Log.i("postcard", "clicked on " + title);
    }

    private void findIt() {
        Editable text = editText.getText();
        String info = text.toString();

        europeanaItems.clear();

        try {
            EuropeanaConnection europeana = new EuropeanaConnection(API_KEY);

            EuropeanaQuery query = new EuropeanaQuery();
            query.setType("IMAGE");
            query.setSubject("postcard");
            query.setLocation(info);

            EuropeanaResults res = europeana.search(query, RESULT_SIZE);

            if (res.getItemCount() > 0) {
                List<EuropeanaItem> items = res.getAllItems();
                List<String> thumbnailURLs = new ArrayList<String>();
                for (EuropeanaItem item : items) {
                    thumbnailURLs.add(item.getBestThumbnail());
                    europeanaItems.add(item);
                }

                ImageAdapter imageAdapter = new ImageAdapter(getApplicationContext(), thumbnailURLs);
                gallery.setAdapter(imageAdapter);
            }

        } catch (Exception e) {
            Log.e("postcard", "failed", e);
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), 5000);
        }
    }

    private void shareThis() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(MIME_TYPE);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Sharing this text");

        startActivity(Intent.createChooser(sharingIntent, "Select a Way to Share"));
    }

}
