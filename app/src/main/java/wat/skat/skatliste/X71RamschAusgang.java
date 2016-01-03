package wat.skat.skatliste;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class X71RamschAusgang extends AppCompatPreferenceActivity {

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
        addPreferencesFromResource(R.xml.pref_71_ramsch_ausgang);

        Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                SkatInfoSingleton infoSingleton = SkatInfoSingleton.getInstance();
                final String key = String.valueOf(preference.getKey());
                switch(key) {
                    case "1verlierer":
                        infoSingleton.ramschAusgang = SkatInfoSingleton.RAMSCH_1_VERLIERER;
                        break;
                    case "2verlierer":
                        infoSingleton.ramschAusgang = SkatInfoSingleton.RAMSCH_2_VERLIERER;
                        break;
                    case "durchmarsch":
                        infoSingleton.ramschAusgang = SkatInfoSingleton.RAMSCH_DURCHMARSCH;
                        break;
                }

                Intent intent = new Intent(getApplicationContext(), X72RamschVerliererGewinner.class);
                startActivity(intent);

                return false;
            }
        };

        String[] names = {"1verlierer", "2verlierer", "durchmarsch"};
        Preference preference;
        for (int i = 0; i < names.length; i++) {
            preference = findPreference(names[i]);
            preference.setOnPreferenceClickListener(listener);
        }
    }
}
