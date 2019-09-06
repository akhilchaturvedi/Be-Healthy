package com.example.behealthy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmSetter extends AppCompatActivity {
    Long interval;
    SeekBar seekBar;
    EditText et_mn;
    EditText et_hour;
    EditText et_min;
    String medicinename;
    TextView tv_interval;
    int hour,min;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_setter);
        et_mn=findViewById(R.id.et_mn);
        et_hour=findViewById(R.id.et_hr);
        et_min=findViewById(R.id.et_min);
        tv_interval=findViewById(R.id.tv_interval);
        seekBar=findViewById(R.id.seekbar);
        seekBar.setProgress(1);
        seekBar.setMax(24);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                tv_interval.setText(Integer.toString(i));
                interval= Long.valueOf(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button button=findViewById(R.id.button_setNot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                medicinename=et_mn.getText().toString();
                hour=Integer.parseInt(et_hour.getText().toString());
                min=Integer.parseInt(et_min.getText().toString());
                setNotification(medicinename,hour,min);
            }
        });

    }
    private void setNotification(String medicinename,int hour,int min)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,min);
        Intent intent=new Intent(AlarmSetter.this,Notification_receiver.class);
        intent.putExtra("medicinename",medicinename);
        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Long Interval1=interval*AlarmManager.INTERVAL_HOUR;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),Interval1,pendingIntent);
        Toast.makeText(AlarmSetter.this,"Notification set",Toast.LENGTH_LONG).show();

    }
}
