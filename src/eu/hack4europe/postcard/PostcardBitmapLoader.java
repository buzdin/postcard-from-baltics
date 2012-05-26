package eu.hack4europe.postcard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import eu.hack4europe.europeana4j.EuropeanaItem;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public final class PostcardBitmapLoader {

    public Bitmap load(EuropeanaItem item) {
        String enclosure = item.getEnclosure();
        InputStream content = null;
        try {
            URL url = new URL(enclosure);
            content = (InputStream) url.getContent();
            return BitmapFactory.decodeStream(content);
        } catch (IOException e) {
            Log.e("http", "Failed to load bitmap: " + enclosure, e);
            throw new RuntimeException(e);
        } finally {
            if (content != null) {
                try {
                    content.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
