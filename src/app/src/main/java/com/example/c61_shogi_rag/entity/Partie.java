package com.example.c61_shogi_rag.entity;

public class Partie {
    int partie_id;
    int joueur_gagnant;
    String date;
    int loser_id;

    public Partie(int partie_id, int joueur_gagnant, String date, int loser_id) {
        this.partie_id = partie_id;
        this.joueur_gagnant = joueur_gagnant;
        this.date = date;
        this.loser_id = loser_id;
    }

    public int getPartie_id() {
        return partie_id;
    }

    public void setPartie_id(int partie_id) {
        this.partie_id = partie_id;
    }

    public int getJoueur_gagnant() {
        return joueur_gagnant;
    }

    public void setJoueur_gagnant(int joueur_gagnant) {
        this.joueur_gagnant = joueur_gagnant;
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
