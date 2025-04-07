package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.piece.Position;

import java.util.Vector;

public class Evaluation {
    private static final int MATERIAL_EVAL_ADJUST = 100;
    private static final int CONTROL_CENTER_ADJUST = 100;


    public static int material_eval(Board board) {
        int playerScore = 0;

        //changer la couleur du joueur car hardcoder
        Vector<Position> playerPieces = board.getPositionFromColor(false);

        for(Position pos : playerPieces){
            byte pieceId = board.getPieceAt(pos);

            playerScore += pieceId;
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

        return totalEvalCenter;

    }

    //TODO rajouter les autres methodes d'evaluations


}
