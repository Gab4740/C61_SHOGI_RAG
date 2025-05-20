package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.piece.NormalPiece;

/**
 * Nom du fichier : GeneralArgent.java
 * Description : Ce fichier définit la classe représentant la pièce "Général Argent" du Shogi,
 *               qui hérite des comportements et déplacements de la classe NormalPiece.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public class GeneralArgent extends NormalPiece {
    public GeneralArgent(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
}
