package com.example.c61_shogi_rag.engine.game;

import java.util.ArrayList;
/**
 * Nom du fichier : TurnHistory.java
 * Description : Ce fichier définit une classe permettant de conserver l'historique des tours joués
 *               dans une partie de Shogi, en les stockant dans une liste.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class TurnHistory {
    private ArrayList<OneTurn> turnsList;

    public TurnHistory() {
        this.turnsList = new ArrayList<OneTurn>();
    }
    public ArrayList<OneTurn> getTurnsList() {
        return turnsList;
    }
}