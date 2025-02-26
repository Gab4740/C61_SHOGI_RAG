package com.example.c61_shogi_rag.entity;
public class HistoriqueCoups {

    private int coups_id, partie_id, nom_joueur;
    private String position_x, position_y, nom_piece,couleur_piece;

    public HistoriqueCoups(int coups_id, int partie_id, int nom_joueur,
                           String position_x, String position_y, String nom_piece, String couleur_piece) {
        this.coups_id = coups_id;
        this.partie_id = partie_id;
        this.nom_joueur = nom_joueur;
        this.position_x = position_x;
        this.position_y = position_y;
        this.nom_piece = nom_piece;
        this.couleur_piece = couleur_piece;
    }

    public int getCoups_id() {
        return coups_id;
    }

    public void setCoups_id(int coups_id) {
        this.coups_id = coups_id;
    }

    public int getPartie_id() {
        return partie_id;
    }

    public void setPartie_id(int partie_id) {
        this.partie_id = partie_id;
    }

    public int getNom_joueur() {
        return nom_joueur;
    }

    public void setNom_joueur(int nom_joueur) {
        this.nom_joueur = nom_joueur;
    }

    public String getPosition_x() {
        return position_x;
    }

    public void setPosition_x(String position_x) {
        this.position_x = position_x;
    }

    public String getPosition_y() {
        return position_y;
    }

    public void setPosition_y(String position_y) {
        this.position_y = position_y;
    }

    public String getNom_piece() {
        return nom_piece;
    }

    public void setNom_piece(String nom_piece) {
        this.nom_piece = nom_piece;
    }

    public String getCouleur_piece() {
        return couleur_piece;
    }

    public void setCouleur_piece(String couleur_piece) {
        this.couleur_piece = couleur_piece;
    }
}
