/*
 * EuropeanaItem.java - europeana4j
 * (C) 2011 Digibis S.L.
 */

package eu.hack4europe.europeana4j;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;


/**
 * A EuropeanaItem is the metadata of a work record provided by Europeana.
 *
 * @author Andr�s Viedma Pel�ez
 */
public class EuropeanaItem {
    private String title;
    private String link;
    private String description;
    private String enclosure;

    // europeana:
    private String year;
    private String language;
    private String type;
    private String provider;
    private String dataProvider;
    private String rights;

    // enrichment:
    private String period_begin;
    private String period_end;
    private String place_latitude;
    private String place_longitude;
    private List<String> period_term;
    private List<String> period_label;
    private List<String> place_term;
    private List<String> place_label;
    private List<String> concept_term;
    private List<String> concept_label;

    // own property
    private String savedBestThumbnail;


    public EuropeanaItem() {
    }

    /**
     * @return Devuelve el valor de title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title Nuevo valor para title.
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return Devuelve el valor de enclosure.
     */
    public String getEnclosure() {
        return enclosure;
    }

    /**
     * @param enclosure Nuevo valor para enclosure.
     */
    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    /**
     * @return Devuelve el valor de year.
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year Nuevo valor para year.
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return Devuelve el valor de language.
     */
    public String getLanguage() {
        return language;
    }

    /**
     * @param language Nuevo valor para language.
     */
    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @return Devuelve el valor de type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type Nuevo valor para type.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return Devuelve el valor de provider.
     */
    public String getProvider() {
        return provider;
    }

    /**
     * @param provider Nuevo valor para provider.
     */
    public void setProvider(String provider) {
        this.provider = provider;
    }

    /**
     * @return Devuelve el valor de dataProvider.
     */
    public String getDataProvider() {
        return dataProvider;
    }

    /**
     * @param dataProvider Nuevo valor para dataProvider.
     */
    public void setDataProvider(String dataProvider) {
        this.dataProvider = dataProvider;
    }

    /**
     * @return Devuelve el valor de rights.
     */
    public String getRights() {
        return rights;
    }

    /**
     * @param rights Nuevo valor para rights.
     */
    public void setRights(String rights) {
        this.rights = rights;
    }

    /**
     * @return Devuelve el valor de period_begin.
     */
    public String getPeriod_begin() {
        return period_begin;
    }

    /**
     * @param period_begin Nuevo valor para period_begin.
     */
    public void setPeriod_begin(String period_begin) {
        this.period_begin = period_begin;
    }

    /**
     * @return Devuelve el valor de period_end.
     */
    public String getPeriod_end() {
        return period_end;
    }

    /**
     * @param period_end Nuevo valor para period_end.
     */
    public void setPeriod_end(String period_end) {
        this.period_end = period_end;
    }

    /**
     * @return Devuelve el valor de place_latitude.
     */
    public String getPlace_latitude() {
        return place_latitude;
    }

    /**
     * @param place_latitude Nuevo valor para place_latitude.
     */
    public void setPlace_latitude(String place_latitude) {
        this.place_latitude = place_latitude;
    }

    /**
     * @return Devuelve el valor de place_longitude.
     */
    public String getPlace_longitude() {
        return place_longitude;
    }

    /**
     * @param place_longitude Nuevo valor para place_longitude.
     */
    public void setPlace_longitude(String place_longitude) {
        this.place_longitude = place_longitude;
    }

    /**
     * @return Devuelve el valor de period_term.
     */
    public List<String> getPeriod_term() {
        return period_term;
    }

    /**
     * @param period_term Nuevo valor para period_term.
     */
    public void setPeriod_term(List<String> period_term) {
        this.period_term = period_term;
    }

    /**
     * @return Devuelve el valor de period_label.
     */
    public List<String> getPeriod_label() {
        return period_label;
    }

    /**
     * @param period_label Nuevo valor para period_label.
     */
    public void setPeriod_label(List<String> period_label) {
        this.period_label = period_label;
    }

    /**
     * @return Devuelve el valor de place_term.
     */
    public List<String> getPlace_term() {
        return place_term;
    }

