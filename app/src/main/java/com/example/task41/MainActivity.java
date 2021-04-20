package com.example.task41;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Handler;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView textView, recordView, workingtype;

    ImageButton start;
    ImageButton pause;
    ImageButton lap ;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;

    Handler handler;

    int Seconds, Minutes, timeView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Seconds = 0;
        Minutes = 0;

        textView = (TextView)findViewById(R.id.timeView);
        start = (ImageButton)findViewById(R.id.btn_start);
        pause = (ImageButton)findViewById(R.id.btn_pause);
        lap = (ImageButton)findViewById(R.id.btn_record);
        recordView = (TextView)findViewById(R.id.recordView);
        workingtype = (TextView)findViewById(R.id.workouttype);

        handler = new Handler() ;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimeBuff += MillisecondTime;

                handler.removeCallbacks(runnable);

            }
        });


        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;

                recordView.setText("You spent "+ textView.getText().toString()+" on "+workingtype.getText().toString());

                textView.setText("00:00");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("key_Minutes", Minutes);
        outState.putInt("key_Seconds", Seconds);

        textView.setText("" + Minutes);
        textView.setText("" + Seconds);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Minutes = savedInstanceState.getInt("key_Minutes", 0);
        Seconds = savedInstanceState.getInt("key_Seconds", 0);

        StartTime = SystemClock.uptimeMillis();

        textView.setText("" + String.format("%02d",Minutes) + ":"
                + String.format("%02d", Seconds));

    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            textView.setText("" + String.format("%02d",Minutes) + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }
    };
}