package com.example.c61_shogi_rag.engine.piece;
/**
 * Nom du fichier : PieceIDs.java
 * Description : Ce fichier définit une énumération des identifiants des pièces de Shogi,
 *               facilitant leur manipulation et leur reconnaissance dans le jeu.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public enum PieceIDs{
    Roi(127),
    Char(9),
    Fou(8),
    GeneralOr(6),
    GeneralArgent(5),
    Chevalier(4),
    Lance(3),
    Pion(1),
    RoiDragon(11),
    ChevalDragon(10);

    private final int value;
    PieceIDs(int value) {
        this.value = value;
    }
    public int getValue() {
        return this.value;
    }
}