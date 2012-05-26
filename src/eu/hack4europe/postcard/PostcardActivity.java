package eu.hack4europe.postcard;

import java.util.ArrayList;
import java.util.List;

import eu.hack4europe.europeana4j.*;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class PostcardActivity extends Activity
{
    /** Called when the activity is first created. */
	public Gallery gallery;
	public EditText editText;
	public Button button;
	public String apiKey;
	public String info;
	public ImageView largeImg;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
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
    }
}
