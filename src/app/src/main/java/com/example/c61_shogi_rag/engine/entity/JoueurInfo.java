package com.example.c61_shogi_rag.engine.entity;
/**
 * Nom du fichier : JoueurInfo.java
 * Description : Ce fichier définit une classe permettant de stocker les informations d'un joueur,
 *               incluant son identifiant et son mot de passe.
 * Auteur : Arslan Khaoua
 * Entête générée par Copilot
 */
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
