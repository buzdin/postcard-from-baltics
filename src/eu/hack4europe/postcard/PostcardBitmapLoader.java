package eu.hack4europe.postcard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import eu.hack4europe.europeana4j.EuropeanaItem;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public final class PostcardBitmapLoader {

    public void loadAsync(EuropeanaItem item, ImageView imageView) {
        BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
        task.execute(item);
    }

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

    class BitmapDownloaderTask extends AsyncTask<EuropeanaItem, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;

        public BitmapDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        // Actual download method, run in the task thread
        protected Bitmap doInBackground(EuropeanaItem... params) {
            // params comes from the execute() call: params[0] is the url.
            return load(params[0]);
        }

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

}
