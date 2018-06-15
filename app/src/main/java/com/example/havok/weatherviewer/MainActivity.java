package com.example.havok.weatherviewer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.havok.weatherviewer.Fragments.WeatherOfflineFragment;
import com.example.havok.weatherviewer.Fragments.WeatherOnlineFragment;

public class MainActivity extends AppCompatActivity {
    public static final String
            TAG = "WeatherViewer",
            PREFERENCES_KEY = "preferences";


    private WeatherOnlineFragment weatherOnlineFragment;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreferences = getPreferences(Activity.MODE_PRIVATE);

        if (savedInstanceState == null) {
            if (isNetworkAvailable()) {
                weatherOnlineFragment = new WeatherOnlineFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, weatherOnlineFragment)
                        .commit();
            } else {
                WeatherOfflineFragment weatherOfflineFragment = new WeatherOfflineFragment();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, weatherOfflineFragment)
                        .commit();
            }
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public String getLocation() {
        return sharedPreferences.getString("city", "Sydney, AU");
    }

    public void setLocation(final String location) {
        sharedPreferences.edit().putString("city", location).apply();
    }

    public void showChangeLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.change_loc_dialog));

        final EditText editText = new EditText(this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(editText);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                weatherOnlineFragment.changeLocation(editText.getText().toString());
            }
        });

        builder.show();
    }
}
