package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.MoveManager;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Vector;

public class Minimax {
    private Vector<ShogiPiece> pieces;
    private Board board;
    public Minimax(Board board, Vector<ShogiPiece> pieces){
        this.board = board;
        this.pieces = pieces;
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
    public int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer){
        if (depth == 0){
            board.prettyPrintConsoleBoard();
            System.out.println("_________________________________");
            return 0; // Evaluation(board, moveGenerator.getPromotionStateMap());
        }

        MoveGeneration moveGenerator = new MoveGeneration(pieces, board);

        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;
            while(moveGenerator.genMove()){
                MoveManager move = moveGenerator.getCurrMoveToReturn();
                if(move != null){
                    move.do_move_on_board(board);
                    int eval = minimax(board, depth - 1, alpha, beta, false);
                    move.undo_move_on_board(board);


                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha){
                        break;
                    }
                }
            }
            return maxEval;
        }
        else{
            int minEval = Integer.MAX_VALUE;

            while(moveGenerator.genMove()){
                MoveManager move = moveGenerator.getCurrMoveToReturn();
                if(move != null) {
                    move.do_move_on_board(board);
                    int eval = minimax(board, depth - 1, alpha, beta, true);
                    move.undo_move_on_board(board);

                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }
}

// PROBLEM : Lorsque l'appel recursif remonte de 1 niveau, les index dans move generator ne reprenne pas a l'endroit qu'ils étaient avant la reccursivité.