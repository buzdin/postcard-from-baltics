package eu.hack4europe.postcard;

import android.app.Application;

public class PostcardApplication extends Application {

	private static PostcardApplication singleton;
    private PostcardModel model;

    public static PostcardApplication getInstance() {
        return singleton;
    }

	public PostcardModel getModel() {
		return model;
	}

    @Override
	public void onCreate(){
        super.onCreate();
        singleton = this;

        model  = new PostcardModel();
	}

}
