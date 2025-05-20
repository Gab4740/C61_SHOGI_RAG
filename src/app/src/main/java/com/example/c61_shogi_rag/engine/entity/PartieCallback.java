package com.example.c61_shogi_rag.engine.entity;

import java.util.List;
/**
 * Nom du fichier : PartieCallback.java
 * Description : Ce fichier définit une interface permettant de gérer les rappels
 *               lors de la récupération des parties, la création d'une partie et la gestion des erreurs.
 * Auteur : Arslan Khaoua
 * Entête générée par Copilot
 */
public interface PartieCallback {
    void onPartiesRecuperees(List<Partie> partieList);

    void onPartieCree(String succes);

    void onError(Exception exception);
}
