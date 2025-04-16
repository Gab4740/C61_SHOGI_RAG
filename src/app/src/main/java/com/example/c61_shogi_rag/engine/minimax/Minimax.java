package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class Minimax {
    private final EvaluationStrategies difficulty;
    private Vector<ShogiPiece> pieces;
    private Hashtable<Byte, ShogiPiece> piecesObj;
    private int leafCounter;
    private Random rand;
    public Minimax(Vector<ShogiPiece> pieces, Hashtable<Byte, ShogiPiece> piecesObj, EvaluationStrategies difficulty){
        this.pieces = pieces;
        this.leafCounter = 0;
        this.rand = new Random();
        this.piecesObj = piecesObj;
        this.difficulty = difficulty;
    }

    /**
     * Méthode récursive pour rechercher dans l'arbre de recherche, Algorithme Minimax
     * @return : Le score de l'échiquier a jouer comme prochain tour
     *
     * @param board : État de l'échiquier de la partie courante,
     * @param depth : Profondeur de la recherche. (niveau de l'arbre),
     * @param alpha : Valeur treshold minimum (-infini),
     * @param beta : Valeur treshold maximum (+infini),
     * @param maximizingPlayer : Boolean qui indique pour quel joueur : (True : AI, False : Joueur)
     * */
    public MoveScore minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, PromotionState promotions){
        if (depth == 0){
            leafCounter++;
            return new MoveScore(difficulty.evaluate(board, promotions, piecesObj), null); // Evaluation(board, moveGenerator.getPromotionStateMap());
        }

        MoveGeneration moveGenerator = new MoveGeneration(pieces, board, promotions, piecesObj);

        if(maximizingPlayer){
            MoveScore maxEval = new MoveScore(Integer.MIN_VALUE, null);
            MoveManager move;
            while(moveGenerator.genMove()){
                move = moveGenerator.getCurrMoveToReturn();
                if(move != null){
                    move.do_move_on_board(board);
                    MoveScore eval = minimax(board, depth - 1, alpha, beta, false, promotions);
                    move.undo_move_on_board(board);

                    if(eval.getScore() > maxEval.getScore()){
                        maxEval.setScore(eval.getScore());
                        maxEval.setMove(move);
                    }
                    alpha = Math.max(alpha, eval.getScore());
                    if (beta <= alpha){
                        break;
                    }
                }
            }
            return maxEval;
        }
        else{
            MoveScore minEval = new MoveScore(Integer.MAX_VALUE, null);
            MoveManager move;
            while(moveGenerator.genMove()){
                move = moveGenerator.getCurrMoveToReturn();
                if(move != null) {
                    move.do_move_on_board(board);
                    MoveScore eval = minimax(board, depth - 1, alpha, beta, true, promotions);
                    move.undo_move_on_board(board);

                    if(eval.getScore() < minEval.getScore()){
                        minEval.setScore(eval.getScore());
                        minEval.setMove(move);
                    }
                    beta = Math.min(beta, eval.getScore());
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }


    public int getLeafCounter() {
        return leafCounter;
    }
}