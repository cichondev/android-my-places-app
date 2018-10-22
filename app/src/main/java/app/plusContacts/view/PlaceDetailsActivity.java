package app.plusContacts.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DialogTitle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import app.plusContacts.R;
import app.plusContacts.service.GooglePlacesDetails;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Principal;

public class PlaceDetailsActivity extends AppCompatActivity {
    private TextView placeName;
    private TextView placeAddress;
    private TextView placePhone1;
    private ProgressDialog progress;
    private FloatingActionButton fabCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));

        fabCall = findViewById(R.id.fab);
        placeName = findViewById(R.id.details_name);
        placeAddress = findViewById(R.id.details_address);
        placePhone1 = findViewById(R.id.details_phone1);

        registerEvents();
        Bundle data = getIntent().getExtras();
        if (data != null) {
            String placeID = data.getString("place_id");
            startLoading();
            getPlaceDetails(placeID);
        }

    }

    private void registerEvents() {
        fabCall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Confirme o telefone");
                alert.setMessage("Você pode alterar o número se for preciso.");
                final View layoutAlert = getLayoutInflater().inflate(R.layout.alert_review_phone, null);
                final TextView phoneNumberView = layoutAlert.findViewById(R.id.phone_number);
                phoneNumberView.setText(placePhone1.getText());
                alert.setView(layoutAlert);

                alert.setPositiveButton("Ligar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = phoneNumberView.getText().toString().replaceAll("[()\\-\\s]", "");
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                        startActivity(intent);
                    }
                });

                alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText( view.getContext(), "Ligação cancelada!", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        });
    }

    private void getPlaceDetails(String placeID) {
        String key = "AIzaSyA72IYrfMQxUZiOxupbVzwPuhAdJrS6y9A";
        String urlApi = new GooglePlacesDetails(placeID, key).prepareUrl();

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlApi, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject result = response.getJSONObject("result");
                            placeName.setText(result.getString("name"));
                            placeAddress.setText(result.getString("formatted_address"));
                            placePhone1.setText(result.getString("formatted_phone_number"));
                        } catch (JSONException e) {
                            Snackbar.make(placeName, "Erro ao carregar as informações!", Snackbar.LENGTH_LONG)
                                    .show();
                        }
                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //
                    }
                });
        queue.add(jsonObjectRequest);
    }

    protected void startLoading() {
        progress = ProgressDialog.show(
                this,
                "Aguarde...",
                "Carregando detalhes...",
                true,
                true
        );
    }
}
