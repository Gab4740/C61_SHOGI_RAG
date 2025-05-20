package com.example.c61_shogi_rag.engine.piece;
/**
 * Nom du fichier : Position.java
 * Description : Ce fichier définit une classe représentant la position d'une pièce sur le plateau de jeu de Shogi,
 *               facilitant l'accès aux coordonnées X et Y ainsi que leur manipulation.
 * Auteur(s) : [Gabriel Veilleux]
 * Entête générée par Copilot
 */
public class Position {
    private final int posX;
    private final int posY;

    public Position(int x, int y){
        posX = x;
        posY = y;
    }

    public int getPosX() {
        return posX;
    }
    public int getPosY() {
        return posY;
    }
    public int[] getPos(){
        int []temp = new int[2];
        temp[0] = posX;
        temp[1] = posY;
        return temp;
    }
}
