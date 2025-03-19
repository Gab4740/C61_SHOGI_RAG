package com.example.c61_shogi_rag.engine.entity;

public class JoueurInfo {

    int idJoueur;
    String motDePasse;

    public JoueurInfo() {
    }

    public JoueurInfo(int idJoueur, String motDePasse) {
        this.idJoueur = idJoueur;
        this.motDePasse = motDePasse;
    }

    public int getIdJoueur() {
        return idJoueur;
    }

    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }
}
