package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.Position;

import java.util.Vector;

public class Evaluation {


    public static int evaluate(Board board) {
        int playerScore = 0;

        //changer la couleur du joueur car hardcoder
        Vector<Position> playerPieces = board.getPositionFromColor(true);

        for(Position pos : playerPieces){
            byte pieceId = board.getPieceAt(pos);

            playerScore += pieceId;
        }

        return playerScore;
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

        return totalEvalCenter;

    }

    //TODO rajouter les autres methodes d'evaluations


}
