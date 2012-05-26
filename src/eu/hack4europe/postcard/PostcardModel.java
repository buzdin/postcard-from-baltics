package eu.hack4europe.postcard;

import eu.hack4europe.europeana4j.EuropeanaItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostcardModel {

    private final List<EuropeanaItem> europeanaItems = new ArrayList<EuropeanaItem>();
    private int selectedItemPosition;
    private double latitude;
    private double longitude;

	public void reset() {
        europeanaItems.clear();
        selectedItemPosition = 0;
    }
	
    public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

    public EuropeanaItem getSelectedItem() {
        return europeanaItems.get(selectedItemPosition);
    }

    public List<EuropeanaItem> getEuropeanaItems() {
        return Collections.unmodifiableList(europeanaItems);
    }

    public void setEuropeanaItems(List<EuropeanaItem> europeanaItems) {
        this.europeanaItems.clear();
        this.europeanaItems.addAll(europeanaItems);
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }
}
