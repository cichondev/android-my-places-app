package app.plusContacts.support;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.Log;
import app.plusContacts.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final String TAG = "Config";

    @Nullable
    public static String get(String name, Context context) {
        Resources resources = context.getResources();

        try {
            InputStream rawResource = resources.openRawResource(R.raw.config);
            Properties properties = new Properties();
            properties.load(rawResource);
            return properties.getProperty(name);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Unable to find the config file: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "Failed to open config file.");
        }

        return null;
    }
}
