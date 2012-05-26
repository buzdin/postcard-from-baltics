/*
 * EuropeanaConnection.java - europeana4j
 * (C) 2011 Digibis S.L.
 */

package eu.hack4europe.europeana4j;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URLEncoder;
import java.util.logging.Logger;

import com.google.gson.Gson;


/**
 * The EuropeanaConnection class is the main access point to the Europeana API,
 * allowing to make querys and obtain the results in different formats. 
 * 
 * @author Andr�s Viedma Pel�ez
 */
public class EuropeanaConnection
{
    private Logger log = Logger.getLogger (EuropeanaConnection.class.getName());
    
    private String apiKey;
    private String europeanaUri = "http://api.europeana.eu/api/opensearch.json";
    
    private HttpConnector http = new HttpConnector();

    
    public EuropeanaConnection (String apiKey)
    {
        this.apiKey = apiKey;
    }
    
    public EuropeanaConnection()
    {
    }
    
    public EuropeanaResults search (EuropeanaQuery search, long maxResults)
            throws IOException
    {
        int pag = 1;
        EuropeanaResults res = this.searchPage (search, pag);
        while ((res.getItemCount() < maxResults) && (res.getItemCount() < res.getTotalResults())) {
            pag++;
            EuropeanaResults res2 = this.searchPage (search, pag);
            res.acumular (res2);
        }
        return res;
    }

    
    public EuropeanaResults searchPage (EuropeanaQuery search, long startPage)
            throws IOException
    {
        String json = this.searchJsonPage (search, startPage);
        
        // Load results object from JSON
        Gson gson = new Gson();
        EuropeanaResults res = (EuropeanaResults) gson.fromJson (json, EuropeanaResults.class);
        
        return res;
    }


    public String searchJsonPage (EuropeanaQuery search, long startPage)
            throws IOException
    {
        String cadenaBusq = search.getQueryString();
        String url = this.europeanaUri + "?searchTerms=" + URLEncoder.encode (cadenaBusq, "UTF-8");
        url += "&startPage=" + startPage;
        url += "&wskey=" + this.apiKey;
        
        // Execute Europeana API request
        String jSON = this.getJSONResult (url);
        
        // Workaround to Bug in Europeana JSON response: quotes not escaped
        StringBuffer buf = new StringBuffer();
        BufferedReader inJson = new BufferedReader (new StringReader (jSON));
        String jsonLine = inJson.readLine();
        while (jsonLine != null) {
            int iSep = jsonLine.indexOf ("\": \"");
            if (iSep < 0) {
                buf.append (jsonLine);
            } else {
                boolean end = false;
                while ((!jsonLine.endsWith ("\"") && !jsonLine.endsWith ("\","))
                            || jsonLine.endsWith ("\": \"")) {
                    String nextLine = inJson.readLine();
                    end = (nextLine == null);
                    if (!end)  jsonLine += nextLine;
                }
                
                buf.append (jsonLine.substring (0, iSep));
                buf.append ("\": \"");
                int iLastQuote = jsonLine.lastIndexOf ("\"");
                String fieldValue = jsonLine.substring (iSep + 4, iLastQuote);
                fieldValue = fieldValue.replace ("\"", "\\\"");
                buf.append (fieldValue);
                buf.append (jsonLine.substring (iLastQuote));
            }
            
            jsonLine = inJson.readLine();
        }
        jSON = buf.toString();
        
        // Namespaces are removed
        jSON = jSON.replace ("europeana:", "");
        jSON = jSON.replace ("enrichment:", "");
        
        return jSON;
    }

    
    private String getJSONResult (String url)
            throws IOException
    {
        log.fine ("Call to Europeana API: " + url);
        String res = http.getURLContent (url);
               
        return res;
    }

    
    public String getEuropeanaUri ()
    {
        return europeanaUri;
    }

    public void setEuropeanaUri (String europeanaUri)
    {
        this.europeanaUri = europeanaUri;
    }
    
    private String readStream(InputStream is) {
        try {
          ByteArrayOutputStream bo = new ByteArrayOutputStream();
          int i = is.read();
          while(i != -1) {
            bo.write(i);
            i = is.read();
          }
          return bo.toString();
        } catch (IOException e) {
          return "";
        }
    }
}
