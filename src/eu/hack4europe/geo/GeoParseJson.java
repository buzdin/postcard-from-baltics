package eu.hack4europe.geo;

import android.location.Location;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GeoParseJson {
	private static String makeUrl(double lat, double lng) {
		return "http://maps.googleapis.com/maps/api/geocode/json?latlng="+ 
		lat + "," + 
		lng + "&sensor=false&language=en";
	}
	
	public static String getCity(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        StringBuffer buffer = null;
	    try {
	      URL url = new URL(makeUrl(lat, lng));
	      BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
	      String inputLine;
	      buffer = new StringBuffer();
	      while ((inputLine = in.readLine()) != null) {
	        buffer.append(inputLine);
	      }
	      in.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	    GeoDAO data = new Gson().fromJson(buffer.toString(), GeoDAO.class);
	    String city = "";
	    for(int i = 0; i < data.getResults(0).getAddress_components().length-1; i++ ){
	    	if(data.getResults(0).getAdress_components(i).getTypes().get(0).equals("locality")) {
	    		city = data.getResults(0).getAdress_components(i).getLong_name();
	    	}
	    }
	    
	    return city;
	  }
}
