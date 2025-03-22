package com.example.c61_shogi_rag.engine.entity;

public interface JoueurCallback {
    void onJoueurRecupere(Joueur joueur);

    void onError(String message);
}
