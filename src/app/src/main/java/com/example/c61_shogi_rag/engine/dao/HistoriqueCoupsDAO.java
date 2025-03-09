package com.example.c61_shogi_rag.engine.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.c61_shogi_rag.engine.entity.HistoriqueCoups;
import com.example.c61_shogi_rag.engine.entity.HistoriqueCoupsCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueCoupsDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference HistoriqueDB = database.getReference("HistoriqueCoups");


    public void addHistorique(int partie_id, int joueur_id, String posX, String posY,
                              String nomPiece, String colorPiece){

        HistoriqueDB.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int nbr_joueur = (int) snapshot.getChildrenCount();
                int new_id = 1;

                if (nbr_joueur >= 0){
                    new_id = nbr_joueur+1;
                }

                HistoriqueCoups historiqueCoups = new HistoriqueCoups(new_id, partie_id,
                        joueur_id, posX, posY, nomPiece, colorPiece);

                HistoriqueDB.child(String.valueOf(new_id)).setValue(historiqueCoups);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erreur de lecture des données : " + error.getMessage());

            }
        });

    }

    public void getHistoriqueCoups(HistoriqueCoupsCallback callback, int id_partie){
        HistoriqueDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HistoriqueCoups> coupsList = new ArrayList<>();

                for (DataSnapshot child : snapshot.getChildren()) { // Utilisation correcte de la boucle
                    HistoriqueCoups historiqueCoups = child.getValue(HistoriqueCoups.class);
                    if (historiqueCoups != null) {
                        if (historiqueCoups.getPartie_id() == id_partie){
                            coupsList.add(historiqueCoups);

                        }
                        Log.d("Firebase", "Partie ID: " + historiqueCoups.getPartie_id());
                    }
                }

                callback.onHistoriqueRecuperee(coupsList); // Appeler le callback avec la liste des parties
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Échec de récupération.", error.toException());
            }
        });
    }

}
