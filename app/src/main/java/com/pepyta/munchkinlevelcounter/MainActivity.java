package com.pepyta.munchkinlevelcounter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

public abstract class MainActivity extends AppCompatActivity implements SensorListener{
    int gear = 0;
    int level = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        updateRNG(rollDice());
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Roll");
        alert.setMessage(Integer.toString(rollDice()));
        alert.show();

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

    private void updateOverall() {
        TextView displayInteger = (TextView)findViewById(R.id.total);
        displayInteger.setText(Integer.toString(level + gear));
    }

    private void updateRNG(int dobas) {
        TextView displayInteger = (TextView)findViewById(R.id.rng);
        displayInteger.setText(Integer.toString(dobas)); // "dasdsasdajdsoajdosajoasj" 132 => "132"
    }


     SensorManager sensorMgr = (SensorManager)getSystemService(SENSOR_SERVICE);
sensorMgr.registerListener(this,
    SensorManager.SENSOR_ACCELEROMETER,
    SensorManager.SENSOR_DELAY_GAME);
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    Log.d("sensor", "shake detected w/ speed: " + speed);
                    Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }

    private static final int SHAKE_THRESHOLD = 800;
}