package eu.hack4europe.postcard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class DescriptionActivity extends Activity {
		private ImageView image;
		private TextView title;
		private TextView description;
		private ImageButton share;
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.description);
	        
	        image = (ImageView) findViewById(R.id.image);
	        title = (TextView) findViewById(R.id.title);
	        description = (TextView) findViewById(R.id.description);
	        share = (ImageButton) findViewById(R.id.share);
	        
	        PostcardBitmapLoader pbl = new PostcardBitmapLoader();
	        pbl.loadAsync(PostcardApplication.getInstance().getModel().getSelectedItem(), image);
	        
	        title.setText(PostcardApplication.getInstance().getModel().getSelectedItem().getTitle());
	        description.setText(PostcardApplication.getInstance().getModel().getSelectedItem().getDescription().toString());
	    }
}
