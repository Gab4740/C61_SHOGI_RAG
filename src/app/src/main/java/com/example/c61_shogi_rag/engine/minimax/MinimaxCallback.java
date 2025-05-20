package com.example.c61_shogi_rag.engine.minimax;

import com.example.c61_shogi_rag.engine.game.MoveManager;
/**
 * Nom du fichier : MinimaxCallback.java
 * Description : Ce fichier définit une interface permettant d'exécuter un rappel
 *               lors du calcul d'un mouvement dans l'algorithme Minimax.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */

public interface MinimaxCallback {
    void onMoveComputed(MoveManager move);
}