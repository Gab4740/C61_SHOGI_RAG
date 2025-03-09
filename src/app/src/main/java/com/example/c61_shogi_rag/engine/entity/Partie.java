package com.example.c61_shogi_rag.engine.entity;

public class Partie {
    int partie_id;
    int winner_id;
    String date;
    int loser_id;

    public Partie() {
    }


    public Partie(int partie_id, int winner_id, String date, int loser_id) {
        this.partie_id = partie_id;
        this.winner_id = winner_id;
        this.date = date;
        this.loser_id = loser_id;
    }

    public int getPartie_id() {
        return partie_id;
    }

    public void setPartie_id(int partie_id) {
        this.partie_id = partie_id;
    }

    public int getWinner_id() {
        return winner_id;
    }

    public void setWinner_id(int winner_id) {
        this.winner_id = winner_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLoser_id() {
        return loser_id;
    }

    public void setLoser_id(int loser_id) {
        this.loser_id = loser_id;
    }
}
