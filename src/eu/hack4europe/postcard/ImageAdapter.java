package eu.hack4europe.postcard;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import eu.hack4europe.europeana4j.EuropeanaItem;

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
        Bitmap bitmap = bitmapLoader.load(item);

        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(new Gallery.LayoutParams(136, 88));

        return imageView;
    }

}