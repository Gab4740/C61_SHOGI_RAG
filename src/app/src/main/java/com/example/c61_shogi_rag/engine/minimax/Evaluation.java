package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.PieceIDs;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

public class Evaluation {
    private static final int MATERIAL_EVAL_ADJUST = 100;
    private static final int CONTROL_CENTER_ADJUST = 100;


    public static int material_eval(Board board, HashMap<String, Boolean> promotionStateMap,
                                    Hashtable<Byte, ShogiPiece> piece) {
        int playerScore = 0;
        boolean pionExiste = false;

        Vector<Position> playerPieces = board.getPositionFromColor(false);

        for(Position pos : playerPieces){

            byte pieceValue = board.getPieceAt(pos);

            if(pieceValue == PieceIDs.Pion.getValue()){
                pionExiste = true;
            }

            if (Boolean.TRUE.equals(promotionStateMap.get(pos.getPosX() + "-" + pos.getPosY()))){
                pieceValue = piece.get(pieceValue).getID_PROMU();
            }

            playerScore += pieceValue;

        }

        //faire en sorte dans l'evaluation si il n'y a pas de pion
        // sur le terrain mettre une valeur negatif pour baisser l'evaluation
        if (!pionExiste){
            playerScore -= 50; // a changer
        }

        return (1 / playerScore) * MATERIAL_EVAL_ADJUST;
    }


    public int controleCenter(Board board){

        int totalEvalCenter = 0;

        int centerX = 4;
        int centerY = 4;

        Vector<Position> playerPieces = board.getPositionFromColor(true);

        for(Position pos: playerPieces){

            int distanceFromCenter = Math.abs(pos.getPosX()- centerX) + Math.abs(pos.getPosY()-centerY);

            totalEvalCenter += (5 - distanceFromCenter);

        }

        //rajouter un valeur pour ajuster l'evaluation
        return totalEvalCenter;

    }

    public static int evaluateCastling(Board board){
        int castlingScore = 0;

        Position kingPos = findKingPosition(board);

        if (kingPos == null){
            return 0;
        }

        castlingScore += checkYaguraCastle(board, kingPos);
        castlingScore += checkAnagumaCastle(board, kingPos);

        return castlingScore;

    }

    private static int checkYaguraCastle(Board board, Position kingPos){

        int score = 0;
        int goldCount = 0;
        int silverCount = 0;

        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();

        //check si le roi est assez haut pour faire la formation
        if (kx > 2) {
            return 0;
        }

        int[][] pieceACoter = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        //verification de la position des pieces a cote du roi
        for (int[] pos : pieceACoter){
            int x = kx + pos[0];
            int y = ky + pos[1];

            if (x >= 0 && x < board.getBOARD_SIZE() && y >= 0 && y < board.getBOARD_SIZE()) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                if (pieceValue == PieceIDs.GeneralOr.getValue()) {
                    goldCount++;
                } else if (pieceValue == PieceIDs.GeneralArgent.getValue()) {
                    silverCount++;
                }

                score += 5;
            }
        }

        if (goldCount >=2 && silverCount >= 1){
            score += 30;
        } else if (goldCount >= 1 && silverCount >= 1) {
            score += 15;
        }

        return score;
    }

    private static int checkAnagumaCastle(Board board, Position kingPos){

        int score = 0;
        int nbrPieceAutour = 0;

        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();

        //check si le roi est dans un coin
        if (kx <= 1 && (ky <= 1 || ky >= 7)){
            return 0;
        }

        int[][] pieceACoter = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        //verification si y'a des piece autour du roi
        for (int[] pos : pieceACoter){
            int x = kx + pos[0];
            int y = ky + pos[1];

            if (x >= 0 && x < board.getBOARD_SIZE() && y >= 0 && y < board.getBOARD_SIZE()) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                nbrPieceAutour++;
                score += 5;

                //rajout d'un bonus si il y a un general
                if (pieceValue == PieceIDs.GeneralOr.getValue() ||
                        pieceValue == PieceIDs.GeneralArgent.getValue()) {
                    score += 5;
                }
            }
        }

        if (nbrPieceAutour >= 3){
            score += 50;
        } else if (nbrPieceAutour >= 2) {
            score += 25;
        }

        return score;
    }

    private static Position findKingPosition(Board board){
        Vector<Position> piece = board.getPositionFromColor(false);
        byte kingValue = (byte) PieceIDs.Roi.getValue();

        for (Position pos: piece){
            if (board.getPieceAt(pos) == kingValue){
                return pos;
            }
        }
        return null;
    }



    //TODO rajouter les autres methodes d'evaluations


}
