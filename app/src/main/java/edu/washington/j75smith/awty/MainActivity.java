package edu.washington.j75smith.awty;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    private PendingIntent alarmIntent = null;
    private String message;
    private String phone;
    private Float interval;
    private BroadcastReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init our receiver
        alarmReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(MainActivity.this, phone + ": " + message, Toast.LENGTH_SHORT).show();
            }
        };

        //register our receiver
        registerReceiver(alarmReceiver, new IntentFilter("edu.washington.j75smith.awty"));

        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStop();
            }
        });
    }

    private void startStop(){
        String field_phone = ((Editable) ((EditText) findViewById(R.id.number)).getText()).toString();
        String field_text = ((Editable) ((EditText) findViewById(R.id.message)).getText()).toString();;
        String field_time = ((Editable) ((EditText) findViewById(R.id.time)).getText()).toString();
        if (!field_time.equals("")) {
            Float given_time = Float.parseFloat(field_time);
            if (!field_phone.equals("") && !field_text.equals("") && given_time > 0) {
                Button theButton = (Button) findViewById(R.id.btn_send);
                //update message and interval
                this.phone = field_phone;
                this.message = field_text;

                //get the interval in minutes
                this.interval = given_time;

                //get the system manager for alarms
                AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                //make the pending intent and its intent
                Intent stuffing = new Intent();
                stuffing.setAction("edu.washington.j75smith.awty");
                alarmIntent = PendingIntent.getBroadcast(this, 0, stuffing, 0);
                if (theButton.getText().equals("Start")) {
                    //startSending
                    int millis = Math.round(interval * 60000);
                    manager.setRepeating(AlarmManager.RTC,
                            System.currentTimeMillis() + millis, millis, alarmIntent);
                    theButton.setText(R.string.btn_stop);
                } else {
                    //stop sending
                    manager.cancel(alarmIntent);
                    alarmIntent.cancel();
                    theButton.setText(R.string.btn_start);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.alarmReceiver);
    }
}
