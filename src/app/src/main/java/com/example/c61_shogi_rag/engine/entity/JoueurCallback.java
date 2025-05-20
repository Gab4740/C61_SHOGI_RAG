package com.example.c61_shogi_rag.engine.entity;
/**
 * Nom du fichier : JoueurCallback.java
 * Description : Ce fichier définit une interface permettant de gérer les rappels lors de la récupération d'un joueur,
 *               ainsi que la gestion des erreurs éventuelles.
 * Auteur : Arslan Khaoua
 * Entête générée par Copilot
 */public interface JoueurCallback {
    void onJoueurRecupere(Joueur joueur);

    void onError(String message);
}
