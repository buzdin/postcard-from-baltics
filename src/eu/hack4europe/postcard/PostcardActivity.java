package eu.hack4europe.postcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;
import eu.hack4europe.europeana4j.EuropeanaConnection;
import eu.hack4europe.europeana4j.EuropeanaItem;
import eu.hack4europe.europeana4j.EuropeanaQuery;
import eu.hack4europe.europeana4j.EuropeanaResults;

import java.util.ArrayList;
import java.util.List;

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
