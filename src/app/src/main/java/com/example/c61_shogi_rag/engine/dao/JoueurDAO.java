package com.example.c61_shogi_rag.engine.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.c61_shogi_rag.engine.entity.Joueur;
import com.example.c61_shogi_rag.engine.entity.JoueurCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JoueurDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference joueurDB = database.getReference("joueur");


    /**
     *
     * methode permettant de cree un nouveau joueur et de l'envoyer a la BD
     *
     * @param nom -> representant le nom/pseudo du joueur
     *
     **/
    public static void addJoueur(String nom){

        // TODO creer un objet joueur_information contenant le nom
        //  et le mots passe ou autre information privee

        joueurDB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int nbr_joueur = (int) snapshot.getChildrenCount();
                int new_id = 1;

                if (nbr_joueur >= 0){
                    new_id = nbr_joueur+1;
                }
                //rajouter le mot de passe avec la class BCrypt pour crypter le mots de passe

                Joueur joueur = new Joueur(new_id, nom);

                joueurDB.child(String.valueOf(new_id)).setValue(joueur);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Erreur de lecture des données : " + error.getMessage());
            }
        });

    }



    /**
     *
     * methode permettant de recuper un joueur
     *
     * return un Joueur
     **/

    //TODO (futur version si temps) rajouter une verification par mot de passe
    public static void getJoueur(JoueurCallback callback) {
        joueurDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Joueur joueur = snapshot.child("1").getValue(Joueur.class);
                if (joueur != null) {
                    Log.d(TAG, "Joueur récupéré: " + joueur.getNom_joueur());
                    callback.onJoueurRecupere(joueur); // Envoi du joueur au callback
                } else {
                    Log.d(TAG, "Aucun joueur trouvé.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Échec de récupération.", error.toException());
            }
        });
    }
}
