package eu.hack4europe.postcard;

import java.util.ArrayList;

import android.app.Application;

public class PostcardApplication extends Application {
	private static PostcardApplication singleton;
	public static int money;
    private final PostcardModel model = new PostcardModel();

	public static PostcardApplication getInstance() {
		return singleton;
	}

	
	@Override
	public void onCreate(){
		super.onCreate();
		singleton = this;
	}
}
