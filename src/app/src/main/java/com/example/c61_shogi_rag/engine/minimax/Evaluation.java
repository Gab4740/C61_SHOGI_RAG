package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.PieceIDs;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

public class Evaluation {

    //rajouter d'autre adjust
    private static final int MATERIAL_EVAL_ADJUST = 100;
    private static final int CASTLING_ADJUST = 150;
    private static final int KING_SAFETY_ADJUST = 200;
    private static final int DISTANCE_TO_KING_ADJUST = 120;
    private static final int CONTROL_CENTER_ADJUST = 80;


    public static int material_eval(Board board, PromotionState promotionStateMap, Hashtable<Byte, ShogiPiece> piece) {
        int totalScore = 0;
        byte[][] b = board.getBoard();
        for(int i = 0; i < board.getBOARD_SIZE(); i++){
            for(int j = 0; j < board.getBOARD_SIZE(); j++){
                totalScore -= b[i][j];
            }
        }
        return totalScore * MATERIAL_EVAL_ADJUST;
    }

    //========================================================================================================

    public int controleCenter(Board board){

        int totalEvalCenter = 0;

        int centerX = 4;
        int centerY = 4;

        Vector<Position> playerPieces = board.getPositionFromColor(false);

        for(Position pos: playerPieces){

            int distanceFromCenter = Math.abs(pos.getPosX()- centerX) + Math.abs(pos.getPosY()-centerY);

            totalEvalCenter += (5 - distanceFromCenter);

        }

        //rajouter un valeur pour ajuster l'evaluation
        return totalEvalCenter;

    }

    //========================================================================================================

    //evaluation du castling
    public static int evaluateCastling(Board board){
        int castlingScore = 0;

        Position kingPos = findKingPosition(board, false);

        if (kingPos == null){
            return 0;
        }

        castlingScore += checkYaguraCastle(board, kingPos);
        castlingScore += checkAnagumaCastle(board, kingPos);

        return castlingScore;
    }

    //check si il y a assez de piece pour faire la formation Yagura autour du rois
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

