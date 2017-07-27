package com.example.hp.toaster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity  {

    EditText mMessageEditText;

    RadioGroup radioGroup;
    RadioButton min10RadioButton;
    RadioButton min30RadioButton;
    RadioButton hour1RadioButton;
    RadioButton customRadioButton;

    EditText customEditText;
    TextView customMinutesTextView;

    Button toastButton;
    Button stopButton;
    private PendingIntent pendingIntent;

    TextView customErrorTextView;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageEditText = (EditText) findViewById(R.id.message_edit_text);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        min10RadioButton = (RadioButton) findViewById(R.id.min10_radio_button);
        min30RadioButton = (RadioButton) findViewById(R.id.min30_radio_button);
        hour1RadioButton = (RadioButton) findViewById(R.id.hour1_radio_button);
        customRadioButton = (RadioButton) findViewById(R.id.custom_radio_button);

        customEditText = (EditText) findViewById(R.id.custom_edit_text_view);
        customMinutesTextView = (TextView) findViewById(R.id.custom_minutes_text_view);

        toastButton = (Button) findViewById(R.id.toast_button);
        stopButton = (Button) findViewById(R.id.stop_button);

        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (checkedId == R.id.custom_radio_button) {
                    customEditText.setVisibility(EditText.VISIBLE);
                    customMinutesTextView.setVisibility(EditText.VISIBLE);
                } else {
                    customEditText.setVisibility(EditText.GONE);
                    customMinutesTextView.setVisibility(EditText.GONE);
                }
            }
        });

        customErrorTextView = (TextView) findViewById(R.id.custom_error_text_view);


        toastButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String message = mMessageEditText.getText().toString();
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();

            /*    Intent i = new Intent(MainActivity.this, AlarmToastReceiver.class);
                i.putExtra("key", message);
                sendBroadcast(i);*/


                if(min10RadioButton.isChecked())
                    startAlarm(10*60000);
                else if(min30RadioButton.isChecked())
                    startAlarm(30*60000);
                else if(hour1RadioButton.isChecked())
                    startAlarm(60*60000);
                else if(customRadioButton.isChecked()){
                    int time = Integer.parseInt(customEditText.getText().toString());

                    if(time<1 || time>3*60){
                        customErrorTextView.setVisibility(View.VISIBLE);
                    }
                    else {
                        customErrorTextView.setVisibility(View.GONE);
                        startAlarm(time*60000);
                    }

                }
            }



            private void startAlarm(int interval) {
                AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent myIntent = new Intent(MainActivity.this , AlarmToastReceiver.class);
                myIntent.putExtra("key",message);
                //startActivity(myIntent);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);;

                manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+3000, interval, pendingIntent);

            }
        });

        stopButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AlarmToastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
            }
        });




        /*Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);*/



    }


       /*public void startAlarm(View view){
           AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int interval = 3000;

            manager.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime()+3000, interval, pendingIntent);
            Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
        }*/


}

