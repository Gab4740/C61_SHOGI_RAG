package com.example.c61_shogi_rag.engine.minimax.difficulties;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.minimax.EvaluationStrategies;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;
import java.util.Random;

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
