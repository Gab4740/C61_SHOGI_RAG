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

        //changer la couleur du joueur car hardcoder
        Vector<Position> playerPieces = board.getPositionFromColor(false);

        for(Position pos : playerPieces){

            // remplacer board.getPieceAt(pos); par la hash

            /*
            * faire en sorte que a chaque piece recuperer sur le board on
            * verifier dans la hasmap si la piece et Promue ou non si oui get sa valeur promote
            * */

            byte pieceValue = board.getPieceAt(pos);

            if (Boolean.TRUE.equals(promotionStateMap.get(pos.getPosX() + "-" + pos.getPosY()))){
                pieceValue = piece.get(pieceValue).getID_PROMU();
            }

            if (pieceValue == piece.get(pieceValue).getID()){
                pionExiste = true;
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

        return totalEvalCenter;

    }

    //TODO rajouter les autres methodes d'evaluations


}
