package com.pepyta.munchkinlevelcounter;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ShakeDetector shakeDetector;
    int gear = 0;
    int level = 1;
    boolean woman = false;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Your roll's result: "+rollDice(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        level = prefs.getInt("level", 1);
        gear = prefs.getInt("gear", 0);
        woman = prefs.getBoolean("woman", false);

        ShakeOptions options = new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(2)
                .sensibility(2.0f);

        this.shakeDetector = new ShakeDetector(options).start(this, new ShakeCallback() {
            @Override
            public void onShake() {
                Log.d("event", "onShake");
                roll(findViewById(R.id.fab));
            }
        });
        updateGear();
        updateLevel();
        updateOverall();
        updateGender();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editPrefs = prefs.edit();
        editPrefs.putInt("level", level);
        editPrefs.putInt("gear", gear);
        editPrefs.putBoolean("woman", woman);
        editPrefs.commit();
    }
    @Override
    protected void onDestroy() {
        shakeDetector.destroy(getBaseContext());
        super.onDestroy();
    }
    /*
    Level increase & decrease
     */
    public void increaseLevel(android.view.View view){
        if(level < 22)
            level++;
        updateLevel();
        updateOverall();
    }

    public void decreaseLevel(android.view.View view){
        if(level > 1)
            level--;

        updateLevel();
        updateOverall();

    }

    public void resetLevel(android.view.View view){
        if(level > 1)
            level = 1;

        updateLevel();
        updateOverall();

    }


    /*
    Gear increase & decrease
     */
    public void increaseGear(android.view.View view){
        gear++;
        updateGear();
        updateOverall();
    }

    public void decreaseGear(android.view.View view){
        if(gear > 0)
            gear--;

        updateGear();
        updateOverall();

    }

    public void roll(android.view.View view){
        int roll = rollDice();
        Snackbar.make(view, "Your roll's result: "+ roll, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show();
    }
    public int rollDice(){
        int dobas = (int)(Math.random()*6+1);
        return dobas;
    }

    private void updateLevel() {
        TextView displayInteger = (TextView)findViewById(R.id.level);
        displayInteger.setText(Integer.toString(level));
    }

    private void updateGear() {
        TextView displayInteger = (TextView)findViewById(R.id.gear);
        displayInteger.setText(Integer.toString(gear));
    }

    private void updateGender() {
        Button gender = (Button)findViewById(R.id.gender);
        if(woman){
            gender.setText("MALE");
        } else {
            gender.setText("FEMALE");
        }
    }


    private void updateOverall() {
        TextView displayInteger = (TextView)findViewById(R.id.total);
        displayInteger.setText(Integer.toString(level + gear));
    }

    public void changeGender(android.view.View view)
    {
        /*ImageButton gender = (ImageButton)findViewById(R.id.gender);
        if(woman){
            gender.setBackgroundResource(R.drawable.male);
            woman = false;
        } else {
            gender.setBackgroundResource(R.drawable.female);
        }
            */
        if(woman){
            woman = false;
        } else {
            woman = true;
        }
        updateGender();
    }
}
