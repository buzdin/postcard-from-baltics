package eu.hack4europe.postcard;

import android.location.Location;
import eu.hack4europe.europeana4j.EuropeanaItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostcardModel {

    private final List<EuropeanaItem> europeanaItems = new ArrayList<EuropeanaItem>();
    private int selectedItemPosition;
    private Location location;

    public void reset() {
        europeanaItems.clear();
        selectedItemPosition = 0;
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

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
