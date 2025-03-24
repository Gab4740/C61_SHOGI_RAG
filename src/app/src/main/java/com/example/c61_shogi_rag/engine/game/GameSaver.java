package com.example.c61_shogi_rag.engine.game;


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

    public void setTurnList(TurnHistory turnList) {
        this.turnList = turnList;
    }
}
