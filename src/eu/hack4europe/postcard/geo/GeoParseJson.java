package eu.hack4europe.postcard.geo;

import android.location.Location;
import android.util.Log;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class GeoParseJson {

    public static String getCity(Location location) {
        double lat = location.getLatitude();
        double lng = location.getLongitude();

        StringBuilder builder = null;
        try {
            URL url = new URL(makeGoogleUrl(lat, lng));
            InputStreamReader inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader in = new BufferedReader(inputStreamReader);
            String inputLine;
            builder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            Log.e("geo", "failed to get place name", e);
        }

        Gson gson = new Gson();
        GeoDAO data = gson.fromJson(builder.toString(), GeoDAO.class);
        String city = "";
        for (int i = 0; i < data.getResults(0).getAddress_components().length - 1; i++) {
            if (data.getResults(0).getAdress_components(i).getTypes().get(0).equals("locality")) {
                city = data.getResults(0).getAdress_components(i).getLong_name();
            }
        }

        return city;
    }

    private static String makeGoogleUrl(double lat, double lng) {
        return "http://maps.googleapis.com/maps/api/geocode/json?latlng=" +
                lat + "," + lng + "&sensor=false&language=en";
    }
}
