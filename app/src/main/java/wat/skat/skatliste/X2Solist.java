package wat.skat.skatliste;


import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

public class X2Solist extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(SkatInfoSingleton.getInstance().spiel + ": " + getTitle());
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
        addPreferencesFromResource(R.xml.pref_2_solist);

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
                infoSingleton.solist = solist_Abs;

                Intent intent = new Intent(getApplicationContext(), X3Ansagen.class);
                intent.putExtra("vor", true);
                startActivity(intent);

                return false;
            }
        };

        final SkatInfoSingleton infoSingleton = SkatInfoSingleton.getInstance();
        String[] spieler = getPlayingPlayersInOrder(infoSingleton.geber, infoSingleton.spielerzahl,
                infoSingleton.spieler1, infoSingleton.spieler2, infoSingleton.spieler3,
                infoSingleton.spieler4, infoSingleton.spieler5);

        Preference preference;
        String[] keys = {"spieler1", "spieler2", "spieler3"};
        for (int i = 0; i < keys.length; i++) {
            preference = findPreference(keys[i]);
            preference.setTitle(spieler[i]);
            preference.setOnPreferenceClickListener(listener);
        }
    }

    private String[] getPlayingPlayersInOrder(int geber, int spielerzahl, String spieler1, String spieler2, String spieler3, String spieler4, String spieler5) {
        String[] res = new String[3];

        switch (geber) {
            case 1:
                res[0] = spieler2;
                res[1] = spieler3;
                res[2] = (spielerzahl >= 4) ?
                        spieler4 : spieler1;
                break;
            case 2:
                res[0] = spieler3;
                switch (spielerzahl) {
                    case 3:
                        res[1] = spieler1;
                        res[2] = spieler2;
                        break;
                    case 4:
                        res[1] = spieler4;
                        res[2] = spieler1;
                        break;
                    case 5:
                        res[1] = spieler4;
                        res[2] = spieler5;
                        break;
                }
                break;
            case 3:
                switch (spielerzahl) {
                    case 3:
                        res[0] = spieler1;
                        res[1] = spieler2;
                        res[2] = spieler3;
                        break;
                    case 4:
                        res[0] = spieler4;
                        res[1] = spieler1;
                        res[2] = spieler2;
                        break;
                    case 5:
                        res[0] = spieler4;
                        res[1] = spieler5;
                        res[2] = spieler1;
                        break;
                }
                break;
            case 4:
                if (spielerzahl == 4) {
                    res[0] = spieler1;
                    res[1] = spieler2;
                    res[2] = spieler3;
                } else {
                    // spielerzahl == 5
                    res[0] = spieler5;
                    res[1] = spieler1;
                    res[2] = spieler2;
                }
                break;
            case 5:
                res[0] = spieler1;
                res[1] = spieler2;
                res[2] = spieler3;
                break;
        }

        return res;
    }
}