package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.minimax.difficulties.Easy;
import com.example.c61_shogi_rag.engine.minimax.difficulties.Hard;
import com.example.c61_shogi_rag.engine.minimax.difficulties.Medium;

public enum Difficulty {
    Easy(new Easy(0)),
    Medium(new Medium(1)),
    Hard(new Hard(2));
    private final EvaluationStrategies strategy;

    Difficulty(EvaluationStrategies strategy) {
        this.strategy = strategy;
    }
    public EvaluationStrategies getStrategy(){
        return this.strategy;
    }

    public String getStrategyString() {return  this.strategy.toString();}
}
