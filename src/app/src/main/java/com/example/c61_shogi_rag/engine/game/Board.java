package com.example.c61_shogi_rag.engine.game;

import com.example.c61_shogi_rag.engine.piece.PieceIDs;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.example.c61_shogi_rag.engine.piece.ShogiPiece;

import java.util.Vector;
/**
 * Nom du fichier : Board.java
 * Description : Ce fichier définit la classe représentant le plateau de jeu du Shogi,
 *               incluant les méthodes de manipulation des pièces et des positions sur l'échiquier.
 * Auteur : Gabriel Veilleux, Romeo Barraza
 * Entête générée par Copilot
 */
public class Board {
    private final int BOARD_SIZE = 9;
    private byte[][] board;

    public Board(){
        board = new byte[BOARD_SIZE][BOARD_SIZE];
    }

    public byte[][] getBoard(){
        return board;
    }

    @Override
    public Board clone() {
        Board clonedBoard = new Board();
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(this.board[i], 0, clonedBoard.board[i], 0, board[i].length);
        }
        return clonedBoard;
    }

    /**
     * Retroune le id de la piece a la positon
     * si retourn 0, ca veux dire qu'il n'y a pas de piece
     *
    * @param pos : Poistion sur l'échiquier 9x9
    **/
    public byte getPieceAt(Position pos){
        return board[pos.getPosX()][pos.getPosY()];
    }
    public void setPieceAt(ShogiPiece piece, Position pos){
        board[pos.getPosX()][pos.getPosY()] = piece.getID();
    }
    public void setPieceAt(byte pieceId, Position pos){
        board[pos.getPosX()][pos.getPosY()] = pieceId;
    }
    public void removePieceAt(Position pos){
        board[pos.getPosX()][pos.getPosY()] = 0;
    }
    public void movePieceTo(Position piecePosition, Position newPosition){
        byte temp = getPieceAt(piecePosition);
        removePieceAt(piecePosition);
        setPieceAt(temp, newPosition);
    }


    /**
    * Retourne un vecteur de positions de la pièce recherché
    *
    * @param piece La pièce recherché
    * */
    public Vector<Position> getPositionsFromPiece(ShogiPiece piece){
        Vector<Position> allPos = new Vector<>();

        byte pieceId = piece.getID();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(board[i][j] == pieceId){
                    allPos.add(new Position(i, j));
                }
            }
        }
        return allPos;
    }

    /**
     * Retourne un vecteur de positions de tous les pièces de la couleur recherché
     *
     * @param color La couleur recherché
     * */
    public Vector<Position> getPositionFromColor(boolean color){
        Vector<Position> allPos = new Vector<>();

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if ((color && board[i][j] > 0) || (!color && board[i][j] < 0)) {
                    allPos.add(new Position(i, j));
                }
            }
        }
        return allPos;
    }

    public boolean isWhitePawnInColumn(final int column) {
        boolean valid = true;
        for(int row = 0; row < BOARD_SIZE; row++) {
            byte temp =  getPieceAt(new Position(row, column));
            if(temp == PieceIDs.Pion.getValue()) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    public int getBOARD_SIZE() {
        return BOARD_SIZE;
    }
}