package app.plusContacts.service;

public class GooglePlacesDetails {

    private static final String URL_PATTERN = "https://maps.googleapis.com/maps/api/place/details/json?placeid={PLACE_ID}&fields={FIELDS}&key={KEY}";
    private String fields = "name,rating,formatted_phone_number,alt_id,formatted_address,geometry,icon,id,name,permanently_closed,photo,place_id,plus_code,scope,type,url";
    private String placeID;
    private String key;
    private String urlFilled;

    public GooglePlacesDetails(String placeID, String key) {
        this.placeID = placeID;
        this.key = key;
    }

    public GooglePlacesDetails setPlaceID(String value) {
        placeID = value;
        return this;
    }

    public GooglePlacesDetails setKey(String value) {
        key = value;
        return this;
    }

    public GooglePlacesDetails setFields(String value) {
        if (!fields.contains(value))
            fields += "," + value;
        return this;
    }

    public String prepareUrl() {
        urlFilled = URL_PATTERN.replace("{FIELDS}", fields);
        urlFilled = urlFilled.replace("{PLACE_ID}", placeID);
        urlFilled = urlFilled.replace("{KEY}", key);
        return urlFilled;
    }
}
