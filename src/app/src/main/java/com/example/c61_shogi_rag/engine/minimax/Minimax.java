package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;

public class Minimax {
    /**
     * Méthode récursive pour rechercher dans l'arbre de recherche, Algorithme Minimax
     * @return : Le score de l'échiquier a jouer comme prochain tour
     *
     * @param node : État de l'échiquier de la partie courante,
     * @param depth : Profondeur de la recherche. (niveau de l'arbre),
     * @param alpha : Valeur treshold minimum (-infini),
     * @param beta : Valeur treshold maximum (+infini),
     * @param maximizingPlayer : Boolean qui indique pour quel joueur : True : AI, False : Joueur
     * */
    public static int minimax(Node node, int depth, int alpha, int beta, boolean maximizingPlayer){
        if (depth == 0){
            return 0; // Evaluation(node.getBoard());
        }
        if(maximizingPlayer){
            int maxEval = Integer.MIN_VALUE;
            for(Node n : node.generateChildren()){
                // APPLY BOARD MOVE
                int eval = minimax(n, depth - 1, alpha, beta, false);
                // UNDO BOARD MOVE
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha){
                    break;
                }
            }
            return maxEval;
        }
        else{
            int minEval = Integer.MAX_VALUE;
            for(Node n : node.generateChildren()){
                // APPLY BOARD MOVE
                int eval = minimax(n, depth - 1, alpha, beta, true);
                // UNDO BOARD MOVE
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if(beta <= alpha){
                    break;
                }
            }
            return minEval;
        }
    }
}
