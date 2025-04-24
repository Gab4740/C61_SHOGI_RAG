package com.example.c61_shogi_rag.engine.dao;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.c61_shogi_rag.engine.entity.Partie;
import com.example.c61_shogi_rag.engine.entity.PartieCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PartieDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference partieDB = database.getReference("partie");

    /**
     *
     * permet d'ajouter une partie a la BD
     *
     * @param id_winner -> representant l'id du joueur gagnant
     * @param id_loser -> representant l'id du joueur perdant
     *
     *
     **/
    public static void addPartie(int id_winner, int id_loser, String jsonString, PartieCallback callback) {


        partieDB.runTransaction(new Transaction.Handler(){
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                try{
                    long nombrePartie = currentData.getChildrenCount();

                    long newId = nombrePartie + 1;
                    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    Partie partie = new Partie((int) newId, id_winner, id_loser, date, jsonString);

                    currentData.child(String.valueOf(newId)).setValue(partie);

                    return Transaction.success(currentData);

                }catch (Exception e){
                    return Transaction.abort();
                }

            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (error != null){

                    if (callback != null){
                        callback.onError(error.toException());

                    }
                } else if (committed) {
                    if (callback != null){
                       if (currentData != null){
                           callback.onPartieCree("Partie sauvegarder");
                       } else {
                           callback.onError(new Exception("Erreur de creation de la partie"));
                       }
                    }
                }
            }

        });

        //a laisser pour l'instant quand on fera les teste pour ajouter les partie
//                addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int nbr_joueur = (int) snapshot.getChildrenCount();
//                int new_id = 1;
//
//                if (nbr_joueur >= 0){
//                    new_id = nbr_joueur+1;
//                }
//
//                LocalDate dateActuelle = LocalDate.now();
//                String date = dateActuelle.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//
//                Partie partie = new Partie(new_id, id_winner, id_loser, date, jsonString);
//
//                partieDB.child(String.valueOf(new_id)).setValue(partie);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Firebase", "Erreur de lecture des données : " + error.getMessage());
//            }
//        });
    }


    /**
     *
     * permet d'aller chercher toutes les parties gagner et perdu du joueur
     *
     * @param callback -> signale qui permet de rapeller la fonction quand un changement est emis
     * @param id_joueur -> representant l'id du joueur connecter
     *
     * return une liste de Partie
     *
     **/
    public static void getPartie(PartieCallback callback, int id_joueur) {
        partieDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Partie> partieList = new ArrayList<>();
                System.out.println(id_joueur);
                for (DataSnapshot child : snapshot.getChildren()) {
                    Partie partie = child.getValue(Partie.class);
                    if (partie != null) {
                        if (partie.getWinner_id() == id_joueur || partie.getLoser_id() == id_joueur){
                            partieList.add(partie);
                        }
                    }
                }
                callback.onPartiesRecuperees(partieList); // Appeler le callback avec la liste des parties
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError(error.toException());
            }
        });
    }

}
