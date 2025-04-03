package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Vector;

public class MoveGeneration {
    private int currListIndex;
    private int currPieceCount;
    private int currDirectionsIndex;
    private ShogiPiece pieceToGenMoveFrom;
    private Vector<Position> posOfSpecificPiece;
    private Position currPosition;
    private Vector<ShogiPiece> pieces;
    private Board board;
    private MoveManager currMoveToReturn;
    private boolean pieceExists;

    public MoveGeneration(Vector<ShogiPiece> pieces, Board board) {
        this.pieces = pieces;
        this.board = board;
        this.pieceToGenMoveFrom = null;

        this.currListIndex = 0;
        this.currPieceCount = 0;
        this.currDirectionsIndex = 0;
    }

    public MoveManager getCurrMoveToReturn() {
        return currMoveToReturn;
    }

    private void getNewPieceFromList(){
        pieceToGenMoveFrom = pieces.get(currListIndex);
        posOfSpecificPiece = board.getPositionsFromPiece(pieceToGenMoveFrom);
        currListIndex++;
    }
    private void getNewPieceFromBoard(){
        if(posOfSpecificPiece.size() == currPieceCount){
            getNewPieceFromList();
            currPieceCount = 0;
            currDirectionsIndex = 0;

            if(!posOfSpecificPiece.isEmpty()){
                currPosition = posOfSpecificPiece.get(currPieceCount);
                pieceExists = true;
                currPieceCount++;
            }
            else{
                pieceExists = false;
            }
        }else{
            if(!posOfSpecificPiece.isEmpty()){
                currPosition = posOfSpecificPiece.get(currPieceCount);
                pieceExists = true;
            }
            else{
                pieceExists = false;
            }
            currPieceCount++;
            currDirectionsIndex = 0;
        }
    }
    public boolean genMove(){
        if(currListIndex == 0){
            getNewPieceFromList();
            getNewPieceFromBoard();
        }
        else if(pieceToGenMoveFrom.getDIRECTIONS().length == currDirectionsIndex){
            getNewPieceFromBoard();
        }

        if(pieceExists){
            int[] direction = pieceToGenMoveFrom.getDIRECTIONS()[currDirectionsIndex];
            Position startPos = new Position(currPosition.getPosX(), currPosition.getPosY());
            Position endPos = new Position(currPosition.getPosX() + direction[1], currPosition.getPosY() + direction[0]);
            Move moveToTest = new Move(startPos, endPos);

            currMoveToReturn = null;
            if(pieceToGenMoveFrom.isValidMove(moveToTest, board)){
                byte originalTraget = board.getPieceAt(moveToTest.getNextPosition());
                currMoveToReturn = new MoveManager(originalTraget, moveToTest);
            }
            currDirectionsIndex++;
        }
        else{
            getNewPieceFromList();
        }

        return (currListIndex + 1) != pieces.size();
    }

}
