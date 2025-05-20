package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;
/**
 * Nom du fichier : EvaluationStrategies.java
 * Description : Ce fichier définit une classe abstraite pour les différentes stratégies d'évaluation
 *               utilisées dans l'algorithme Minimax, en fonction du niveau de difficulté.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public abstract class EvaluationStrategies {
    private int difficulty;

    public EvaluationStrategies(int difficulty) {
        this.difficulty = difficulty;
    }

    public abstract int evaluate(Board board, PromotionState promotions, Hashtable<Byte, ShogiPiece> piece);
}
