package wat.skat.skatliste;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class LoadGame extends AppCompatActivity {
    private DBSpielController dbSpielCon;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbSpielCon = new DBSpielController(this);
        dbSpielCon.open();

        listView = (ListView) findViewById(R.id.listViewGames);
        listView.setEmptyView(findViewById(R.id.textViewEmpty));

        Cursor cursor = dbSpielCon.fetch();
        String[] from = new String[]{
                DBSpielContract.Entry._ID,
                DBSpielContract.Entry.COL_DATUM,
                DBSpielContract.Entry.COL_SPIELER};

        int[] into = new int[]{
                R.id.tvId,
                R.id.tvSpielDatum,
                R.id.tvSpieler};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(LoadGame.this, R.layout.activity_view_game_record, cursor, from, into, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        // OnCLickListener For List Items
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long viewId) {
                String datum       = getString(view.findViewById(R.id.tvSpielDatum));
                String spieler     = getString(view.findViewById(R.id.tvSpieler));
                Intent intent = new Intent(getApplicationContext(), SkatList.class);
                intent.putExtra("intentFlag", SkatList.INTENT_FLAG_SPIEL_FORTSETZEN);
                intent.putExtra("datum", datum);
                intent.putExtra("spieler", spieler);

                if (!datum.equals("")) {
                    startActivity(intent);
                }
            }

            private String getString(View v) {
                TextView tv = (TextView) v;
                CharSequence charSeq = tv.getText();
                if (charSeq != null) {
                    return charSeq.toString();
                } else {
                    return "";
                }
            }
        });
    }

}
