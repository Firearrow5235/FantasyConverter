package thepixelshades.fantasyconverter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set spinner content

        Spinner fantasy = (Spinner) findViewById(R.id.fantasy_spinner);
        ArrayAdapter<CharSequence> fantasyAdapter = ArrayAdapter.createFromResource(this, R.array.mon_fantasy, android.R.layout.simple_spinner_item);
        fantasyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fantasy.setAdapter(fantasyAdapter);

        Spinner real = (Spinner) findViewById(R.id.real_spinner);
        ArrayAdapter<CharSequence> realAdapter = ArrayAdapter.createFromResource(this, R.array.mon_real, android.R.layout.simple_spinner_item);
        fantasyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        real.setAdapter(realAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void calculate(View view){
        EditText fantValue = (EditText) findViewById(R.id.fantasy_value);
        TextView realValue = (TextView) findViewById(R.id.real_value);
        Spinner fantSpinner = (Spinner) findViewById(R.id.fantasy_spinner);
        DecimalFormat df = new DecimalFormat("#.00");

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        Resources res = getResources();

        int coinRadius = Integer.parseInt(pref.getString("pref_coin_width", ""));
        coinRadius = coinRadius / 2;
        float coinThickness = Float.parseFloat(pref.getString("pref_coin_thickness", ""));

        float coinVolumeMM = (float) ((coinRadius * coinRadius) * Math.PI * coinThickness);
        float coinMass = (float) (coinVolumeMM / 10) * res.getFraction(R.dimen.gold_density, 1, 1) / 100;
        float coinPrice = (float) coinMass * res.getFraction(R.dimen.gold_price, 1, 1);

        switch (fantSpinner.getSelectedItem().toString()){
            case "Gold":
                break;
            case "Silver":
                coinPrice = coinPrice / 10;
                break;
            case "Copper":
                coinPrice = coinPrice / 100;
        }

        realValue.setText(df.format(coinPrice * Integer.parseInt(fantValue.getText().toString())));
    }
}
