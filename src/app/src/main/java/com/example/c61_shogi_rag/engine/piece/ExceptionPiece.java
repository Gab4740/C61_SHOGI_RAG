package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;
/**
 * Nom du fichier : ExceptionPiece.java
 * Description : Ce fichier définit une classe abstraite représentant une pièce exceptionnelle du Shogi,
 *               dont les mouvements sont régis par des règles spécifiques nécessitant une validation particulière.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public abstract class ExceptionPiece extends ShogiPiece{

    public ExceptionPiece(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }

    @Override
    protected boolean allMoveChecks(Move move, Board board) {
        if(defaultCheck(move)){
            return exception(board);
        };
        return false;
    }
    public abstract boolean exception(Board board);
}
