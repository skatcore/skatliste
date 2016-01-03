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

public class X73RamschMultiplikator extends AppCompatPreferenceActivity {

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
        addPreferencesFromResource(R.xml.pref_73_ramsch_multiplikator);

        if (SkatInfoSingleton.getInstance().ramschAusgang == SkatInfoSingleton.RAMSCH_DURCHMARSCH) {
            // Activity unpassend fuer Durschmarsch, zeige vorherige an.
            Intent intent = new Intent(getApplicationContext(), X72RamschVerliererGewinner.class);
            startActivity(intent);
        }

        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                String s = String.valueOf(preference.getKey());
                final int multiplikator = Integer.valueOf(s);
                SkatInfoSingleton.getInstance().ramschMultiplikator = multiplikator;

                Intent intent = new Intent(getApplicationContext(), X74RamschAuswertung.class);
                startActivity(intent);
                return false;
            }
        };


        String[] names = {"1", "2", "3", "4"};
        Preference preference;
        for (int i = 0; i < names.length; i++) {
            preference = findPreference(names[i]);
            preference.setOnPreferenceClickListener(listener);
        }
    }
}
