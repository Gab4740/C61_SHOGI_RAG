package com.example.c61_shogi_rag.ui.screens.archived_game_screen

import androidx.lifecycle.ViewModel
import com.example.c61_shogi_rag.engine.dao.HistoriqueCoupsDAO

class ArchivedGameViewModel: ViewModel()  {
    val historiqueCoupsDAO = HistoriqueCoupsDAO()

}