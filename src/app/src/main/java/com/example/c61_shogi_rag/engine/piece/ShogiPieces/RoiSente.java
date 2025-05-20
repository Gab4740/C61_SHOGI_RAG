package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.piece.NormalPiece;
/**
 * Nom du fichier : RoiSente.java
 * Description : Ce fichier définit la classe représentant la pièce "Roi Sente" du Shogi,
 *               qui hérite des comportements et déplacements de la classe NormalPiece.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */

public class RoiSente extends NormalPiece {
    public RoiSente(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
}
