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
    private val _totalWatchedCount = MutableStateFlow(0)
    val totalWatchedCount = _totalWatchedCount.asStateFlow()

    fun setAnimesByWatchedStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            val watchedAnimes = AnimeRepository.getAnimeByWatchedStatus()
            _animes.value = watchedAnimes
            _totalWatchedCount.value = watchedAnimes.size
        }
    }

    fun toggleFavorite(animeEntity: AnimeEntity) {
        viewModelScope.launch {
            val updatedAnimeEntity = animeEntity.copy(isFavorite = !animeEntity.isFavorite)
            AnimeRepository.updateAnime(updatedAnimeEntity)
            setAnimesByWatchedStatus()
        }
    }


    fun filterFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            _animes.value = AnimeRepository.getAnimeByWatchedStatus().filter { it.isFavorite }
        }
    }

    init {
        setAnimesByWatchedStatus()
    }
}
