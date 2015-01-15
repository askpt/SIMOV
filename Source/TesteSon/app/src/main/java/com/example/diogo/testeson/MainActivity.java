package com.example.diogo.testeson;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    Button btStart;
    Button btStop;
    TextView tvAmplitude;
    son son;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btStart = (Button) findViewById(R.id.bt_Start);
        btStop = (Button) findViewById(R.id.bt_stop);
        tvAmplitude = (TextView) findViewById(R.id.tv_amplitude);


        btStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                son.start();
                tvAmplitude.setText(String.valueOf(son.getAmplitude()));
                // Perform action on click
            }
        });

        btStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                son.stop();
                // Perform action on click
            }
        });

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
