package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.PieceIDs;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
/**
 * Nom du fichier : Evaluation.java
 * Description : Ce fichier définit une classe permettant d'évaluer diverses stratégies dans l'algorithme Minimax,
 *               notamment le matériel, la sécurité du roi, la formation de châteaux et le contrôle du centre du plateau.
 * Auteur : Gabriel Veilleux, Arslan Khaoua
 * Entête générée par Copilot
 */
public class Evaluation {
    private static final int MATERIAL_EVAL_ADJUST = 100;
    private static final int CASTLING_ADJUST = 150;
    private static final int KING_SAFETY_ADJUST = 200;
    private static final int DISTANCE_TO_KING_ADJUST = 120;
    private static final int CONTROL_CENTER_ADJUST = 80;

    private static final int[][] POSITION_ADJACENTE = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};

    private static final int[][] FOU_DIRECTION = {{-1,-1},{-1,1},{1,-1},{1,1}};

    private static final int[][] CHAR_DIRECTION = {{0,-1},{0,1},{-1,0},{1,0}};

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

    public int cotroleCenter(Board board){

        int totalEvalCenter = 0;

        int centerX = 4;
        int centerY = 4;

        Vector<Position> playerPieces = board.getPositionFromColor(false);

        for(Position pos: playerPieces){

            int distanceFromCenter = Math.abs(pos.getPosX()- centerX) + Math.abs(pos.getPosY()-centerY);

            totalEvalCenter += (5 - distanceFromCenter);

        }

        return totalEvalCenter;
    }

    public static int evaluateCastling(Board board){
        Position kingPos = findKingPosition(board, false);

        if (kingPos == null){
            return 0;
        }
        return checkYaguraCastle(board, kingPos) + checkAnagumaCastle(board, kingPos);
    }

    private static int checkYaguraCastle(Board board, Position kingPos){

        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();

        if (kx > 2) {
            return 0;
        }

        int score = 0;
        int goldCount = 0;
        int silverCount = 0;
        int boardSize = board.getBOARD_SIZE();

        for (int[] pos : POSITION_ADJACENTE){
            int x = kx + pos[0];
            int y = ky + pos[1];

            if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                if (pieceValue < 0) {
                    if (pieceValue == -PieceIDs.GeneralOr.getValue()) {
                        goldCount++;
                    } else if (pieceValue == -PieceIDs.GeneralArgent.getValue()) {
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

    private static int checkAnagumaCastle(Board board, Position kingPos){

        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();

        if (kx <= 1 && (ky <= 1 || ky >= 7)){
            return 0;
        }

        int score = 0;
        int nbrPieceAutour = 0;
        int boardSize = board.getBOARD_SIZE();

        for (int[] pos : POSITION_ADJACENTE){
            int x = kx + pos[0];
            int y = ky + pos[1];

            if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                if (pieceValue < 0) {
                    nbrPieceAutour++;
                    score += 5;

                    if (pieceValue == -PieceIDs.GeneralOr.getValue() ||
                            pieceValue == -PieceIDs.GeneralArgent.getValue()) {
                        score += 5;
                    }
                }
            }
        }
        if (nbrPieceAutour >= 3){
            score += 50;
        } else if (nbrPieceAutour == 2) {
            score += 25;
        }
        return score;
    }

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

    public static int evaluateKingSafety(Board board){
        Position kingPos = findKingPosition(board, false);

        if (kingPos == null){
            return 0;
        }
        return calculeAllKingSafetyMethode(board, kingPos) ;
    }

    private static int calculeAllKingSafetyMethode(Board board, Position kingPos){
        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();
        int boardSize = board.getBOARD_SIZE();

        int thicknessScore = 0;
        int escapeRouteScore = 0;
        int nbrPieceAutour = 0;
        int nbrEscapeRoute = 0;

        for (int[] pos : POSITION_ADJACENTE){
            int x = kx + pos[0];
            int y = ky + pos[1];

            if (x >= 0 && x < boardSize && y >= 0 && y < boardSize) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                if (pieceValue < 0){
                    nbrPieceAutour++;
                    thicknessScore += 10;

                    if (pieceValue == -PieceIDs.GeneralOr.getValue() ||
                            pieceValue == -PieceIDs.GeneralArgent.getValue()) {
                        thicknessScore += 5;
                    }
                }
                else {
                    nbrEscapeRoute++;

                    if (pieceValue == 0){
                        escapeRouteScore += 10;
                    } else {
                        escapeRouteScore += 5;
                    }
                }
            }
        }

        if (nbrPieceAutour >= 6){
            thicknessScore += 30;
        } else if (nbrPieceAutour >= 4){
            thicknessScore += 20;
        } else if (nbrPieceAutour <= 1){
            thicknessScore -= 30;
        }

        if (nbrEscapeRoute >= 4){
            escapeRouteScore += 10;
        } else if (nbrEscapeRoute >= 2){
            escapeRouteScore += 5;
        } else if (nbrEscapeRoute == 0){
            escapeRouteScore -= 30;
        }

        int enteringScore = (ky >= 5) ? 10 * (ky - 4) : 0;
        return thicknessScore + escapeRouteScore + enteringScore;
    }

    private static int checkThinckeness(Board board, Position kingPos){
        int score = 0;

        int kx = kingPos.getPosX();
        int ky = kingPos.getPosY();

        int nbrPieceAutour = 0;

        for (int[] pos : POSITION_ADJACENTE){
            int x = kx + pos[0];
            int y = ky + pos[1];

            if (x >= 0 && x < board.getBOARD_SIZE() && y >= 0 && y < board.getBOARD_SIZE()) {
                byte pieceValue = board.getPieceAt(new Position(x, y));

                if (pieceValue < 0){
                    nbrPieceAutour++;
                    score += 10;

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

                if (pieceValue == 0 || pieceValue > 0){
                    nbrEscapeRoute++;

                    if (pieceValue == 0){
                        score += 10;
                    }else {
                        score += 5;
                    }
                }
            }
        }

        if (nbrEscapeRoute >= 4){
            score += 10;
        }else if (nbrEscapeRoute >= 2){
            score += 5;
        }else if (nbrEscapeRoute == 0){
            score -= 30;
        }
        return score ;
    }

    private static int evaluateEnteringKing(Board board, Position kingPos){
        int score = 0;
        int ky = kingPos.getPosY();

        if (ky >= 5){
            score += 10 * (ky - 4);
        }
        return score;
    }

    public static int evaluateDistanceToTheKing(Board board, Hashtable<Byte, ShogiPiece> piece){

        Position kingIa = findKingPosition(board, false);
        if(kingIa == null){
            return 0;
        }
        Position kingPlayer = findKingPosition(board, true);
        if(kingPlayer == null){
            return 10000000; // AI A MANGER LE ROI.
        }

        int kingIaX = kingIa.getPosX();
        int kingIaY = kingIa.getPosY();
        int kingPlayerX = kingPlayer.getPosX();
        int kingPlayerY = kingPlayer.getPosY();

        Vector<Position> piecesIa = board.getPositionFromColor(false);
        int score = 0;

        for (Position pos : piecesIa){
            byte pieceValue = board.getPieceAt(pos);

            if (pieceValue == -PieceIDs.Roi.getValue()){
                continue;
            }

            int posX = pos.getPosX();
            int posY = pos.getPosY();

            int distanceToEnnemyKing = Math.abs(kingPlayerX - posX) + Math.abs(kingPlayerY - posY);
            int distanceToKingIa = Math.abs(kingIaX - posX) + Math.abs(kingIaY - posY);

            int absPieceValue = Math.abs(pieceValue);

            if (distanceToEnnemyKing <= 2){
                score += absPieceValue * 10;
            } else if (distanceToEnnemyKing <= 4){
                score += absPieceValue * 5;
            } else {
                score -= absPieceValue * 2;
            }

            if (distanceToKingIa <= 2){
                score += absPieceValue * 3;
            }
        }

        return score;
    }

    public static int evaluateControlleSquares(Board board, Hashtable<Byte, ShogiPiece> pieceTable) {
        int score = 0;
        byte[][] boardArray = board.getBoard();
        int boardSize = board.getBOARD_SIZE();

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                byte piece = boardArray[y][x];
                if (piece == 0) continue;

                ShogiPiece pieceObj = pieceTable.get(piece);
                if (pieceObj == null) continue;

                for (int[] direction : pieceObj.getDIRECTIONS()) {
                    int newX = x + direction[0];
                    int newY = y + direction[1];

                    if (newX < 0 || newX >= boardSize || newY < 0 || newY >= boardSize) {
                        continue;
                    }

                    byte target = boardArray[newY][newX];

                    if (piece > 0 && target <= 0) {
                        score -= 1;
                    } else if (piece < 0 && target >= 0) {
                        score += 1;
                    }
                }
            }
        }
        return score;
    }

    private static int countControllingPiecesFast(Board board, Position newPos, List<Position> piecesPos, Hashtable<Byte, ShogiPiece> pieces) {
        int count = 0;

        for (Position pos : piecesPos) {
            if (pos.equals(newPos)) continue;

            byte pieceValue = board.getPieceAt(pos);
            ShogiPiece shogiPiece = pieces.get(pieceValue);
            if (shogiPiece == null) continue;

            Move tempMove = new Move(pos, newPos);

            if (shogiPiece.isValidMove(tempMove, board)) {
                count++;
            }
        }

        return count;
    }
}
