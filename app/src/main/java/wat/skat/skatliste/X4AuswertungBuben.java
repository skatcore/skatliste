package wat.skat.skatliste;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.text.TextUtils;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

import java.util.List;

public class X4AuswertungBuben extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        setupSimplePreferencesScreen();
    }

    /**
     * Shows the simplified settings UI if the device configuration if the
     * device configuration dictates that a simplified, single-pane UI should be
     * shown.
     */
    private void setupSimplePreferencesScreen() {
        SkatInfoSingleton infoSingleton = SkatInfoSingleton.getInstance();
        boolean isNullspiel = infoSingleton.spiel.equals("Null");
        if (isNullspiel) {
            // Direkt weiter, ohne Buben-Info
            infoSingleton.buben_multiplikator = 0;
            Intent intent = new Intent(getApplicationContext(), X5AuswertungAusgang.class);
            startActivity(intent);
        }

        addPreferencesFromResource(R.xml.pref_4_auswertung_buben);

        final Preference[] preferences = new Preference[4];
        String[] keys = {"buben1", "buben2", "buben3", "buben4"};
        for (int i = 0; i < keys.length; i++) {
            preferences[i] = findPreference(keys[i]);
        }

        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SkatInfoSingleton infoSingleton = SkatInfoSingleton.getInstance();

                switch (String.valueOf(preference.getKey())) {
                    case "buben1":
                        infoSingleton.buben_multiplikator = 2;
                        break;
                    case "buben2":
                        infoSingleton.buben_multiplikator = 3;
                        break;
                    case "buben3":
                        infoSingleton.buben_multiplikator = 4;
                        break;
                    case "buben4":
                        infoSingleton.buben_multiplikator = 5;
                        break;
                }
                Intent intent = new Intent(getApplicationContext(), X5AuswertungAusgang.class);
                startActivity(intent);
                return false;
            }
        };

        Preference preference;
        for (int i = 0; i < keys.length; i++) {
            preference = findPreference(keys[i]);
            preference.setOnPreferenceClickListener(listener);
        }
    }
}
