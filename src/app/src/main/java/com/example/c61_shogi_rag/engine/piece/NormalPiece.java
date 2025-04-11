package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;

public class NormalPiece extends ShogiPiece{

    public NormalPiece(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
    @Override
    protected boolean allMoveChecks(Move move, Board board) {
        if(defaultCheck(move) && secondaryStep(board)){
            int deltaX = finalX - currX;
            int deltaY = finalY - currY;
            return MoveUtils.checkSteps(deltaX, deltaY, getDIRECTIONS());
        };
        return false;
    }
}
