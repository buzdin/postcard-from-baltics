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
	        
	        
	    }
}
