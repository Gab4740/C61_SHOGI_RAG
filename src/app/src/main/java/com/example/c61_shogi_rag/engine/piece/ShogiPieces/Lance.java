package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.MoveUtils;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

public class Lance extends ShogiPiece {
    public Lance(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
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

        int deltaY = currY - finalY;
        int displacementY = 0;

        return MoveUtils.checkCross(finalX, finalY, currX, currY, board, getDIRECTIONS());
    }
}
