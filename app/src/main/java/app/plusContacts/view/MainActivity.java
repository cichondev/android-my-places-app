package app.plusContacts.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import app.plusContacts.R;
import app.plusContacts.domain.PlaceBasic;
import app.plusContacts.service.GooglePlacesSearch;
import app.plusContacts.support.Config;
import app.plusContacts.support.LocationGps;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnKeyListener {

    private RecyclerView mRecyclerView;
    private AdapterListResults mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private EditText etSearch;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_results);
        etSearch = findViewById(R.id.et_search);

        setSupportActionBar(toolbar);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Clicked on fab button!");
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new AdapterListResults();
        mRecyclerView.setAdapter(mAdapter);

        etSearch.setOnKeyListener(this);
        LocationGps.getInstance().identify(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * onKeyPress for searchValue input;
     */
    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        EditText etSearch = (EditText) view;
        String searchValue = etSearch.getText().toString();

        if (keyEvent.getKeyCode() != KeyEvent.KEYCODE_ENTER) {
            return false;
        }
        getPlaces(searchValue);
        return true;
    }

    /**
     * Perform request of Places for searchValue typed by user.
     *
     * @param searchValue
     */
    public void getPlaces(String searchValue) {
        startLoading();

        String urlApi = new GooglePlacesSearch()
                .setKey(Config.get("maps_key", this))
                .setLocation(LocationGps.getInstance().getLatitude(), LocationGps.getInstance().getLongitude())
                .setQuery(searchValue)
                .setRadius(Config.get("raio", this))
                .prepareUrl();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, urlApi, null, new Response.Listener<JSONObject>() {
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

                        mAdapter.setItems(items);
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
                this,
                "Aguarde...",
                "Buscando locais...",
                true,
                true
        );
    }
}
