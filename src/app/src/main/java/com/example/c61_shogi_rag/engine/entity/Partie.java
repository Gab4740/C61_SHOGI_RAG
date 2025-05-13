package com.example.c61_shogi_rag.engine.entity;

public class Partie {

    private int partie_id;
    private int winner_id;
    private int loser_id;
    private String date;
    private String historiqueCoups;
    private boolean playerCouleur;
    private boolean partieTerminee;

    public Partie() {
    }

    public Partie(int partie_id, int winner_id, int loser_id, String date, String historiqueCoups, boolean playerCouleur, boolean partieTerminee) {
        this.partie_id = partie_id;
        this.winner_id = winner_id;
        this.loser_id = loser_id;
        this.date = date;
        this.historiqueCoups = historiqueCoups;
        this.playerCouleur = playerCouleur;
        this.partieTerminee = partieTerminee;
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

    public int getLoser_id() {
        return loser_id;
    }

    public void setLoser_id(int loser_id) {
        this.loser_id = loser_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHistoriqueCoups() {
        return historiqueCoups;
    }

    public void setHistoriqueCoups(String historiqueCoups) {
        this.historiqueCoups = historiqueCoups;
    }

    public boolean isPlayerCouleur() {
        return playerCouleur;
    }

    public void setPlayerCouleur(boolean playerCouleur) {
        this.playerCouleur = playerCouleur;
    }

    public boolean isPartieTerminee() {
        return partieTerminee;
    }

    public void setPartieTerminee(boolean partieTerminee) {
        this.partieTerminee = partieTerminee;
    }
}
