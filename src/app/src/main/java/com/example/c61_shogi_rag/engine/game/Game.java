package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.piece.InitPiece;
import com.example.c61_shogi_rag.engine.piece.Piece;
import com.example.c61_shogi_rag.engine.piece.Position;

import java.util.Vector;

public class Game {
    private Board gameBoard;
    private Vector<Piece> pieces;
    private Vector<Piece> capturedPieceBlack;
    private Vector<Piece> capturedPieceWhite;
    private Time gameTimer;
    private Boolean isPlayerStarting;

    public Game(Boolean isPlayerStarting){
        this.pieces = new Vector<Piece>();
        this.capturedPieceBlack = new Vector<Piece>();
        this.capturedPieceWhite = new Vector<Piece>();
        this.gameTimer = new Time();
        this.gameBoard = new Board();
        this.isPlayerStarting = isPlayerStarting;
    }
    private void PieceInit(){
        pieces.add(InitPiece.create("Pion_noir"));
        pieces.add(InitPiece.create("Pion_blanc"));

        pieces.add(InitPiece.create("Lance_blanc"));
        pieces.add(InitPiece.create("Lance_noir"));

        pieces.add(InitPiece.create("Chevalier_blanc"));
        pieces.add(InitPiece.create("Chevalier_noir"));

        pieces.add(InitPiece.create("GeneralArgent_blanc"));
        pieces.add(InitPiece.create("GeneralArgent_noir"));

        pieces.add(InitPiece.create("Generalor_blanc"));
        pieces.add(InitPiece.create("Generalor_noir"));

        pieces.add(InitPiece.create("Fou_blanc"));
        pieces.add(InitPiece.create("Fou_noir"));

        pieces.add(InitPiece.create("Char_blanc"));
        pieces.add(InitPiece.create("Char_noir"));

        if(isPlayerStarting){
            pieces.add(InitPiece.create("roisente_blanc"));
            pieces.add(InitPiece.create("roigote_noir"));
        }else{
            pieces.add(InitPiece.create("roisente_noir"));
            pieces.add(InitPiece.create("roigote_blanc"));
        }
    }
    private void BoardInit(){
        // PLACEMENT DES PIONS BLANCS
        gameBoard.setPieceAt(pieces.get(0), new Position(6,0));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,1));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,2));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,3));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,4));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,5));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,6));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,7));
        gameBoard.setPieceAt(pieces.get(0), new Position(6,8));
    }
    public void GameInit(){
        PieceInit();
        BoardInit();
        // TODO
    }
}
