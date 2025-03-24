package com.example.c61_shogi_rag.engine.dao;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.c61_shogi_rag.engine.entity.Joueur;
import com.example.c61_shogi_rag.engine.entity.JoueurCallback;
import com.example.c61_shogi_rag.engine.entity.JoueurInfo;
import com.example.c61_shogi_rag.engine.util.BCrypt;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class JoueurDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference joueurDB = database.getReference("joueur");
    private static final DatabaseReference joueurInfoDB = database.getReference("joueurInfo");



    /**
     *
     * methode permettant de cree un nouveau joueur et de l'envoyer a la BD
     *
     * @param nom -> representant le nom/pseudo du joueur
     *
     **/
    public static void addJoueur(String nom, String password, JoueurCallback callback) {

        // 1. Vérifier si le nom d'utilisateur existe déjà
        Query joueurExiste = joueurDB.orderByChild("nom_joueur").equalTo(nom);
        joueurExiste.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Si le nom existe déjà, on arrête et on signale l'erreur
                if (snapshot.exists()) {
                    callback.onError("Un utilisateur avec ce nom existe déjà. Veuillez en choisir un autre");
                    return;
                }

                // 2. Générer un nouvel ID pour le joueur
                joueurDB.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        // Calculer le nouvel ID
                        int nbr_joueur = (int) snapshot.getChildrenCount();
                        int new_id = (nbr_joueur >= 0) ? nbr_joueur + 1 : 1;

                        // 3. Hasher le mot de passe
                        String motDePasse = BCrypt.hashpw(password, BCrypt.gensalt());

                        // 4. Créer et sauvegarder le joueur
                        Joueur joueur = new Joueur(new_id, nom);
                        joueurDB.child(String.valueOf(new_id)).setValue(joueur)
                                .addOnSuccessListener(aVoid -> {

                                    // 5. Créer et sauvegarder les infos du joueur
                                    JoueurInfo joueurInfo = new JoueurInfo(new_id, motDePasse);
                                    joueurInfoDB.child(String.valueOf(new_id)).setValue(joueurInfo)
                                            .addOnSuccessListener(aVoid2 -> {

                                                // Tout est sauvegardé avec succès
                                                callback.onJoueurRecupere(joueur);
                                            })
                                            .addOnFailureListener(e -> {
                                                callback.onError("Erreur lors de l'enregistrement des infos: " + e.getMessage());
                                            });
                                })
                                .addOnFailureListener(e -> {
                                    callback.onError("Erreur lors de l'enregistrement du joueur: " + e.getMessage());
                                });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onError("Erreur de base de données: " + error.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onError("Erreur de base de données: " + error.getMessage());
            }
        });
    }


    //avoir si on laisse ou non
    public static void getJoueurById(JoueurCallback callback, int id_joueur){
        joueurDB.child(String.valueOf(id_joueur)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Joueur joueur = snapshot.getValue(Joueur.class);
                    if (joueur != null){
                        //Log.d(TAG, "Joueur récupéré avec l'ID "+ id_joueur + " :" + joueur.getNom_joueur());
                        callback.onJoueurRecupere(joueur);
                    }else {
                        //Log.d(TAG, "Aucun joueur trouvé.");
                        callback.onJoueurRecupere(null);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Échec de récupération.", error.toException());
            }
        });
    }



    public static void getJoueurByName(JoueurCallback callback, String nom, String password) {
        Query query = joueurDB.orderByChild("nom_joueur").equalTo(nom);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Joueur joueur = null;
                    for (DataSnapshot joueurSnapshot : snapshot.getChildren()) {
                        joueur = joueurSnapshot.getValue(Joueur.class);
                        System.out.println("Joueur trouvé : " + joueur.getNom_joueur());
                    }

                    Query queryJoueurInfo = joueurInfoDB.orderByChild("idJoueur").equalTo(joueur.getJoueur_id());
                    Joueur finalJoueur = joueur;

                    queryJoueurInfo.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotInfo) {
                            if (snapshot.exists()) {
                                JoueurInfo joueurInfo = null;
                                for (DataSnapshot infoSnapshot : snapshotInfo.getChildren()) {
                                    joueurInfo = infoSnapshot.getValue(JoueurInfo.class);

                                    if (BCrypt.checkpw(password, joueurInfo.getMotDePasse())) {
                                        System.out.println("bon mots de passe");
                                        callback.onJoueurRecupere(finalJoueur);
                                    } else {
                                        System.out.println("pas bon mots de passe");
                                        callback.onError("Mot de passe incorrect.");
                                        return;
                                    }

                                }
                            }
                            else{
                                callback.onError("information du joueur introuvable.");
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.w(TAG, "Échec de récupération.", error.toException());
                            callback.onError("Erreur de la base de donnee : " + error.getMessage());
                        }
                    });

                }else{
                    callback.onError("Joueur introuvable.");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Échec de récupération.", error.toException());
                callback.onError("Erreur de la base de donnee : " + error.getMessage());
            }
        });
    }

}
