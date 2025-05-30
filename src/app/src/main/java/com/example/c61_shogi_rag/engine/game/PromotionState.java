package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.piece.Position;

import java.util.HashMap;
/**
 * Nom du fichier : PromotionState.java
 * Description : Ce fichier définit une classe permettant de gérer l'état de promotion des pièces de Shogi,
 *               assurant le suivi des positions promues et la possibilité de promotion pour les pièces du jeu.
 * Auteur : Gabriel Veilleux
 * Entête générée par Copilot
 */
public class PromotionState {
    private HashMap<String, Boolean> internalMap;

    public PromotionState(HashMap<String, Boolean> inputMap) {
        this.internalMap = new HashMap<>(inputMap);
    }

    @Override
    public PromotionState clone() {
        return new PromotionState(new HashMap<>(this.internalMap));
    }

    public HashMap<String, Boolean> getMap() {
        return this.internalMap;
    }

    private String getPositionKey(Position pos) {
        int row = pos.getPosX();
        int col = pos.getPosY();
        return row + "-" + col;
    }
    public Position getPostionFromKey(String key) {
        String[] parts = key.split("-");
        int num1 = Integer.parseInt(parts[0]);
        int num2 = Integer.parseInt(parts[1]);
        return new Position(num1, num2);
    }
    public void promotePiece(Position pos) {
        String positionKey = getPositionKey(pos);
        internalMap.put(positionKey, true);
    }
    public void removePromotedPosition(Position pos){
        String key = getPositionKey(pos);
        internalMap.remove(key);
    }
    public boolean isPiecePromoted(Position pos){
        String key = getPositionKey(pos);
        if(internalMap.containsKey(key)){
            return internalMap.get(key);
        }
        return false;
    }
    public void shouldPlayerPiecePromote(Position pos){
        if(pos.getPosX() <= 2){
            promotePiece(pos);
        }
    }
    public boolean canPiecePromote(Position pos){
        return !this.isPiecePromoted(pos) && pos.getPosX() <= 2;
    }
}
