package com.example.eksamensapp.screens.animedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.Anime
import com.example.eksamensapp.data.database.AnimeDbRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {
    private val _anime = MutableStateFlow<Anime?>(null)
    val anime = _anime.asStateFlow()

    fun setAnime(id: Int) {
        viewModelScope.launch {
            _anime.value = AnimeDbRepository.getAnimeById(id)
        }
    }
}