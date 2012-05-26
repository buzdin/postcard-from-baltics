package eu.hack4europe.postcard;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
    private Drawable spinner;

    public ImageAdapter(Context context,
                        List<EuropeanaItem> europeanaItems,
                        PostcardBitmapLoader bitmapLoader,
                        Drawable spinner) {
        this.context = context;
        this.items = europeanaItems;
        this.bitmapLoader = bitmapLoader;
        this.spinner = spinner;
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
        int height = parent.getHeight();
        int width = Math.round((float) (height * 1.5));
        imageView.setLayoutParams(new Gallery.LayoutParams(width, height));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundDrawable(spinner);

        EuropeanaItem item = items.get(position);
        bitmapLoader.loadAsync(item, imageView);


        return imageView;
    }

}