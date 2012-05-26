/*
 * EuropeanaQuery.java - europeana4j
 * (C) 2011 Digibis S.L.
 */

package eu.hack4europe.europeana4j;


/**
 * The EuropeanaQuery is an encapsulated query to a EuropeanaConnection
 * object.
 *
 * @author Andr�s Viedma Pel�ez
 */
public class EuropeanaQuery {
    private String wholeSubQuery;

    private String generalTerms;
    private String provider;
    private String dataProvider;
    private String notProvider;
    private String notDataProvider;
    private String creator;
    private String title;
    private String date;
    private String subject;
    private String type;
    private String country;
    private String language;

    public EuropeanaQuery() {
    }

    public EuropeanaQuery(String cadenaLibre) {
        this.wholeSubQuery = cadenaLibre;
    }

    /**
     * @return Devuelve el valor de cadenaLibre.
     */
    public String getWholeSubQuery() {
        return wholeSubQuery;
    }

    /**
     * @param cadenaLibre Nuevo valor para cadenaLibre.
     */
    public void setWholeSubQuery(String cadenaLibre) {
        this.wholeSubQuery = cadenaLibre;
    }

    /**
     * @return Devuelve el valor de terminosTodosCampos.
     */
    public String getGeneralTerms() {
        return generalTerms;
    }

    /**
     * @param terminosTodosCampos Nuevo valor para terminosTodosCampos.
     */
    public void setGeneralTerms(String terminosTodosCampos) {
        this.generalTerms = terminosTodosCampos;
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
     * @return Devuelve el valor de creator.
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator Nuevo valor para creator.
     */
    public void setCreator(String creator) {
        this.creator = creator;
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
     * @return Devuelve el valor de date.
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date Nuevo valor para date.
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return Devuelve el valor de subject.
     */
    public String getSubject() {
        return subject;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    /**
     * @param subject Nuevo valor para subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQueryString() {
        StringBuffer buf = new StringBuffer();

        this.addBusquedaCampo(buf, null, this.wholeSubQuery);

        this.addBusquedaCampo(buf, "text", this.generalTerms);
        this.addBusquedaCampo(buf, "creator", this.creator);
        this.addBusquedaCampo(buf, "title", this.title);
        this.addBusquedaCampo(buf, "date", this.date);
        this.addBusquedaCampo(buf, "subject", this.subject);
        this.addBusquedaCampo(buf, "TYPE", this.type);
        this.addBusquedaCampo(buf, "DATA_PROVIDER", this.dataProvider, false, true);
        this.addBusquedaCampo(buf, "PROVIDER", this.provider, false, true);
        this.addBusquedaCampo(buf, "DATA_PROVIDER", this.notDataProvider, true, true);
        this.addBusquedaCampo(buf, "PROVIDER", this.notProvider, true, true);
        this.addBusquedaCampo(buf, "COUNTRY", this.country, false, true);
        this.addBusquedaCampo(buf, "LANGUAGE", this.language, false, true);

        return buf.toString();
    }

    private void addBusquedaCampo(StringBuffer buf, String campo, String valor) {
        this.addBusquedaCampo(buf, campo, valor, false, false);
    }

    private void addBusquedaCampo(StringBuffer buf, String campo, String valor, boolean not, boolean forzarComillas) {
        if (valor == null) return;

        valor = valor.trim();
        if (valor.length() == 0) return;

        if (not) {
            buf.append(" NOT ");
        } else if (buf.length() > 0) {
            buf.append(" AND ");
        }

        if (campo != null) {
            buf.append(campo);
            buf.append(": ");
        }

        buf.append("(");

        if (forzarComillas && !valor.startsWith("\"")) {
            buf.append("\"");
            buf.append(valor);
            buf.append("\"");
        } else {
            buf.append(valor);
        }
        buf.append(")");
    }


}
