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

        int x = pos.getPosX();
        int y = pos.getPosY();

        int centerX = 4;
        int centerY = 4;

        int distanceFromCenter = Math.abs(x- centerX) + Math.abs(y-centerY);

        if (distanceFromCenter > 4){
            return 0;
        }

        return 5 - distanceFromCenter;

    }

    //TODO rajouter les autres methodes d'evaluations


}
