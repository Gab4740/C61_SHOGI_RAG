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

        int deltaY = currY - finalY;
        int displacementY = 0;

        displacementY = getID() > 0 ? -1 : 1; // Noir ou Blanc : Directionnel
        while(currX != finalX){
            int previousX = currX;
            currX += displacementY ;
            if(!MoveUtils.checkSteps((currX - previousX), deltaY, getDIRECTIONS()) || board.getBoard()[currX][currY] != 0){
                return false;
            }
        }
        return true;
    }
}
