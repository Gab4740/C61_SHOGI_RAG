package com.example.c61_shogi_rag.engine.piece;
/**
 * Nom du fichier : Move.java
 * Description : Ce fichier définit une classe représentant un mouvement dans le jeu de Shogi,
 *               permettant de stocker la position initiale et la position cible d'une pièce.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */

public class Move {
    private final Position currentPosition;
    private final Position nextPosition;

    public Move(Position current, Position next){
        currentPosition = current;
        nextPosition = next;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }
    public Position getNextPosition() {
        return nextPosition;
    }
}
