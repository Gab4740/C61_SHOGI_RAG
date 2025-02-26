package com.example.c61_shogi_rag.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.c61_shogi_rag.entity.Joueur;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PartieDAO {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference test = database.getReference("joueur");


    //TODO terminer les methode d'acces

    public void addPartie(int id_winner, int id_loser) {
    }


//        test.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int nbr_joueur = (int) snapshot.getChildrenCount();
//                int new_id = 1;
//
//                if (nbr_joueur >= 0){
//                    new_id = nbr_joueur+1;
//                }
//
//                Joueur joueur = new Joueur(new_id, nom);
//
//                test.child(String.valueOf(new_id)).setValue(joueur);
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.e("Firebase", "Erreur de lecture des données : " + error.getMessage());
//
//            }
//        });

    //}

//    public void getJoueur(){
//
//        test.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Joueur joueur = snapshot.child("1").getValue(Joueur.class);
//                Log.d(TAG, "value is :" + joueur.getNom_joueur());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//    }

}
