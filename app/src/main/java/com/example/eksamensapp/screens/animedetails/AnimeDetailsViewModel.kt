package com.example.eksamensapp.screens.animedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.data.database.AnimeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeDetailsViewModel : ViewModel() {
    private val _animeEntity = MutableStateFlow<AnimeEntity?>(null)
    val anime = _animeEntity.asStateFlow()

    fun setAnime(id: Int) {
        viewModelScope.launch {
            _animeEntity.value = AnimeRepository.getAnimeById(id)
        }
    }

    fun updateAnime(animeEntity: AnimeEntity) {
        viewModelScope.launch {
            AnimeRepository.updateAnime(animeEntity)
        }
    }

    fun toggleWatched(animeEntity: AnimeEntity) {
        viewModelScope.launch {
            val updatedAnimeEntity = animeEntity.copy(haveWatched = !animeEntity.haveWatched)
            AnimeRepository.updateAnime(updatedAnimeEntity)
            setAnime(animeEntity.id)
        }
    }

}