package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;

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
