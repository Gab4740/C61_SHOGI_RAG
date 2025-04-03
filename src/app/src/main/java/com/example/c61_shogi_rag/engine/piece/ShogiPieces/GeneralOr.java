package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.MoveUtils;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

public class GeneralOr extends ShogiPiece {
    public GeneralOr(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
        super(id, idPromu, imageId, imageIdPromu, nom, directions);
    }
    @Override
    public boolean isValidMove(Move move, Board board) {
        int currX = move.getCurrentPosition().getPosX();
        int currY = move.getCurrentPosition().getPosY();
        int finalX = move.getNextPosition().getPosX();
        int finalY = move.getNextPosition().getPosY();

        if(finalY > 9 || finalY < 0 || finalX > 9 || finalX < 0){
            return false;
        }

        int deltaX = finalX - currX;
        int deltaY = finalY - currY;
        return MoveUtils.checkSteps(deltaX, deltaY, getDIRECTIONS());
    }
}
