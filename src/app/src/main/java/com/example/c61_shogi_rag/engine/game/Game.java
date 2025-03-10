package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.piece.InitPiece;
import com.example.c61_shogi_rag.engine.piece.Piece;
import com.example.c61_shogi_rag.engine.piece.PieceIDs;
import com.example.c61_shogi_rag.engine.piece.Position;

import java.util.Hashtable;

public class Game {
    private Board gameBoard;
    private Hashtable<Byte, Piece> pieces;
    private Hashtable<Byte, Piece> capturedPieceBlack;
    private Hashtable<Byte, Piece> capturedPieceWhite;
    private Time gameTimer;
    private Boolean isPlayerStarting;

    /**
     * @param isPlayerStarting : Si le joueur commence : true, si le AI commence : false
     * */
    public Game(Boolean isPlayerStarting){
        this.pieces = new Hashtable<>();
        this.capturedPieceBlack = new Hashtable<>();
        this.capturedPieceWhite = new Hashtable<>();
        this.gameTimer = new Time();
        this.gameBoard = new Board();
        this.isPlayerStarting = isPlayerStarting;
    }
    /**
     * Permet de créer les pièce nécessaire au jeux
     * */
    private void PieceInit(){
        Piece pionBlanc = InitPiece.create("Pion_blanc");
        pieces.put(pionBlanc.getID(), pionBlanc);

        Piece pionNoir = InitPiece.create("Pion_noir");
        pieces.put(pionNoir.getID(), pionNoir);

        Piece Lance_blanc = InitPiece.create("Lance_blanc");
        pieces.put(Lance_blanc.getID(), Lance_blanc);

        Piece Lance_noir = InitPiece.create("Lance_noir");
        pieces.put(Lance_noir.getID(), Lance_noir);

        Piece Chevalier_blanc = InitPiece.create("Chevalier_blanc");
        pieces.put(Chevalier_blanc.getID(), Chevalier_blanc);

        Piece Chevalier_noir = InitPiece.create("Chevalier_noir");
        pieces.put(Chevalier_noir.getID(), Chevalier_noir);

        Piece GeneralArgent_blanc = InitPiece.create("GeneralArgent_blanc");
        pieces.put(GeneralArgent_blanc.getID(), GeneralArgent_blanc);

        Piece GeneralArgent_noir = InitPiece.create("GeneralArgent_noir");
        pieces.put(GeneralArgent_noir.getID(), GeneralArgent_noir);

        Piece Generalor_blanc = InitPiece.create("Generalor_blanc");
        pieces.put(Generalor_blanc.getID(), Generalor_blanc);

        Piece Generalor_noir = InitPiece.create("Generalor_noir");
        pieces.put(Generalor_noir.getID(), Generalor_noir);

        Piece Fou_blanc = InitPiece.create("Fou_blanc");
        pieces.put(Fou_blanc.getID(), Fou_blanc);

        Piece Fou_noir = InitPiece.create("Fou_noir");
        pieces.put(Fou_noir.getID(), Fou_noir);

        Piece Char_blanc = InitPiece.create("Char_blanc");
        pieces.put(Char_blanc.getID(), Char_blanc);

        Piece Char_noir = InitPiece.create("Char_noir");
        pieces.put(Char_noir.getID(), Char_noir);

        if(isPlayerStarting){
            Piece Roi_blanc = InitPiece.create("roisente_blanc");
            pieces.put(Roi_blanc.getID(), Roi_blanc);

            Piece Roi_noir = InitPiece.create("roigote_noir");
            pieces.put(Roi_noir.getID(), Roi_noir);

        }else{
            Piece Roi_blanc = InitPiece.create("roigote_blanc");
            pieces.put(Roi_blanc.getID(), Roi_blanc);

            Piece Roi_noir = InitPiece.create("roisente_noir");
            pieces.put(Roi_noir.getID(), Roi_noir);
        }
    }
    /**
     * Place les pièces a leur positon de départ sur l'échiquier
     * */
    private void BoardInit(){
        Piece pieceToPlace;

        pieceToPlace = pieces.get((byte)PieceIDs.Pion.getValue());
        for(int i =0; i<9; i++){
            gameBoard.setPieceAt(pieceToPlace, new Position(6,i));
        }

        pieceToPlace = pieces.get((byte)-PieceIDs.Pion.getValue());
        for(int i =0; i<9; i++){
            gameBoard.setPieceAt(pieceToPlace, new Position(2,i));
        }

        pieceToPlace = pieces.get((byte)PieceIDs.Lance.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,0));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,8));

        pieceToPlace = pieces.get((byte)-PieceIDs.Lance.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,0));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,8));

        pieceToPlace = pieces.get((byte)PieceIDs.Chevalier.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,1));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,7));

        pieceToPlace = pieces.get((byte)-PieceIDs.Chevalier.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,1));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,7));

        pieceToPlace = pieces.get((byte)PieceIDs.GeneralArgent.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,2));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,6));

        pieceToPlace = pieces.get((byte)-PieceIDs.GeneralArgent.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,2));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,6));

        pieceToPlace = pieces.get((byte)PieceIDs.GeneralOr.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,3));
        gameBoard.setPieceAt(pieceToPlace, new Position(8,5));

        pieceToPlace = pieces.get((byte)-PieceIDs.GeneralOr.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,3));
        gameBoard.setPieceAt(pieceToPlace, new Position(0,5));

        pieceToPlace = pieces.get((byte)PieceIDs.Roi.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(8,4));

        pieceToPlace = pieces.get((byte)-PieceIDs.Roi.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(0,4));

        pieceToPlace = pieces.get((byte)PieceIDs.Fou.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(7,1));

        pieceToPlace = pieces.get((byte)-PieceIDs.Fou.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(1,7));

        pieceToPlace = pieces.get((byte)PieceIDs.Char.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(7,7));

        pieceToPlace = pieces.get((byte)-PieceIDs.Char.getValue());
        gameBoard.setPieceAt(pieceToPlace, new Position(1,1));
    }
    public void GameInit(){
        PieceInit();
        BoardInit();
        prettyPrintConsoleBoard();
        // TODO
    }
    public Board getGameBoard() {
        return gameBoard;
    }

    /**
     * Retourne un objet piece pour la position donné
     *
     * @param pos : Positon x et y sur l'échiquier
     */
    public Piece getPieceAt(Position pos){
        return pieces.get(gameBoard.getPieceAt(pos));
    }

    // DEBUG TOOLS
    private void prettyPrintConsoleBoard(){
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 9; j++) {
                System.out.print(gameBoard.getBoard()[i][j] + " ");
            }
            System.out.println();
        }
    }
}
