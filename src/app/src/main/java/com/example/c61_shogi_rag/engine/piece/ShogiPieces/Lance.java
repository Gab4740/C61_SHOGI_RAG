package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.piece.StraightPathPiece;
/**
 * Nom du fichier : Lance.java
 * Description : Ce fichier définit la classe représentant la pièce "Lance" du Shogi,
 *               qui hérite des propriétés de déplacement en ligne droite de la classe StraightPathPiece.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class Lance extends StraightPathPiece {
    public Lance(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
}
