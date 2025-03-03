package com.example.c61_shogi_rag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.c61_shogi_rag.dao.JoueurDAO
import com.example.c61_shogi_rag.dao.PartieDAO
import com.example.c61_shogi_rag.entity.Joueur
import com.example.c61_shogi_rag.entity.Partie
import com.example.c61_shogi_rag.entity.PartieCallback
import com.example.c61_shogi_rag.ui.theme.C61_SHOGI_RAGTheme

class MainActivity : ComponentActivity() {

    var joueurDAO = JoueurDAO();

    var partieDao = PartieDAO();

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
            }
        };

        //verification pour savoir sir joueur recuper n'est pas null
        //si non null appeler la fonction
        joueurRecuperer?.joueur_id?.let { getPartieJouer(it) }



    }

    //methode permetant d'aller chercher l'historique des partie jouer
    // en fonction de l'id du joueur
    private fun getPartieJouer(id_joueur: Int) {
        partieDao.getPartie(object : PartieCallback {
            override fun onPartiesRecuperees(partieList: List<Partie>) {
                if (partieList.isNotEmpty()) {
                    // Traiter les parties récupérées
                    System.out.println("Nombre de parties récupérées : ${partieList.size}")
                } else {
                    System.out.println("Aucune partie trouvée pour ce joueur.")
                }
            }
        }, id_joueur)

    }


}

