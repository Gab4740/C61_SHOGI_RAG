package com.example.c61_shogi_rag.engine.entity;
/**
 * Nom du fichier : Joueur.java
 * Description : Ce fichier définit une classe représentant un joueur dans le jeu de Shogi,
 *               incluant son identifiant et son nom.
 * Auteur : Arslan Khaoua
 * Entête générée par Copilot
 */
public class Joueur {
    private int joueur_id;
    private String nom_joueur;

    public Joueur(){

    }

    public Joueur(int joueur_id, String nom_joueur) {
        this.joueur_id = joueur_id;
        this.nom_joueur = nom_joueur;
    }

    public int getJoueur_id() {
        return joueur_id;
    }

    public void setJoueur_id(int joueur_id) {
        this.joueur_id = joueur_id;
    }

    public String getNom_joueur() {
        return nom_joueur;
    }

    public void setNom_joueur(String nom_joueur) {
        this.nom_joueur = nom_joueur;
    }
}
