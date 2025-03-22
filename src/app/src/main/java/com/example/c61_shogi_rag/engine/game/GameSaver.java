package com.example.c61_shogi_rag.engine.game;

import com.google.gson.Gson;

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
