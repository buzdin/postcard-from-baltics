/*
 * ThumbnailsAccessor.java - europeana4j
 * (C) 2011 Digibis S.L.
 */

package eu.hack4europe.europeana4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * A ThumbnailsAccesor is a tool that makes easier the handling of
 * thumbnails of the Europeana items.
 * 
 * @author Andr�s Viedma Pel�ez
 */
public class ThumbnailsAccessor
{
    private final static Logger log = Logger.getLogger (ThumbnailsAccessor.class.getName());
    
    private HttpConnector http = new HttpConnector();
    private EuropeanaConnection europeana = new EuropeanaConnection();
    
    
    public ThumbnailsAccessor ()
    {
    }

    public ThumbnailsAccessor (EuropeanaConnection europeana)
    {
        this.europeana = europeana;
    }

    
    public boolean writeItemThumbnail (EuropeanaItem item, OutputStream out, boolean useTypeThumb)
            throws IOException
    {
        String mime = "image";
        boolean bOk = this.http.silentWriteURLContent (item.getThumbnailURL(), out, mime);
        if (!bOk)  bOk = this.http.silentWriteURLContent (item.getOriginalThumbnailURL(), out, mime);
        if (!bOk && useTypeThumb)
            return this.http.writeURLContent (item.getThumbnailOrTypeURL(), out, mime);
        else
            return bOk;
    }

    
    public String getItemThumbnailURL (EuropeanaItem item, boolean useTypeThumb)
    {
        String mime = "image";
        boolean bOk = this.http.checkURLContent (item.getThumbnailURL(), mime);
        if (bOk)  return item.getThumbnailURL();
        
        bOk = this.http.checkURLContent (item.getOriginalThumbnailURL(), mime);
        if (bOk)  return item.getOriginalThumbnailURL();
        
        return (useTypeThumb? item.getThumbnailOrTypeURL() : null);
    }

    
    public List<EuropeanaItem> copyThumbnails (EuropeanaQuery search, File dir, int maxResults)
            throws IOException
    {
        EuropeanaResults res = europeana.search (search, maxResults);
        
        List<EuropeanaItem> items = res.getAllItems();
        List<EuropeanaItem> itemsOk = new ArrayList<EuropeanaItem> (items.size());
        for (int i=0; i < items.size(); i++) {
            EuropeanaItem item = (EuropeanaItem) items.get(i);
            String id = item.getObjectIdentifier();
            if ((id != null) && (id.length() > 0)) {
                String nombreFich = id + ".jpg";
                File fich = new File (dir, nombreFich);
                FileOutputStream out = new FileOutputStream (fich);
                try {
                    if (i % 10 == 0)  log.info ("Copying thumbnail: " + (i + 1) + " / " + items.size());
                    this.writeItemThumbnail (item, out, false);
                    
                } finally {
                    out.close();
                }
                
                fich = new File (dir, nombreFich);
                if (fich.length () < 100) {
                    fich.delete();
                } else if (fich.exists()) {
                    itemsOk.add (item);
                }
            }
        }
        
        log.info ("Copying finished OK, thumbnails generated: " + itemsOk.size());
        return itemsOk;
    }

    
    public List<EuropeanaItem> fillThumbnailURLs (List<EuropeanaItem> items, boolean onlyValid)
    {
        List<EuropeanaItem> res = items;
        if (onlyValid)  res = new ArrayList<EuropeanaItem> (items.size());
        
        for (int i=0; i<items.size(); i++) {
            EuropeanaItem item = (EuropeanaItem) items.get(i);
            String url = this.getItemThumbnailURL (item, false);
            item.setSavedBestThumbnail (url);
            if (onlyValid && item.hasBestThumbnailSaved())  res.add (item);
        }
        return res;
    }
    
}
