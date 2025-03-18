package com.example.c61_shogi_rag.ui.screens.main_menu_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.entity.Joueur
import com.example.c61_shogi_rag.engine.dao.JoueurDAO
import com.example.c61_shogi_rag.engine.dao.PartieDAO
import com.example.c61_shogi_rag.engine.entity.JoueurCallback
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.entity.PartieCallback


class MainMenuViewModel(connectedUser:String): ViewModel()  {
    var connectedUser by mutableStateOf(connectedUser)
    var opponent by mutableStateOf("AI")

    var joueurRecuperer by mutableStateOf<Joueur?>(null)
        private set


    //private val joueurDAO = JoueurDAO()

    fun getJoueur(){
        JoueurDAO.getJoueur { joueur ->
            if (joueur != null) {
                joueurRecuperer = joueur
            }
        }
    }

    fun getJoueurById(id_joueur:Int){
        JoueurDAO.getJoueurById(object : JoueurCallback {
            override fun onJoueurRecupere(joueur: Joueur) {
                joueurRecuperer = joueur
            }
        }, id_joueur)
    }

}