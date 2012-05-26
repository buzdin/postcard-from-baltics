package eu.hack4europe.postcard;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends BaseAdapter {
	int mGalleryItemBackground;
	private Context mContext;

	public ImageAdapter(Context c, List<String> urlList) {
		mContext = c;
		imageURLs = urlList;
	}

	public int getCount() {
		return imageURLs.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(mContext);
		try {
			Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(
					imageURLs.get(position)).getContent());
			i.setImageBitmap(bitmap);
		} catch (IOException ioe) {
			Toast.makeText(mContext, "URL broken", 5000);
		}
		i.setScaleType(ImageView.ScaleType.FIT_XY);
		i.setLayoutParams(new Gallery.LayoutParams(136, 88));
		return i;
	}

	private List<String> imageURLs;
}