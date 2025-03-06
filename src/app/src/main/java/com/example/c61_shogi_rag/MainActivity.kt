package com.example.c61_shogi_rag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.c61_shogi_rag.dao.HistoriqueCoupsDAO
import com.example.c61_shogi_rag.dao.JoueurDAO
import com.example.c61_shogi_rag.dao.PartieDAO
import com.example.c61_shogi_rag.entity.HistoriqueCoups
import com.example.c61_shogi_rag.entity.HistoriqueCoupsCallback
import com.example.c61_shogi_rag.entity.Joueur
import com.example.c61_shogi_rag.entity.Partie
import com.example.c61_shogi_rag.entity.PartieCallback
import com.example.c61_shogi_rag.gamescreen.GameScreen
import com.example.c61_shogi_rag.ui.theme.C61_SHOGI_RAGTheme


class MainActivity : ComponentActivity() {

    var joueurDAO = JoueurDAO();

    var partieDao = PartieDAO();

    var historiqueCoupsDAO = HistoriqueCoupsDAO();

    var listeParties: MutableList<Partie> = mutableListOf()
    var listeCoups: MutableList<HistoriqueCoups> = mutableListOf()


    private var joueurRecuperer: Joueur ? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            C61_SHOGI_RAGTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //MainMenuScreen(innerPadding)
                    GameScreen(innerPadding)
                }
            }
        }

        //joueur.addJoueur("eric", "Salut92");

        joueurDAO.getJoueur { joueur ->
            if (joueur != null) {
                joueurRecuperer = joueur
                System.out.println(joueurRecuperer?.nom_joueur)
                joueurRecuperer?.joueur_id?.let {
                    getPartieJouer(it) //lance l'appel apres avoir recus les information sur joueur
                    getHistoriqueDeCoups(1) //doit attendre de recevoir l'appel de getPartie avant de lancer getHistoriques

                }

            }
        };

        //partieDao.addPartie(1, 2);
        //partieDao.addPartie(2, 1);
        //partieDao.addPartie(3, 2);



        //verification pour savoir sir joueur recuper n'est pas null
        //si non null appeler la fonctionv


//        joueurRecuperer?.let {
//            historiqueCoupsDAO.addHistorique(listeParties.get(1).partie_id, it.joueur_id,
//                "1", "1", "rois", "blanc")
//        };


        historiqueCoupsDAO.addHistorique(1, 2,
            "2", "2", "pion", "noir")




    }

    //methode permetant d'aller chercher l'historique des partie jouer
    // en fonction de l'id du joueur
    private fun getPartieJouer(id_joueur: Int) {
        partieDao.getPartie(object : PartieCallback {
            override fun onPartiesRecuperees(partieList: List<Partie>) {
                if (partieList.isNotEmpty()) {
                    listeParties.clear()
                    listeParties.addAll(partieList)
                    // Traiter les parties récupérées
                    System.out.println("Nombre de parties récupérées : ${listeParties.size}")
                } else {
                    System.out.println("Aucune partie trouvée pour ce joueur.")
                }
            }
        }, id_joueur)
    }

    //methode permetante de get tout les coups de la partie choisie
    private fun getHistoriqueDeCoups(id_partie: Int) {
        historiqueCoupsDAO.getHistoriqueCoups(object : HistoriqueCoupsCallback {
            override fun onHistoriqueRecuperee(coupsList: List<HistoriqueCoups>) {
                if (coupsList.isNotEmpty()) {
                    listeCoups.clear()
                    listeCoups.addAll(coupsList)
                    // Traiter les parties récupérées
                    System.out.println("Nombre de coups récupérées : ${listeCoups.size}")
                } else {
                    System.out.println("Aucune partie trouvée pour ce joueur.")
                }
            }
        }, id_partie)
    }
}

