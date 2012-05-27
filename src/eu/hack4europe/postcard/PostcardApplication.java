package eu.hack4europe.postcard;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.widget.Toast;

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
    
    public boolean haveInternet(Context ctx) {
    	try{
        	ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        	//mobile
        	State mobile = conMan.getNetworkInfo(0).getState();
        	//wifi
        	State wifi = conMan.getNetworkInfo(1).getState();

        	if (mobile == NetworkInfo.State.CONNECTED || mobile == NetworkInfo.State.CONNECTING) {
        	    return true;
        	} else if (wifi == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTING) {
        	    return true;
        	}
    	} catch (Exception e){
            Toast.makeText(
                    getApplicationContext(),
                    "Location name is not available, check your internet connection",
                    3000).show();	
    	}
    	
    	return false;
    	
    	/*
		try {
			NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx
					.getSystemService(Context.CONNECTIVITY_SERVICE))
					.getActiveNetworkInfo();

			if (info == null || !info.isConnected()) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}*/
    }

}
