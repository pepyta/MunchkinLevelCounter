package com.pepyta.munchkinlevelcounter;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

public class MainActivity extends AppCompatActivity{
    private ShakeDetector shakeDetector;
    int gear = 0;
    int level = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildView();

        ShakeOptions options = new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(2)
                .sensibility(2.0f);

        this.shakeDetector = new ShakeDetector(options).start(this, new ShakeCallback() {
            @Override
            public void onShake() {
                Log.d("event", "onShake");
                roll();
            }
        });
        //IF YOU WANT JUST IN BACKGROUND
        //this.shakeDetector = new ShakeDetector(options).start(this);
    }
    private void buildView() {
        Button btnStopService = (Button) findViewById(R.id.btnStopService);
        btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("destroy", "destroy service shake");
                shakeDetector.stopShakeDetector(getBaseContext());
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
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

    public void roll(){
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
}