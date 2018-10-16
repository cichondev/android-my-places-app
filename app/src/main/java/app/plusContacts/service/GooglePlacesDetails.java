package app.plusContacts.service;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

public class GooglePlacesDetails {

    private static final String URL_PATTERN = "https://maps.googleapis.com/maps/api/place/details/json?placeid={PLACE_ID}&fields={FIELDS}&key={KEY}";
    private String fields = "name,rating,formatted_phone_number,alt_id,formatted_address,geometry,icon,id,name,permanently_closed,photo,place_id,plus_code,scope,type,url";
    private String placeID;
    private String key;
    private String urlFilled;

    public GooglePlacesDetails() {}

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

    public void performRequest() {
        urlFilled = URL_PATTERN.replace("{FIELDS}", fields);
        urlFilled = urlFilled.replace("{PLACE_ID}", placeID);
        urlFilled = urlFilled.replace("{KEY}", key);
        // todo send http request...


        // Criar uma classe ASyncTask...

        // Colocar a lógica pra efetuar a request

        // ler o resultado JSON

//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                (Request.Method.GET, urlFilled, null, new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        //mTextView.setText("Response: " + response.toString());
//                        // todo pegar as keys necessárias aqui...
//                    }
//                }, new Response.ErrorListener() {
//
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // TODO: Handle error
//
//                    }
//                });
//
//        // Access the RequestQueue through your singleton class.
//        queue.add(jsonObjectRequest);

    }
}
