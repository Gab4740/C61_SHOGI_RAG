package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
/**
 * Nom du fichier : MoveGeneration.java
 * Description : Ce fichier implémente la génération des mouvements pour les pièces de Shogi,
 *               permettant de parcourir et tester les déplacements possibles en fonction des règles du jeu.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class MoveGeneration {
    private int currListIndex;
    private int currPieceCount;
    private int currDirectionsIndex;
    private int parachutePieceIndex;
    private ShogiPiece pieceToGenMoveFrom;
    private Vector<Position> posOfSpecificPiece;
    private Position currPosition;
    private Vector<ShogiPiece> pieces;
    private Hashtable<Byte, ShogiPiece> piecesObj;
    private PromotionState promotionStateMap;
    private Board board;
    private MoveManager currMoveToReturn;
    private boolean pieceExists;
    private LinkedHashMap<String, Integer> capturedPieceBlackHM;
    private Vector<ShogiPiece> pieceToParachute;

    public MoveGeneration(Vector<ShogiPiece> pieces, Board board, PromotionState promotionState, Hashtable<Byte, ShogiPiece> piecesObj, LinkedHashMap<String, Integer> capturedPieceBlackHM) {
        this.pieces = pieces;
        this.board = board;
        this.pieceToGenMoveFrom = null;
        this.promotionStateMap = promotionState;
        this.piecesObj = piecesObj;

        this.currListIndex = 0;
        this.currPieceCount = 0;
        this.currDirectionsIndex = 0;
        this.parachutePieceIndex = 0;

        this.capturedPieceBlackHM = capturedPieceBlackHM;
    }

    public MoveManager getCurrMoveToReturn() {
        return currMoveToReturn;
    }

    private void getNewPieceFromList() {
        if (currListIndex >= pieces.size()) {
            pieceExists = false;
            return;
        }

        pieceToGenMoveFrom = pieces.get(currListIndex);
        posOfSpecificPiece = board.getPositionsFromPiece(pieceToGenMoveFrom);
        currListIndex++;
    }
    private void getNewPieceFromBoard() {
        if (posOfSpecificPiece == null || posOfSpecificPiece.size() == currPieceCount) {
            getNewPieceFromList();
            currPieceCount = 0;
            currDirectionsIndex = 0;

            if (posOfSpecificPiece != null && !posOfSpecificPiece.isEmpty()) {
                currPosition = posOfSpecificPiece.get(currPieceCount);
                pieceExists = true;
                currPieceCount++;
            } else {
                pieceExists = false;
            }
        } else {
            if (!posOfSpecificPiece.isEmpty()) {
                currPosition = posOfSpecificPiece.get(currPieceCount);
                pieceExists = true;
            } else {
                pieceExists = false;
            }
            currPieceCount++;
            currDirectionsIndex = 0;
        }
    }
    private void getPieceToPrachute(){
        pieceToParachute = new Vector<>();
        for (Map.Entry<String, Integer> entry : capturedPieceBlackHM.entrySet()) {
            String pieceClassName = entry.getKey();
            int count = entry.getValue();
            if (count > 0) {
                ShogiPiece matchingPiece = pieces.stream()
                        .filter(p -> Objects.equals(p.getClass().getCanonicalName(), pieceClassName))
                        .findFirst()
                        .orElse(null);

                if (matchingPiece != null) {
                    for (int i = 0; i < count; i++) {
                        pieceToParachute.add(matchingPiece);
                    }
                }
            }
        }
    }
    public boolean genMove(){
        if(currListIndex == 0){
            getNewPieceFromList();
            getNewPieceFromBoard();
            //getPieceToPrachute();
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

                int absID = Math.abs(pieceToGenMoveFrom.getID());
                if(promote){
                    promotionStateMap.removePromotedPosition(startPos);
                    promotionStateMap.promotePiece(endPos);
                }
                else if (currMoveToReturn.checkIfShouldBePromoted() && (absID != 127 && absID != 11 && absID != 10)) {
                    promotionStateMap.promotePiece(endPos);
                }

                if(originalTraget != 0){
                    ShogiPiece capturedPiece = piecesObj.get(originalTraget);
                    if(Math.abs(capturedPiece.getID()) != 127){
                        int qte = capturedPieceBlackHM.get(capturedPiece.getClass().getCanonicalName()) + 1;
                        capturedPieceBlackHM.put(capturedPiece.getClass().getCanonicalName(), qte);
                    }
                }
            }
            currDirectionsIndex++;
        }
        else{
            getNewPieceFromList();
            getNewPieceFromBoard();
            return (currListIndex - 1) != pieces.size();
        }

        //if(currListIndex == pieces.size() && !pieceToParachute.isEmpty()){
        //    // Start testing parachuting here
        //}
        return (currListIndex != pieces.size() || pieceToGenMoveFrom.getDIRECTIONS().length != currDirectionsIndex);
    }

    public PromotionState getPromotionStateMap() {
        return promotionStateMap;
    }

    public LinkedHashMap<String, Integer> getCapturedPieceBlackHM() {
        return capturedPieceBlackHM;
    }
}
