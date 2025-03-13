package com.example.c61_shogi_rag.ui.screens.history_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.dao.PartieDAO
import com.example.c61_shogi_rag.engine.entity.Partie
import com.example.c61_shogi_rag.engine.entity.PartieCallback

class HistoryViewModel: ViewModel()  {

    var listeParties by mutableStateOf<List<Partie>>(emptyList())


     fun getPartieJouer(id_joueur: Int) {
        PartieDAO.getPartie(object : PartieCallback {
            override fun onPartiesRecuperees(partieList: List<Partie>) {
               listeParties = partieList
            }
        }, id_joueur)
    }

}