package com.example.c61_shogi_rag.game;

import java.util.Timer;
import java.util.TimerTask;

public class Time{
    private int timePassed;
    private Timer timer;
    private TimerTask task;
    private int minutes;
    private int secondes;
    private String displayTime;

    public Time(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                timePassed++;
                secondes = timePassed % 60;
                if(secondes == 0){
                    minutes++;
                }

                String minutesFormat;
                String secondeFormat;
                if(secondes < 10){
                    secondeFormat = "0" + secondes;
                }
                else{
                    secondeFormat = String.valueOf(secondes);
                }
                if(minutes < 10) {
                    minutesFormat = "0" + minutes;
                }
                else{
                    minutesFormat = String.valueOf(minutes);
                }
            }
        };
    }

    public void startTime(){
        timer.schedule(task, 1000, 1000);
    }

    public int getTimePassed() {
        return timePassed;
    }
    public String getDisplayTime(){
        return displayTime;
    }
}
