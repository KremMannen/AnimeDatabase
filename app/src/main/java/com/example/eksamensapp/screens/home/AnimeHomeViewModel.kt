package com.example.eksamensapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.data.database.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeHomeViewModel : ViewModel() {
    private val _animes = MutableStateFlow<List<AnimeEntity>>(emptyList())
    val animes = _animes.asStateFlow()

    fun setAnimes() {
        viewModelScope.launch(Dispatchers.IO)
        { AnimeRepository.populateDatabaseFromApi()
            _animes.value = AnimeRepository.getAllAnime() }
    }

    init {
        setAnimes()
    }
}