                if (pieceValue < 0) {
                    if (pieceValue == PieceIDs.GeneralOr.getValue()) {
                        goldCount++;
                    } else if (pieceValue == PieceIDs.GeneralArgent.getValue()) {
                        silverCount++;
                    }

                    score += 5;
                }
            }
        }

        if (goldCount >=2 && silverCount >= 1){
            score += 30;
        } else if (goldCount >= 1 && silverCount >= 1) {
            score += 15;
        }

        return score;
    }

    //check si il y a assez de piece pour faire la formation Anaguma autour du rois
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

                //check si piece allier
                if (pieceValue < 0) {
                    nbrPieceAutour++;
                    score += 5;

                    //rajout d'un bonus si il y a un general
                    if (pieceValue == PieceIDs.GeneralOr.getValue() ||
                            pieceValue == PieceIDs.GeneralArgent.getValue()) {
                        score += 5;
                    }

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

    //fonction pour trouver la position du roi de L'ia
    private static Position findKingPosition(Board board, boolean color){
        int adjust = color ? 1 : -1;
        byte kingValue = (byte) (PieceIDs.Roi.getValue() * adjust);

        byte[][] b = board.getBoard();
        for(int i = 0; i < board.getBOARD_SIZE(); i++){
            for(int j = 0; j < board.getBOARD_SIZE(); j++){
                if(b[i][j] == kingValue){
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    //========================================================================================================

    //evaluation de la safety du roi
    public static int evaluateKingSafety(Board board){
        Position kingPos = findKingPosition(board, false);

        int score = 0;
        if (kingPos == null){
            return score;
        }
        score += checkThinckeness(board, kingPos);
        score += checkEscapeRoute(board, kingPos);
        score += evaluateEnteringKing(board, kingPos);

        return score ;
    }

    //check le nombre de piece allie autoure du rois pour evaluer sa protection
    private static int checkThinckeness(Board board, Position kingPos){
        int score = 0;

        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();

        int[][] pieceACoter = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        int nbrPieceAutour = 0;

        for (int[] pos : pieceACoter){
            int x = kx + pos[0];
            int y = ky + pos[1];

            if (x >= 0 && x < board.getBOARD_SIZE() && y >= 0 && y < board.getBOARD_SIZE()) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                //pour voir si piece allier
                if (pieceValue < 0){
                    nbrPieceAutour++;
                    score += 10;

                    //rajout d'un bonus si il y a un general qui protege le roi
                    if (pieceValue == PieceIDs.GeneralOr.getValue() ||
                            pieceValue == PieceIDs.GeneralArgent.getValue()) {
                        score += 5;
                    }
                }
            }
        }


        if (nbrPieceAutour >= 6){
            score += 30;
        }
        else if (nbrPieceAutour >= 4){
            score += 20;
        }
        else if (nbrPieceAutour <= 1){
            score -= 30;
        }



        return score;
    }

    //regarde les cases autoure du roi plus il y a case vide mieux c'est, si il y a piece ennemy le rois peut le manger mais plus dangereux
    private static int checkEscapeRoute(Board board, Position kingPos){
        int score = 0;

        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();

        int[][] pieceACoter = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

        int nbrEscapeRoute = 0;

        for (int[] pos : pieceACoter){
            int x = kx + pos[0];
            int y = ky + pos[1];


            if (x >= 0 && x < board.getBOARD_SIZE() && y >= 0 && y < board.getBOARD_SIZE()) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                //si case vide ou piece ennemy capturable
                if (pieceValue == 0 || pieceValue > 0){
                    nbrEscapeRoute++;

                    // si case vide
                    if (pieceValue == 0){
                        score += 10;
                    }else {
                        //si case avec piece ennemy
                        score += 5;
                    }
                }
            }
        }

        if (nbrEscapeRoute >= 4){
            score += 10;
        }else if (nbrEscapeRoute >= 2){
            score += 5;
        }else if (nbrEscapeRoute == 0){ //si zero route pas bon car king peut pas partir en cas de danger
            score -= 30;
        }

        return score ;

    }

    //regarde si le roi ce trouve dans le camps ennemy, plus le roi est loin dans le camps mieux c'est
    private static int evaluateEnteringKing(Board board, Position kingPos){
        int score = 0;
        int ky = kingPos.getPosY();

        if (ky >= 5){
            score += 10 * (ky - 4);
        }

        return score;
    }

    //========================================================================================================


    public static int evaluateDistanceToTheKing(Board board, Hashtable<Byte, ShogiPiece> piece){
        int score = 0;

        Position kingIa = findKingPosition(board, false);
        if(kingIa == null){
            return 0;
        }
        Position kingPlayer = findKingPosition(board, true);
        if(kingPlayer == null){
            return 10000000; // AI A MANGER LE ROI.
        }

        Vector<Position> piecesIa = board.getPositionFromColor(false);

        for (Position pos : piecesIa){
            byte pieceValue = board.getPieceAt(pos);

            if (pieceValue == PieceIDs.Roi.getValue()){
                continue;
            }

            int disctanceToEnnemyKing = Math.abs(kingPlayer.getPosX() - pos.getPosX()) + Math.abs(kingPlayer.getPosY() - pos.getPosY());

            int disctanceToKingIa =  Math.abs(kingIa.getPosX() - pos.getPosX()) + Math.abs(kingIa.getPosY() - pos.getPosY());

            //plus les pieces sont proche du rois ennemy meilleur sont les point en fonction de l'importance de la piece
            if (disctanceToEnnemyKing <= 2){
                score += pieceValue * 10;
            }else if (disctanceToEnnemyKing <= 4){
                score += pieceValue * 5;
            }else {
                //si piece trop loin du roi ennemy
                score -= pieceValue * 2;
            }

            //si piece proche du rois bien car rois proteger
            if (disctanceToKingIa <= 2){
                score += pieceValue * 3;
            }
        }

        return score ;
    }




    //TODO rajouter les autres methodes d'evaluations


}
