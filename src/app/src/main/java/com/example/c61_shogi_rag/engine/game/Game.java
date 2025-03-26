package com.example.c61_shogi_rag.engine.game;

import static java.security.AccessController.getContext;

import android.widget.Toast;

import com.example.c61_shogi_rag.engine.dao.PartieDAO;
import com.example.c61_shogi_rag.engine.entity.Partie;
import com.example.c61_shogi_rag.engine.entity.PartieCallback;
import com.example.c61_shogi_rag.engine.piece.InitPiece;
import com.example.c61_shogi_rag.engine.piece.Move;
import com.example.c61_shogi_rag.engine.piece.Piece;
import com.example.c61_shogi_rag.engine.piece.PieceIDs;
import com.example.c61_shogi_rag.engine.piece.Position;
import com.google.gson.Gson;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class Game {
    private final Board gameBoard;
    private Hashtable<Byte, Piece> pieces;
    private Vector<Byte> capturedPieceBlack;
    private Vector<Byte> capturedPieceWhite;
    private final Time gameTimer;
    private Boolean isPlayerStarting;
    private Boolean isPlayerTurn;
    private Boolean isGameEnded;
    private Boolean GameWinner;
    private static GameSaver gameSaver;


    /**
     * @param isPlayerStarting : Si le joueur commence : true, si le AI commence : false
     * */
    public Game(Boolean isPlayerStarting){
        this.pieces = new Hashtable<>();
        this.capturedPieceBlack = new Vector<>();
        this.capturedPieceWhite = new Vector<>();
        this.gameTimer = new Time();
        this.gameBoard = new Board();
        this.isPlayerStarting = isPlayerStarting;
        this.isPlayerTurn = isPlayerStarting;
        this.isGameEnded = false;
        this.gameSaver = new GameSaver();
        this.GameWinner = null;
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
    /**
     * Initialise tous les variables nécessaire pour jouer la partie
     * */
    public void GameInit(){
        PieceInit();
        BoardInit();
        gameTimer.startTime();
    }
    /**
     * Méthode qui permet de d'effectuer le déplacement d'une pièce
     *
     * @param firstPos : La pièce choisi,
     * @param secondPos : La nouvelle position choisi
     * */
    public boolean playTurn(Position firstPos, Position secondPos){
        boolean valid = false;
        boolean enemyPieceToCapture =  isEnemyPieceAtPos(secondPos);

        if(isPlayerPieceAtPos(firstPos) && (enemyPieceToCapture || isPositionEmpty(secondPos))){
            Piece pieceToPlay = getPieceAt(firstPos);
            Move pieceMove = new Move(firstPos, secondPos);

            if(pieceToPlay.isValidMove(pieceMove, gameBoard)){
                if(enemyPieceToCapture){
                    capturePieceAtPos(secondPos, true);
                }
                gameBoard.movePieceTo(firstPos, secondPos);

                gameSaver.addNewTurn(new OneTurn(pieceToPlay.getID(), firstPos, secondPos, false));

                isPlayerTurn = !isPlayerTurn;
                valid = true;
            }
            promotePiece(pieceToPlay);
            isGameEnded = isKingsAlive();
        }
        return valid;
    }
    /**
     * Méthode qui permet de d'effectuer le parachutage d'une piece
     *
     * @param parachutePos : La position cliquer,
     * @param piece : Pièce a parachuter
     * */
    public boolean parachute(Position parachutePos, byte piece){
        boolean valid = false;


        return valid;
    }
    /**
     * Cheque si la piece doit etre promu, si oui effectue le changement
     *
     * @param piece : La piece du tour courrant
     * */
    private void promotePiece(Piece piece){
        // TODO
    }
    public boolean getIsGameEnded(){ return isGameEnded; }
    /**
     * Retourne la classe qui possede la liste de l'historique de coup
     * */
    public static GameSaver getGameSaver() {return gameSaver; }
    /**
     * Retourne la classe du GameBoard
     * */
    public Board getGameBoard(){ return gameBoard; }
    /**
     * Retourne un booléen qui indique si c'est le tour du joueur = true ou au AI = false
     * */
    public boolean getIsPlayerTurn(){ return isPlayerTurn; }
    /**
     * Retourne un objet piece pour la position donné
     * */
    public Piece getPieceAt(Position pos){ return pieces.get(gameBoard.getPieceAt(pos)); }
    /**
     * Retourn True si c'est une pièce enemy, False si ce n'est pas une piece enemy ou pas de piece
     * */
    private boolean isEnemyPieceAtPos(Position pos){ return gameBoard.getPieceAt(pos) < 0; }
    /**
     * Retourn True si une piece du joeur est a la positon, false si non
     * */
    public boolean isPlayerPieceAtPos(Position pos){ return gameBoard.getPieceAt(pos) > 0; }
    /**
     * Retourn True si la case de l'échiquier est vide
     * */
    private boolean isPositionEmpty(Position pos){ return gameBoard.getPieceAt(pos) == 0; }
    /**
     * Retourne true si les deux rois sont encore en vie, si au moins un roi est mort, retourne false
     * */
    private boolean isKingsAlive(){
        for(byte id : capturedPieceBlack){
            if(Math.abs(id) == PieceIDs.Roi.getValue()){
                return true;
            }
        }
        for(byte id : capturedPieceWhite){
            if(Math.abs(id) == PieceIDs.Roi.getValue()){
                return true;
            }
        }
        return false;
    }
    /**
     * Méthode pour capturer une pièce a la position donné.
     *
     * @param pos : Position de la pièce a captuer,
     * @param color : True = Les blancs capture (joueur), False = Les noirs capture (AI)
     * */
    private void capturePieceAtPos(Position pos, boolean color){
        if (color) {
            capturedPieceWhite.add(gameBoard.getPieceAt(pos));
        } else {
            capturedPieceBlack.add(gameBoard.getPieceAt(pos));
        }
        gameBoard.removePieceAt(pos);

    }

    // DEBUG TOOLS
    public void prettyPrintConsoleBoard(){
        for (int i = 0; i < 9; i++) {
            System.out.print((i + 1) + " | ");
            for (int j = 0; j < 9; j++) {
                System.out.print(gameBoard.getBoard()[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void archiverPartie(int id_gagnant, int id_perdant){

        if (isGameEnded){
            try{
                Gson gson = new Gson();

                String jsonString = gson.toJson(gameSaver.getTurnList());

                PartieDAO.addPartie(id_gagnant, id_perdant, jsonString, new PartieCallback() {
                    @Override
                    public void onPartiesRecuperees(List<Partie> partieList) {
                        //voir pour peut etre mettre un toast
                    }

                    @Override
                    public void onError(Exception e) {
                        //voir pour peut etre mettre un toast
                    }
                });

            }catch (Exception exception){
                exception.printStackTrace();
            }

        }


    }
}
