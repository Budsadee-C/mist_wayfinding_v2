package com.juniper.mist_wayfinding_v2;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import com.juniper.mist_wayfinding_v2.R;
import com.juniper.mist_wayfinding_v2.AddTokenDialogFragment;
import com.juniper.mist_wayfinding_v2.HomeFragment;
import com.juniper.mist_wayfinding_v2.MapFragment;

public class MainActivity extends AppCompatActivity implements HomeFragment.SdkTokenReceivedListener,
        AddTokenDialogFragment.SdkTokenSavedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_title_bar_name);
            //setting up the home fragment
            setUpHomeFragment();
        }
    }
    private void setUpHomeFragment() {
        Fragment tokenFragment = getSupportFragmentManager().
                findFragmentByTag(HomeFragment.TAG);

        if (tokenFragment == null) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.frame_fragment,
                            HomeFragment.newInstance(), HomeFragment.TAG).
                    addToBackStack(HomeFragment.TAG).
                    commit();
        }
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().getBackStackEntryCount();
        } else {
            super.onBackPressed();
        }
    }
    private void setUpMapFragment(String sdkToken) {
        Fragment mapFragment = getSupportFragmentManager().
                findFragmentByTag(MapFragment.TAG);

        if (mapFragment == null) {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.frame_fragment,
                            MapFragment.newInstance(sdkToken), MapFragment.TAG).
                    addToBackStack(MapFragment.TAG).
                    commit();
        }
    }
    @Override
    public void onSdkTokenSaved(String token) {
        Snackbar.make(findViewById(android.R.id.content), R.string.sdk_token_saved, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void OnSdkTokenReceived(String sdkToken) {
        setUpMapFragment(sdkToken);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}