    /**
     * @param place_term Nuevo valor para place_term.
     */
    public void setPlace_term(List<String> place_term) {
        this.place_term = place_term;
    }

    /**
     * @return Devuelve el valor de place_label.
     */
    public List<String> getPlace_label() {
        return place_label;
    }

    /**
     * @param place_label Nuevo valor para place_label.
     */
    public void setPlace_label(List<String> place_label) {
        this.place_label = place_label;
    }

    /**
     * @return Devuelve el valor de concept_term.
     */
    public List<String> getConcept_term() {
        return concept_term;
    }

    /**
     * @param concept_term Nuevo valor para concept_term.
     */
    public void setConcept_term(List<String> concept_term) {
        this.concept_term = concept_term;
    }

    /**
     * @return Devuelve el valor de concept_label.
     */
    public List<String> getConcept_label() {
        return concept_label;
    }

    /**
     * @param concept_label Nuevo valor para concept_label.
     */
    public void setConcept_label(List<String> concept_label) {
        this.concept_label = concept_label;
    }

    public String getCreator() {
        if (this.description == null) return null;

        int iUno = this.description.lastIndexOf(';');
        if (iUno < 0) return null;
        String quitar2Derechos = this.description.substring(0, iUno);
        int iDos = quitar2Derechos.lastIndexOf(';');
        if (iDos < 0) return null;
        quitar2Derechos = quitar2Derechos.substring(0, iDos);

        return quitar2Derechos;
    }

    public String getThumbnailURL() {
        try {
            String encodedThumb = "";
            if (this.enclosure != null)
                encodedThumb = URLEncoder.encode(this.enclosure, "UTF-8");
            return "http://europeanastatic.eu/api/image?uri=" + encodedThumb;

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getThumbnailOrTypeURL() {
        try {
            String encodedThumb = "";
            if (this.enclosure != null)
                encodedThumb = URLEncoder.encode(this.enclosure, "UTF-8");
            return "http://europeanastatic.eu/api/image?uri=" + encodedThumb + "&type=" + this.getType();

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOriginalThumbnailURL() {
        return this.enclosure;
    }

    public String getObjectIdentifier() {
        String part0 = this.getObjectURL();
        if (part0 == null) return null;
        int iPart = this.link.lastIndexOf("/");
        if (iPart < 0)
            return null;
        else
            return this.link.substring(iPart + 1);
    }

    public String getObjectURL() {
        int iPart = this.link.indexOf(".srw");
        if (iPart < 0)
            return null;
        else
            return this.link.substring(0, iPart);
    }

    /**
     * @return Devuelve el valor de savedBestThumbnail.
     */
    public String getSavedBestThumbnail() {
        return savedBestThumbnail;
    }

    /**
     * @param savedBestThumbnail Nuevo valor para savedBestThumbnail.
     */
    public void setSavedBestThumbnail(String savedBestThumbnail) {
        this.savedBestThumbnail = savedBestThumbnail;
    }

    public boolean hasBestThumbnailSaved() {
        return ((this.savedBestThumbnail != null) && (this.savedBestThumbnail.length() > 0));
    }


    public String getBestThumbnail() {
        if (this.hasBestThumbnailSaved())
            return this.savedBestThumbnail;
        else
            return this.getThumbnailOrTypeURL();
    }


    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public void toJSON(Writer out)
            throws IOException {
        Gson gson = new Gson();
        JsonWriter out2 = new JsonWriter(out);
        gson.toJson(this, EuropeanaItem.class, out2);
        out2.flush();
    }

    public static EuropeanaItem loadJSON(String json) {
        Gson gson = new Gson();
        return (EuropeanaItem) gson.fromJson(json, EuropeanaItem.class);
    }

    public static EuropeanaItem loadJSON(Reader json) {
        Gson gson = new Gson();
        return (EuropeanaItem) gson.fromJson(json, EuropeanaItem.class);
    }

    public static List<EuropeanaItem> loadJSONList(String json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaItem>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }

    public static List<EuropeanaItem> loadJSONList(Reader json) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<List<EuropeanaItem>>() {
        }.getType();
        return gson.fromJson(json, collectionType);
    }

}
