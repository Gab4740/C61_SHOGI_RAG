package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.minimax.difficulties.Easy;
import com.example.c61_shogi_rag.engine.minimax.difficulties.Hard;
import com.example.c61_shogi_rag.engine.minimax.difficulties.Medium;
/**
 * Nom du fichier : Difficulty.java
 * Description : Ce fichier définit une énumération des niveaux de difficulté pour l'algorithme Minimax,
 *               associant chaque niveau à une stratégie d'évaluation spécifique.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
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
