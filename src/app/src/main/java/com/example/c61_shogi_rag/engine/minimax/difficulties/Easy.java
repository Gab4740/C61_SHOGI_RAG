package com.example.c61_shogi_rag.engine.minimax.difficulties;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.minimax.EvaluationStrategies;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;
import java.util.Random;
/**
 * Nom du fichier : Easy.java
 * Description : Ce fichier définit la classe représentant la stratégie d'évaluation "Facile"
 *               pour l'algorithme Minimax, générant des évaluations de manière aléatoire.
 * Auteur : Gabriel Veilleux, Arslan Khaoua
 * Entête générée par Copilot
 */
public class Easy extends EvaluationStrategies {
    private Random rand = new Random();
    public Easy(int difficulty) {
        super(difficulty);
    }
    @Override
    public int evaluate(Board board, PromotionState promotions, Hashtable<Byte, ShogiPiece> piece) {
        return rand.nextInt(100);
    }
}
