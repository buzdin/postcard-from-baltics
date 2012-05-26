/*
 * EuropeanaResults.java - europeana4j
 * (C) 2011 Digibis S.L.
 */

package eu.hack4europe.europeana4j;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A EuropeanaResults is an object encapsulating the results of a query
 * to Europeana. It can be the result of multiple calls to the Europeana API
 * with the same query (using pagination of results).
 *
 * @author Andr�s Viedma Pel�ez
 */
public class EuropeanaResults {
    private String link;
    private String description;
    private long totalResults;
    private long startIndex;
    private long itemsPerPage;
    private List<EuropeanaItem> items = new ArrayList<EuropeanaItem>();


    public EuropeanaResults() {
    }

    /**
     * @return Devuelve el valor de link.
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link Nuevo valor para link.
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return Devuelve el valor de description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description Nuevo valor para description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Devuelve el valor de totalResults.
     */
    public long getTotalResults() {
        return totalResults;
    }

    /**
     * @param totalResults Nuevo valor para totalResults.
     */
    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * @return Devuelve el valor de startIndex.
     */
    public long getStartIndex() {
        return startIndex;
    }

    /**
     * @param startIndex Nuevo valor para startIndex.
     */
    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    /**
     * @return Devuelve el valor de itemsPerPage.
     */
    public long getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     * @param itemsPerPage Nuevo valor para itemsPerPage.
     */
    public void setItemsPerPage(long itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public void addItem(EuropeanaItem item) {
        this.items.add(item);
    }

    public void setItems(List<EuropeanaItem> _items) {
        this.items.clear();
        this.items.addAll(_items);
    }

    public List<EuropeanaItem> getAllItems() {
        if (this.items == null)
            return Collections.emptyList();
        else
            return Collections.unmodifiableList(this.items);
    }

    public long getItemCount() {
        return (this.items == null ? 0 : this.items.size());
    }

    public void acumular(EuropeanaResults res2) {
        this.items.addAll(res2.items);
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void toJSON(Writer out)
            throws IOException {
        Gson gson = new Gson();
        JsonWriter out2 = new JsonWriter(out);
        gson.toJson(this, EuropeanaResults.class, out2);
        out2.flush();
    }

    public static EuropeanaResults loadJSON(String json) {
        Gson gson = new Gson();
        return (EuropeanaResults) gson.fromJson(json, EuropeanaResults.class);
    }

    public static EuropeanaResults loadJSON(Reader json) {
        Gson gson = new Gson();
        return (EuropeanaResults) gson.fromJson(json, EuropeanaResults.class);
    }

    public static List<EuropeanaResults> loadJSONList(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaResults>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }

    public static List<EuropeanaResults> loadJSONList(Reader json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaResults>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }

}
