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

    //TODO rajouter les autres methodes d'evaluations


}
