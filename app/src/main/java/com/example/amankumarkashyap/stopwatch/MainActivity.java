package com.example.amankumarkashyap.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button start,stop,reset;
    int seconds;
    boolean running,wasRunning;
    TextView timeView;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startButton);
        stop = findViewById(R.id.stopButton);
        reset = findViewById(R.id.resetButton);
        timeView = findViewById(R.id.textView);
        if(savedInstanceState != null)
        {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false ;
                seconds = 0;
            }
        });

        runTimer();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning)
            running =true;
    }

    private void runTimer() {
        mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                int  hours = (seconds/3600);
                int minutes = (seconds%3600)/60;
                int mseconds = seconds%60;
                String time = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,mseconds);
                timeView.setText(time);
                if(running)
                    seconds++;
                mHandler.postDelayed(this,1000);
            }
        });

    }
}
