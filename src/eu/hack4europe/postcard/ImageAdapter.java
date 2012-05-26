package eu.hack4europe.postcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import eu.hack4europe.europeana4j.EuropeanaItem;

import java.lang.ref.WeakReference;
import java.util.List;

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private final List<EuropeanaItem> items;
    private final PostcardBitmapLoader bitmapLoader;

    public ImageAdapter(Context context,
                        List<EuropeanaItem> europeanaItems,
                        PostcardBitmapLoader bitmapLoader) {
        this.context = context;
        this.items = europeanaItems;
        this.bitmapLoader = bitmapLoader;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);

        EuropeanaItem item = items.get(position);
//        BitmapDownloaderTask task = new BitmapDownloaderTask(imageView);
//        task.execute(item);

        Bitmap bitmap = bitmapLoader.load(item);

        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(136, 88));

        return imageView;
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
            return bitmapLoader.load(params[0]);
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