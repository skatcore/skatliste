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

public class X6AuswertungSchneider extends AppCompatPreferenceActivity {

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
            // Direkt weiter, ohne Schneider
            infoSingleton.schneider_gespielt = 0;
            infoSingleton.schwarz_gespielt = 0;
            Intent intent = new Intent(getApplicationContext(), X7Auswertung.class);
            startActivity(intent);
        }

        addPreferencesFromResource(R.xml.pref_6_auswertung_schneider);

        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SkatInfoSingleton infoSingleton = SkatInfoSingleton.getInstance();

                switch (String.valueOf(preference.getKey())) {
                    case "normal":
                        infoSingleton.schneider_gespielt = 0;
                        infoSingleton.schwarz_gespielt = 0;
                        break;
                    case "schneider":
                        infoSingleton.schneider_gespielt = 1;
                        infoSingleton.schwarz_gespielt = 0;
                        break;
                    case "schwarz":
                        infoSingleton.schneider_gespielt = 1;
                        infoSingleton.schwarz_gespielt = 1;
                        break;
                }

                Intent intent = new Intent(getApplicationContext(), X7Auswertung.class);
                startActivity(intent);
                return false;
            }
        };
        findPreference("normal").setOnPreferenceClickListener(listener);
        findPreference("schneider").setOnPreferenceClickListener(listener);
        findPreference("schwarz").setOnPreferenceClickListener(listener);
    }
}
