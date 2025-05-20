package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;
/**
 * Nom du fichier : StraightPathPiece.java
 * Description : Ce fichier définit une classe représentant une pièce de Shogi
 *               se déplaçant en ligne droite, incluant la validation de ses mouvements sur le plateau.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */

public class StraightPathPiece extends ShogiPiece{
    public StraightPathPiece(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }

    @Override
    protected boolean allMoveChecks(Move move, Board board) {
        if(defaultCheck(move)){
            return MoveUtils.checkCross(finalX, finalY, currX, currY, board, getDIRECTIONS());
        };
        return false;
    }
}
