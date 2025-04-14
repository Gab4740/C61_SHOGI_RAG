package com.example.c61_shogi_rag.engine.minimax.difficulties;

import com.example.c61_shogi_rag.engine.game.Board;
import com.example.c61_shogi_rag.engine.game.PromotionState;
import com.example.c61_shogi_rag.engine.minimax.Evaluation;
import com.example.c61_shogi_rag.engine.minimax.EvaluationStrategies;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Hashtable;

public class Medium extends EvaluationStrategies {
    public Medium(int difficulty) {
        super(difficulty);
    }
    @Override
    public int evaluate(Board board, PromotionState promotions, Hashtable<Byte, ShogiPiece> piece) {
        return Evaluation.material_eval(board, promotions, piece);
    }
}
