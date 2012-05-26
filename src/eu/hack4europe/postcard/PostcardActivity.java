package eu.hack4europe.postcard;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class PostcardActivity extends Activity implements View.OnClickListener {

    public static final String MIME_TYPE = "image/jpeg";

    private Button shareButton;

    /** Called when the activity is first created. */
	public Gallery gallery;
	public EditText editText;
	public Button button;
	public String apiKey;
	public String info;
	public ImageView largeImg;
	public List<String> urlList;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    	apiKey = "HTMQFSCKKB";
		largeImg = (ImageView) findViewById(R.id.bigImage);
    	gallery = (Gallery) findViewById(R.id.gallery1);
    	editText = (EditText) findViewById(R.id.editText1);
    	button = (Button)findViewById(R.id.button1);
    	urlList = new ArrayList<String>();
    	
    	OnClickListener ocl = new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				info = editText.getText().toString();
		    	try {
					EuropeanaQuery query = new EuropeanaQuery(info);
					query.setType("IMAGE");
					query.setSubject("postcard");
					//query.setCountry("Riga");
					EuropeanaConnection europeana = new EuropeanaConnection(apiKey);
					EuropeanaResults res = europeana.search(query, 12);

					if (res.getItemCount() > 0) {
						List<EuropeanaItem> items = res.getAllItems();
						List<String> itemURLs = new ArrayList<String>();
						for (int i = 0; i < items.size(); i++) {
							EuropeanaItem item = items.get(i);
							itemURLs.add(item.getBestThumbnail());
							urlList.add(item.getEnclosure());
						}
						

				    	ImageAdapter imga = new ImageAdapter(getApplicationContext(), itemURLs);
				    	gallery.setAdapter(imga);
					}
			    	
				} catch (Exception e) {
			    	Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), 5000);
				}
				
			}
		};

		button.setOnClickListener(ocl);

        shareButton = (Button) findViewById(R.id.share);
        shareButton.setOnClickListener(this);
    	gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
    		@Override
    		public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
    			try {
    				Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
    						urlList.get(position)).getContent());
    				largeImg.setImageBitmap(bitmap);
    			} catch (IOException ioe) {
    				Toast.makeText(getApplicationContext(), "URL broken", 5000);
    			}
    	        // Maybe you can try
    	        // i.setImageDrawable(((ImageView) view).getDrawable());
    	    }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
    	});
    }

    @Override
    public void onClick(View view) {
        if (view == shareButton) {
            shareThis();
        }

		


    }

    private void shareThis() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType(MIME_TYPE);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "Sharing this text");

        startActivity(Intent.createChooser(sharingIntent, "Select a Way to Share"));
    }

}
