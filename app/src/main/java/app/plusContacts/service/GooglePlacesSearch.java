package app.plusContacts.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import app.plusContacts.domain.PlaceBasic;
import app.plusContacts.view.MainActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;

public class GooglePlacesSearch {
    // todo colocar essa URL da api em arquivo de configuração;
    private static final String URL_PATTERN = "https://maps.googleapis.com/maps/api/place/textsearch/json?query={QUERY}&location={LATITUDE},{LONGITUDE}&radius={RADIUS}&key={KEY}";
    private String query;
    private String latitude;
    private String longitude;
    private String radius;
    private String key;
    private String urlFilled;
    private JSONObject response;
    private Context context;
    private ProgressDialog progress;

    public GooglePlacesSearch(Context context) {
        this.context = context;
    }

    public GooglePlacesSearch setQuery(String value) throws UnsupportedEncodingException {
        query = URLEncoder.encode(value, "utf-8");
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

    protected GooglePlacesSearch prepareRequest() {
        urlFilled = URL_PATTERN;
        urlFilled = urlFilled.replace("{QUERY}", query);
        urlFilled = urlFilled.replace("{LATITUDE}", latitude);
        urlFilled = urlFilled.replace("{LONGITUDE}", longitude);
        urlFilled = urlFilled.replace("{RADIUS}", radius);
        urlFilled = urlFilled.replace("{KEY}", key);
        return this;
    }

    public void performRequest() {
        startLoading();
        prepareRequest();

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlFilled, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LinkedList<PlaceBasic> items = new LinkedList<>();
                        try {
                            JSONArray results = response.getJSONArray("results");

                            for (int i=0; i < results.length(); i++) {
                                String name = ((JSONObject)results.get(i)).getString("name");
                                String address = ((JSONObject)results.get(i)).getString("formatted_address");
                                String placeID = ((JSONObject)results.get(i)).getString("place_id");
                                items.add(new PlaceBasic(placeID, name, address));
                            }
                        } catch (JSONException e) {
                            Log.e("GooglePlacesSearch", e.getMessage());
                        }

                        ((MainActivity)context).getAdapter().setItems(items); //TODO RESOLVER ESSE ACOPLAMENTO (uma sugestão seria colocar dentro da activity)

                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Log.e("GooglePlacesSearch", error.getMessage());
                    }
                });

        queue.add(jsonObjectRequest);
    }

    protected void startLoading() {
        progress = ProgressDialog.show(
                context,
                "Aguarde...",
                "Buscando locais...",
                true,
                true
        );
    }
}
