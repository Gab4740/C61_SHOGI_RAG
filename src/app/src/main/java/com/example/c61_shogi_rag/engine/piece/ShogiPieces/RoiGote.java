package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.piece.NormalPiece;
/**
 * Nom du fichier : RoiGote.java
 * Description : Ce fichier définit la classe représentant la pièce "Roi Gote" du Shogi,
 *               qui hérite des comportements et déplacements de la classe NormalPiece.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class RoiGote extends NormalPiece {
    public RoiGote(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
}
