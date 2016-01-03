package wat.skat.skatliste;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class X1Spiel extends AppCompatPreferenceActivity {
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
        addPreferencesFromResource(R.xml.pref_1_spiel);

        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                final String spiel = String.valueOf(preference.getTitle());

                SkatInfoSingleton.getInstance().spiel = spiel;
                Intent intent = new Intent(getApplicationContext(), X2Solist.class);
                startActivity(intent);

                return false;
            }
        };

        String[] names = {"Grand", "Null", "Kreuz", "Pik", "Herz", "Karo"};
        Preference preference;
        for (int i = 0; i < names.length; i++) {
            preference = findPreference(names[i]);
            preference.setOnPreferenceClickListener(listener);
        }

        preference = findPreference("Ramsch");
        preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SkatInfoSingleton.getInstance().spiel = "Ramsch";
                Intent intent = new Intent(getApplicationContext(), X71RamschAusgang.class);
                startActivity(intent);
                return false;
            }
        });
    }
}
