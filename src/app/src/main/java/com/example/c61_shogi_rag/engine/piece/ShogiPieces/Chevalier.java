package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.piece.NormalPiece;
/**
 * Nom du fichier : Chevalier.java
 * Description : Ce fichier définit la classe représentant la pièce "Chevalier" du Shogi,
 *               qui hérite des propriétés de déplacement de la classe NormalPiece.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public class Chevalier extends NormalPiece {
    public Chevalier(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
}
