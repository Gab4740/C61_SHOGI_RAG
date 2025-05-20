package com.example.c61_shogi_rag.engine.minimax.difficulties;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.minimax.Evaluation;
import com.example.c61_shogi_rag.engine.minimax.EvaluationStrategies;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;
/**
 * Nom du fichier : Hard.java
 * Description : Ce fichier définit la classe représentant la stratégie d'évaluation "Difficile"
 *               pour l'algorithme Minimax, intégrant plusieurs critères d'analyse tels que
 *               la sécurité du roi, le matériel et le contrôle des cases du plateau.
 * Auteur : Gabriel Veilleux, Arslan Khaoua
 * Entête générée par Copilot
 */
public class Hard extends EvaluationStrategies {
    public Hard(int difficulty) {
        super(difficulty);
    }

    @Override
    public int evaluate(Board board, PromotionState promotions, Hashtable<Byte, ShogiPiece> pieces) {
        int score = 0;
        score += Evaluation.evaluateCastling(board);
        score += Evaluation.evaluateKingSafety(board);
        score += Evaluation.evaluateDistanceToTheKing(board, pieces);
        score += Evaluation.material_eval(board, promotions, pieces);
         /** NE PAS DECOMMENTER POUR L'INSTANT A PART SI TU VEUT QUE L'IA NE JOUE JAMAIS **/
        //score += Evaluation.evaluateControlleSquares(board, pieces);

        return score;
    }
}