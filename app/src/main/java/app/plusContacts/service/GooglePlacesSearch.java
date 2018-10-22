package app.plusContacts.service;


import android.content.Context;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GooglePlacesSearch {
    // todo colocar essa URL da api em arquivo de configuração;
    private static final String URL_PATTERN = "https://maps.googleapis.com/maps/api/place/textsearch/json?query={QUERY}&location={LATITUDE},{LONGITUDE}&radius={RADIUS}&key={KEY}";
    private String query;
    private String latitude;
    private String longitude;
    private String radius;
    private String key;
    private String urlFilled;

    public GooglePlacesSearch setQuery(String value) {
        try {
            query = URLEncoder.encode(value, "utf-8");
        } catch (UnsupportedEncodingException e) {}
        return this;
    }

    public GooglePlacesSearch setLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        return this;
    }

    public GooglePlacesSearch setRadius(String value) {
        radius = value;
        return this;
    }

    public GooglePlacesSearch setKey(String value) {
        key = value;
        return this;
    }

    public String prepareUrl() {
        urlFilled = URL_PATTERN;
        urlFilled = urlFilled.replace("{QUERY}", query);
        urlFilled = urlFilled.replace("{LATITUDE}", latitude);
        urlFilled = urlFilled.replace("{LONGITUDE}", longitude);
        urlFilled = urlFilled.replace("{RADIUS}", radius);
        urlFilled = urlFilled.replace("{KEY}", key);
        return urlFilled;
    }
}
