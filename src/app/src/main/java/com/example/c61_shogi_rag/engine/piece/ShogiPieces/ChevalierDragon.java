package com.example.c61_shogi_rag.engine.piece.ShogiPieces;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.MoveUtils;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

public class ChevalierDragon extends ShogiPiece {
    public ChevalierDragon(byte id, byte idPromu, int imageId, int imageIdPromu, String nom, int[][] directions) {
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

        boolean pieceColor = board.getPieceAt(new Position(currX, currY)) > 0;
        boolean targetPieceColor = board.getPieceAt(new Position(finalX, finalY)) > 0;
        if(pieceColor == targetPieceColor && board.getPieceAt(new Position(finalX, finalY)) != 0){
            return false;
        }

        int deltaX = finalX - currX;
        int deltaY = finalY - currY;
        if(deltaX > 1 && deltaY > 1){ return MoveUtils.checkDiagonals(finalX, finalY, currX, currY, board, getDIRECTIONS()); }
        else{ return MoveUtils.checkSteps(deltaX, deltaY, getDIRECTIONS()); }
    }
}
