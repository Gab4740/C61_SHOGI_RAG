package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;

public abstract class EvaluationStrategies {
    private int difficulty;

    public EvaluationStrategies(int difficulty) {
        this.difficulty = difficulty;
    }

    public abstract int evaluate(Board board, PromotionState promotions, Hashtable<Byte, ShogiPiece> piece);
}
