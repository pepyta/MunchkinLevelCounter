package com.pepyta.munchkinlevelcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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

    private void updateLevel() {
        TextView displayInteger = (TextView)findViewById(R.id.level);
        displayInteger.setText(Integer.toString(level));
    }

    private void updateGear() {
        TextView displayInteger = (TextView)findViewById(R.id.gear);
        displayInteger.setText(Integer.toString(gear));
    }

    private void updateOverall() {
        TextView displayInteger = (TextView)findViewById(R.id.overall);
        displayInteger.setText(Integer.toString(level + gear));
    }
}
