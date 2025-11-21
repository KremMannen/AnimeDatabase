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
    private val _animeEntity = MutableStateFlow<AnimeEntity?>(null)
    val animes = _animes.asStateFlow()
    val anime = _animeEntity.asStateFlow()


    fun setAnime(id: Int) {
        viewModelScope.launch {
            _animeEntity.value = AnimeRepository.getAnimeById(id)
        }
    }


    fun setAnimesByWatchedStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            _animes.value = AnimeRepository.getAnimeByWatchedStatus()
        }
    }


    fun setFavorite(animeEntity: AnimeEntity) {
        viewModelScope.launch {
            AnimeRepository.updateAnime(animeEntity.copy(isFavorite = true))
            setAnime(animeEntity.id)
        }
    }


    fun unsetFavorite(animeEntity: AnimeEntity) {
        viewModelScope.launch {
            AnimeRepository.updateAnime(animeEntity.copy(isFavorite = false))
            setAnime(animeEntity.id)
        }
    }


    init {
        setAnimesByWatchedStatus()
    }
}

