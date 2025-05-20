package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.ExceptionPiece;
import com.example.c61_shogi_rag.engine.piece.MoveUtils;
/**
 * Nom du fichier : Fou.java
 * Description : Ce fichier définit la classe représentant la pièce "Fou" du Shogi,
 *               qui hérite des comportements exceptionnels et se déplace en diagonale sur le plateau.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public class Fou extends ExceptionPiece {
    public Fou(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }

    @Override
    public boolean exception(Board board) {
        return MoveUtils.checkDiagonals(finalX, finalY, currX, currY, board, getDIRECTIONS());
    }
}
