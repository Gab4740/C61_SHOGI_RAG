package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.MoveManager;
/**
 * Nom du fichier : MoveScore.java
 * Description : Ce fichier définit une classe représentant un score associé à un mouvement dans l'algorithme Minimax,
 *               permettant d'évaluer et de comparer les coups jouables dans le jeu de Shogi.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class MoveScore {
    private int score;
    private MoveManager move;

    public MoveScore(int score, MoveManager move) {
        this.score = score;
        this.move = move;
    }

    public int getScore() {
        return score;
    }
    public MoveManager getMove() {
        return move;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setMove(MoveManager move) {
        this.move = move;
    }
}
