package com.example.c61_shogi_rag.engine.game;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Nom du fichier : Time.java
 * Description : Ce fichier définit une classe permettant de gérer un chronomètre,
 *               incluant le suivi du temps écoulé et l'affichage formaté du temps.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class Time{
    private int timePassed;
    private Timer timer;
    private TimerTask task;
    private int minutes;
    private int secondes;
    private String displayTime;
    private boolean isStarted;

    public Time(){
        this.timePassed = 0;
        this.minutes = 0;
        this.secondes = 0;
        this.displayTime = "00 : 00";
        this.isStarted = false;
        setTimer();

    }

    public void setTimer(){
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
                displayTime = minutesFormat + " : " + secondeFormat;
            }
        };
    }

    public void startTime(){
        if (isStarted){
            return;
        }

        stopTime();
        setTimer();
        timer.schedule(task, 0, 1000);
        isStarted = true;
    }
    public int getTimePassed() {
        return timePassed;
    }
    public String getDisplayTime(){
        return displayTime;
    }

    public void stopTime(){
        if (timer != null){
            timer.cancel();
            timer.purge();
        }
        isStarted = false;
    }

}
