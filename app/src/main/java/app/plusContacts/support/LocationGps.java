package app.plusContacts.support;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class LocationGps {
    private static LocationGps instance;
    protected LocationListener listener;
    protected double latitude = 0;
    protected double longitude = 0;

    public LocationGps() {
        this.listener = new LocationListener() {
            public void onLocationChanged(Location arg0) {}
            public void onProviderDisabled(String arg0) {}
            public void onProviderEnabled(String arg0) {}
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
        };
    }

    public void identify(Context context, int attempts) {
        for (int i = 0; i < attempts; i++) {
            this.getCoordinates(context);
            if (hasCoordinates()) { break; }
        }
        if (!hasCoordinates()) {
            Toast.makeText( context, "Não foi possível obter sua localização!", Toast.LENGTH_SHORT).show();
        }
    }

    public void enableGPS(Context context) {
        Toast.makeText( context, "Favor ativar o GPS...", Toast.LENGTH_SHORT).show();

        // Aciona janela para habilitar o GPS
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent);
    }

    private void getCoordinates(Context context) {
        // Obtem o serviço de GPS e a última posição conhecida
        LocationManager lm;
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        //TODO adicionar código pra verificar se user deu permissão.
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
        Location lastKnownLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // Se existe uma posição conhecida
        if (lastKnownLoc != null) {
            latitude = lastKnownLoc.getLatitude();
            longitude = lastKnownLoc.getLongitude();
        }
    }

    public boolean isEnableGps(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public boolean hasCoordinates() {
        return (latitude != 0 && longitude != 0);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static LocationGps getInstance() {
        if (instance == null) {
            instance = new LocationGps();
        }
        return instance;
    }
}
