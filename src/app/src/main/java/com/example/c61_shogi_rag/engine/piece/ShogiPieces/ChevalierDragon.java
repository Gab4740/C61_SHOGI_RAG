package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.ExceptionPiece;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.MoveUtils;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

public class ChevalierDragon extends ExceptionPiece {
    public ChevalierDragon(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }

    @Override
    public boolean exception(Board board) {
        if(secondaryStep(board)){
            int deltaX = finalX - currX;
            int deltaY = finalY - currY;
            if(Math.abs(deltaX) > 1 && Math.abs(deltaY) > 1){ return MoveUtils.checkDiagonals(finalX, finalY, currX, currY, board, getDIRECTIONS()); }
            else{ return MoveUtils.checkSteps(deltaX, deltaY, getDIRECTIONS()); }
        }
        return false;
    }
}
