package eu.hack4europe.postcard;

import android.app.Application;

public class PostcardApplication extends Application {
	private static PostcardApplication singleton;
    private PostcardModel model;

	public PostcardModel getModel() {
		return model;
	}


	public static PostcardApplication getInstance() {
		return singleton;
	}

	
	@Override
	public void onCreate(){
		model  = new PostcardModel();
		super.onCreate();
		singleton = this;
	}
}
