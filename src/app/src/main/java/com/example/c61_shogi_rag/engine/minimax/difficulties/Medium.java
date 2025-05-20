package com.example.c61_shogi_rag.engine.minimax.difficulties;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.minimax.Evaluation;
import com.example.c61_shogi_rag.engine.minimax.EvaluationStrategies;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;
/**
 * Nom du fichier : Medium.java
 * Description : Ce fichier définit la classe représentant la stratégie d'évaluation "Moyenne"
 *               pour l'algorithme Minimax, basée sur l'évaluation du matériel des pièces présentes sur le plateau.
 * Auteur : Gabriel Veilleux, Arslan Khaoua
 * Entête générée par Copilot
 */
public class Medium extends EvaluationStrategies {
    public Medium(int difficulty) {
        super(difficulty);
    }
    @Override
    public int evaluate(Board board, PromotionState promotions, Hashtable<Byte, ShogiPiece> piece) {
        return Evaluation.material_eval(board, promotions, piece);
    }
}
