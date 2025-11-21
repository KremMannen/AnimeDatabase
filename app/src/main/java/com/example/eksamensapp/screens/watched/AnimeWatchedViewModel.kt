package com.example.eksamensapp.screens.watched


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.data.database.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class AnimeWatchedViewModel : ViewModel() {
    private val _animes = MutableStateFlow<List<AnimeEntity>>(emptyList())
    val animes = _animes.asStateFlow()

    fun setAnimesByWatchedStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            _animes.value = AnimeRepository.getAnimeByWatchedStatus()
        }
    }

    fun setFavorite(animeEntity: AnimeEntity) {
        viewModelScope.launch {
            AnimeRepository.updateAnime(animeEntity.copy(isFavorite = true))
            refreshAnimes()
        }
    }

    fun unsetFavorite(animeEntity: AnimeEntity) {
        viewModelScope.launch {
            AnimeRepository.updateAnime(animeEntity.copy(isFavorite = false))
            refreshAnimes()
        }
    }

    fun filterFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _animes.value = AnimeRepository.getAnimeByWatchedStatus().filter { it.isFavorite }
        }
    }

    private fun refreshAnimes() {
        viewModelScope.launch(Dispatchers.IO) {
            _animes.value = AnimeRepository.getAnimeByWatchedStatus()
        }
    }

    init {
        setAnimesByWatchedStatus()
    }
}
