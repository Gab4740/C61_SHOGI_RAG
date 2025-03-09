package com.example.c61_shogi_rag.engine.dao;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.c61_shogi_rag.engine.entity.Partie;
import com.example.c61_shogi_rag.engine.entity.PartieCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PartieDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference partieDB = database.getReference("partie");



    public void addPartie(int id_winner, int id_loser) {

        partieDB.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int nbr_joueur = (int) snapshot.getChildrenCount();
                int new_id = 1;

                if (nbr_joueur >= 0){
                    new_id = nbr_joueur+1;
                }

                LocalDate dateActuelle = LocalDate.now();
                String date = dateActuelle.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Partie partie = new Partie(new_id, id_winner, date, id_loser);

                partieDB.child(String.valueOf(new_id)).setValue(partie);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erreur de lecture des données : " + error.getMessage());

                }
        });

    }



    public void getPartie(PartieCallback callback, int id_joueur) {
        partieDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Partie> partieList = new ArrayList<>();
                System.out.println(id_joueur);
                for (DataSnapshot child : snapshot.getChildren()) { // Utilisation correcte de la boucle
                    Partie partie = child.getValue(Partie.class);
                    if (partie != null) {
                        if (partie.getWinner_id() == id_joueur || partie.getLoser_id() == id_joueur){
                            partieList.add(partie);

                        }
                        Log.d("Firebase", "Partie ID: " + partie.getPartie_id());
                    }
                }

                callback.onPartiesRecuperees(partieList); // Appeler le callback avec la liste des parties
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase", "Échec de récupération.", error.toException());
            }
        });
    }



}
