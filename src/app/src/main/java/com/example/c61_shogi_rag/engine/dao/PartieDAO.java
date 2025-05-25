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
/**
 * Nom du fichier : PartieDAO.java
 * Description : Ce fichier implémente l'accès aux données des parties dans la base de données Firebase,
 *               permettant la création, la mise à jour et la récupération des parties jouées.
 * Auteur : Arslan Khaoua
 * Entête générée par Copilot
 */
public class PartieDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference partieDB = database.getReference("partie");

    /**
     *
     * permet d'ajouter une partie a la BD
     *
     * @param id_winner -> representant l'id du joueur gagnant
     * @param id_loser -> representant l'id du joueur perdant
     * @param playerCouleur -> representant la couleur du joueur
     * @param jsonString -> representant la liste de tous les coups jouer de la partie
     * @param partieFini -> representant si la partie est fini ou non
     * @param callback -> signale qui permet de rapeller la fonction quand un changement est emis
     *
     *
     **/
    public static void addPartie(int id_winner, int id_loser, boolean playerCouleur, String jsonString, boolean partieFini, PartieCallback callback) {


        partieDB.runTransaction(new Transaction.Handler(){
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                try{
                    long maxId = 0;

                    for (MutableData child : currentData.getChildren()) {
                        try {
                            long childId = Long.parseLong(child.getKey());
                            if (childId > maxId) {
                                maxId = childId;
                            }
                        } catch (NumberFormatException e) {
                            // Ignorer les clés non numériques
                            Log.w("PartieDAO", "Clé non numérique trouvée: " + child.getKey());
                        }
                    }

                    // Utiliser maxId + 1 pour le nouvel ID
                    long newId = maxId + 1;

                    String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                    Partie partie = new Partie((int) newId, id_winner, id_loser, date, jsonString, playerCouleur, partieFini);

                    currentData.child(String.valueOf(newId)).setValue(partie);

                    return Transaction.success(currentData);

                }catch (Exception e){
                    Log.e("PartieDAO", "Erreur lors de la transaction: " + e.getMessage(), e);
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

    }


    /**
     * methode qui permet de mettre a jour une partie reprise
     *
     * @param partieId -> representant l'id de la partie a modifier
     * @param winnerId -> representant l'id du joueur gagnant
     * @param loserId -> representant l'id du joueur perdant
     * @param partieFini -> representant si la partie est fini ou non
     * @param callback -> signale qui permet de rapeller la fonction quand un changement est emis
     *
     **/
    public static void updatePartie(int partieId, int winnerId, int loserId, boolean partieFini,String jsonString, PartieCallback callback){
        DatabaseReference partieRef = partieDB.child(String.valueOf(partieId));

        partieRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                try{
                    Partie partie = currentData.getValue(Partie.class);
                    if (partie != null){
                        partie.setWinner_id(winnerId);
                        partie.setLoser_id(loserId);
                        partie.setHistoriqueCoups(jsonString);
                        partie.setPartieTerminee(partieFini);
                        currentData.setValue(partie);
                    }
                    return Transaction.success(currentData);
                }catch (Exception e){
                    Log.e("PartieDAO", "Erreur lors de la mise a jour: " + e.getMessage());
                    return Transaction.abort();
                }
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (error !=null){
                    if (callback != null){
                        callback.onError(error.toException());
                    }
                } else if (committed){
                    if (callback != null){
                        callback.onPartieCree("Partie mise a jour");
                    }
                }
            }

        });
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
        partieDB.addListenerForSingleValueEvent(new ValueEventListener() {
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
