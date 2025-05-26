package com.example.c61_shogi_rag.engine.piece;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Chevalier;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Lance;
import com.example.c61_shogi_rag.engine.piece.ShogiPieces.Pion;
/**
 * Nom du fichier : MoveUtils.java
 * Description : Ce fichier définit une classe utilitaire contenant des méthodes
 *               permettant de vérifier les déplacements des pièces de Shogi sur le plateau,
 *               incluant les mouvements en diagonale, en ligne droite et les parachutages de pièces.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public class MoveUtils {
    public static boolean checkCross(int finalX, int finalY, int currX, int currY, Board board, int[][] directions){
        int displacementXDragon = (finalX != currX) ? (finalX > currX ? 1 : -1) : 0;
        int displacementYDragon = (finalY != currY) ? (finalY > currY ? 1 : -1) : 0;

        boolean pieceColor = board.getPieceAt(new Position(currX, currY)) > 0;

        while (currX != finalX || currY != finalY) {
            int previousX = currX;
            int previousY = currY;
            currX += displacementXDragon;
            currY += displacementYDragon;

            boolean targetPieceColor = board.getPieceAt(new Position(currX, currY)) > 0;
            if(pieceColor == targetPieceColor && board.getPieceAt(new Position(currX, currY)) != 0){
                return false;
            }

            if ((!checkSteps((currX - previousX), (currY - previousY), directions))) {
                return false;
            } else if ((currX != finalX || currY != finalY) && board.getPieceAt(new Position(currX, currY)) != 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkDiagonals(int finalX, int finalY, int currX, int currY, Board board, int[][] directions){
        if(Math.abs(finalX - currX) == Math.abs(finalY - currY)){
            int displacementX = finalX > currX ? 1 : -1;
            int displacementY = finalY > currY ? 1 : -1;

            boolean pieceColor = board.getPieceAt(new Position(currX, currY)) > 0;

            while(currX != finalX && currY != finalY){
                int previousX = currX;
                int previousY = currY;
                currX += displacementX;
                currY += displacementY;

                boolean targetPieceColor = board.getPieceAt(new Position(currX, currY)) > 0;
                if(pieceColor == targetPieceColor && board.getPieceAt(new Position(currX, currY)) != 0){
                    return false;
                }

                if ((!checkSteps((currX - previousX), (currY - previousY), directions))) {
                    return false;
                } else if (currX != finalX && currY != finalY && board.getPieceAt(new Position(currX, currY)) != 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean checkSteps(int deltaX, int deltaY, int[][] directions){
        int tempX = 0, tempY = 0;
        for (int[] direction : directions) {
            for (int j = 0; j < direction.length; j++) {
                if (j == 0) {
                    tempX = direction[j];
                } else if (j == 1) {
                    tempY = direction[j];
                }
            }
            if (deltaY == tempX && deltaX == tempY) {
                return true;
            }
        }
        return false;
    }
}
