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
    protected double latitude;
    protected double longitude;

    public LocationGps() {
        this.listener = new LocationListener() {
            public void onLocationChanged(Location arg0) {}
            public void onProviderDisabled(String arg0) {}
            public void onProviderEnabled(String arg0) {}
            public void onStatusChanged(String arg0, int arg1, Bundle arg2) {}
        };
    }

    public void identify(Context context) {
        if (!isEnableGps(context)) {
            Toast.makeText( context, "GPS desligado", Toast.LENGTH_SHORT).show();

            // Aciona janela para habilitar o GPS
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);

        } else {
            // Obtem o serviço de GPS e a última posição conhecida
            LocationManager lm;
            lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            //TODO adicionar código pra verificar se user deu permissão.
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, listener);
            Location lastKnownLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            // Se existe uma posição conhecida
            if (lastKnownLoc == null) {
                // Se o GPS não for encontrado
                Toast.makeText( context, "Posição de GPS não encontrada", Toast.LENGTH_SHORT).show();
            } else {
                latitude = lastKnownLoc.getLatitude();
                longitude = lastKnownLoc.getLongitude();
            }
        }
    }

    public Boolean isEnableGps(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
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
