package com.example.c61_shogi_rag.engine.game;

/**
 * Nom du fichier : GameSaver.java
 * Description : Ce fichier définit une classe permettant de sauvegarder l'historique des tours joués
 *               dans une partie de Shogi, en les ajoutant à une liste de tours.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class GameSaver {
    private TurnHistory turnList;

    public GameSaver() {
        this.turnList = new TurnHistory();
    }
    public void addNewTurn(OneTurn turn){
        this.turnList.getTurnsList().add(turn);
    }
    public TurnHistory getTurnList() {
        return turnList;
    }
}
