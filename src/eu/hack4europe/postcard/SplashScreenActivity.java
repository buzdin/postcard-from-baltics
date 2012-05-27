package eu.hack4europe.postcard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SplashScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
	
		final int welcomeScreenDisplay = 3000;
		Thread welcomeThread = new Thread() {

			int wait = 0;

			@Override
			public void run() {
				try {
					super.run();
					while (wait < welcomeScreenDisplay) {
						sleep(100);
						wait += 100;
					}
				} catch (Exception e) {
					System.out.println("EXc=" + e);
				} finally {
					if(PostcardApplication.getInstance().haveInternet(SplashScreenActivity.this)){
						startActivity(new Intent(SplashScreenActivity.this,
						PostcardActivity.class));	
						finish();
					}
				}
			}
		};
		welcomeThread.start();

	}
}
