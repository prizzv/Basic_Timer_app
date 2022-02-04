package com.practice.eggtimerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekBar;
    TextView timerTextView;
    Boolean counterIsActive = false;
    CountDownTimer timeReverseCountDownTimer;
    Button reverseTimeButton;

    public void resetTiemr(){
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        timeReverseCountDownTimer.cancel();
        reverseTimeButton.setText("GO!");
        counterIsActive = false;
    }
    public void goReverseTime(View view){
        reverseTimeButton = (Button) findViewById(R.id.goButton);

        if(counterIsActive){
            resetTiemr();
        }else {
            counterIsActive = true;
            timerSeekBar.setEnabled(false);

            timeReverseCountDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000L + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) (millisUntilFinished / 1000L));
                }

                @Override
                public void onFinish() {
                    MediaPlayer timerSoundMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    timerSoundMediaPlayer.start();
                    resetTiemr();
                }
            };
            timeReverseCountDownTimer.start();
            reverseTimeButton.setText("STOP");
        }
    }

    public void updateTimer(int progress){
        int minutes = progress/60;
        int seconds = progress - (minutes*60);
        String secondString = String.format("%02d", seconds);

        timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.countDownTextView);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(30);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
}