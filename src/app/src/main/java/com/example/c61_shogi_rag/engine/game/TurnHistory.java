package com.example.c61_shogi_rag.engine.game;

import java.util.ArrayList;

public class TurnHistory {
    private ArrayList<OneTurn> turnsList;

    public TurnHistory() {
        this.turnsList = new ArrayList<OneTurn>();
    }
    public ArrayList<OneTurn> getTurnsList() {
        return turnsList;
    }
}