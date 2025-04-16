package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class MoveGeneration {
    private int currListIndex;
    private int currPieceCount;
    private int currDirectionsIndex;
    private ShogiPiece pieceToGenMoveFrom;
    private Vector<Position> posOfSpecificPiece;
    private Position currPosition;
    private Vector<ShogiPiece> pieces;
    private Hashtable<Byte, ShogiPiece> piecesObj;
    private PromotionState promotionStateMap;
    private Board board;
    private MoveManager currMoveToReturn;
    private boolean pieceExists;

    public MoveGeneration(Vector<ShogiPiece> pieces, Board board, PromotionState promotionState, Hashtable<Byte, ShogiPiece> piecesObj) {
        this.pieces = pieces;
        this.board = board;
        this.pieceToGenMoveFrom = null;
        this.promotionStateMap = promotionState;
        this.piecesObj = piecesObj;

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
            int[] direction;
            ShogiPiece PieceToTest;
            boolean promote = false;
            if(promotionStateMap.isPiecePromoted(currPosition)){
                direction = piecesObj.get(pieceToGenMoveFrom.getID_PROMU()).getDIRECTIONS()[currDirectionsIndex];
                PieceToTest = piecesObj.get(pieceToGenMoveFrom.getID_PROMU());
                promote = true;
            }
            else{
                direction = pieceToGenMoveFrom.getDIRECTIONS()[currDirectionsIndex];
                PieceToTest = pieceToGenMoveFrom;
            }

            Position startPos = new Position(currPosition.getPosX(), currPosition.getPosY());
            Position endPos = new Position(currPosition.getPosX() + direction[1], currPosition.getPosY() + direction[0]);
            Move moveToTest = new Move(startPos, endPos);

            currMoveToReturn = null;
            if(PieceToTest.isValidMove(moveToTest, board)){
                byte originalTraget = board.getPieceAt(endPos);
                currMoveToReturn = new MoveManager(originalTraget, moveToTest);

                if(promote){
                    promotionStateMap.removePromotedPosition(startPos);
                    promotionStateMap.promotePiece(endPos);
                }
                // ALWAYS PROMOTE
                else if (currMoveToReturn.checkIfShouldBePromoted()) {
                    promotionStateMap.promotePiece(endPos);
                }
            }
            currDirectionsIndex++;

            // ADD EXCEPTION FOR PIECE THAT ARE PATHING
        }
        else{
            getNewPieceFromList();
            getNewPieceFromBoard();
            return (currListIndex - 1) != pieces.size();
        }
        return (currListIndex != pieces.size() || pieceToGenMoveFrom.getDIRECTIONS().length != currDirectionsIndex);
    }

    public void setBoard(Board board) {
        this.board = board;
        currListIndex = 0;
        currPieceCount = 0;
        currDirectionsIndex = 0;
    }
    public Board getBoard(){
        return this.board;
    }
}
