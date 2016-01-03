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

public class X72RamschVerliererGewinner extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int ramschAusgang = SkatInfoSingleton.getInstance().ramschAusgang;
        if (ramschAusgang == SkatInfoSingleton.RAMSCH_1_VERLIERER) {
            setTitle("Verlierer auswählen");
        } else {
            setTitle("Gewinner auswählen");
        }
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
        addPreferencesFromResource(R.xml.pref_72_ramsch_verlierer_gewinner);

        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SkatInfoSingleton infoSingleton = SkatInfoSingleton.getInstance();
                String s = String.valueOf(preference.getKey());
                char c = s.charAt(s.length() - 1);
                int solist_Relativ = Integer.valueOf(c);
                int solist_Abs = ((solist_Relativ + infoSingleton.geber) % infoSingleton.spielerzahl);
                if (solist_Abs == 0) {
                    solist_Abs = infoSingleton.spielerzahl;
                }
                infoSingleton.ramschSolist = solist_Abs;

                Intent intent;
                if (SkatInfoSingleton.getInstance().ramschAusgang != SkatInfoSingleton.RAMSCH_DURCHMARSCH) {
                    intent = new Intent(getApplicationContext(), X73RamschMultiplikator.class);
                } else {
                    intent = new Intent(getApplicationContext(),X74RamschAuswertung.class);
                }
                startActivity(intent);

                return false;
            }
        };

        final SkatInfoSingleton infoSingleton = SkatInfoSingleton.getInstance();
        String[] spieler = infoSingleton.getPlayingPlayersInOrder();

        Preference preference;
        String[] keys = {"spieler1", "spieler2", "spieler3"};
        for (int i = 0; i < keys.length; i++) {
            preference = findPreference(keys[i]);
            preference.setTitle(spieler[i]);
            preference.setOnPreferenceClickListener(listener);
        }
    }
}
