package com.example.eksamensapp.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.data.database.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeSearchViewModel : ViewModel() {


    private val _searchedAnime = MutableStateFlow<List<AnimeEntity>>(emptyList())
    val searchedAnime = _searchedAnime.asStateFlow()

    fun setSearchedAnime(input: String) {
        val id = input.toIntOrNull()

        // Om input er et tall, søk på id
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val anime = AnimeRepository.getAnimeById(id)
                // lager en tom liste dersom ingenting blir funnet på id
                _searchedAnime.value = anime?.let { listOf(it) } ?: emptyList()
            }
        }
        // Ellers søk på title
        if (id == null && input.isNotBlank()) {viewModelScope.launch(Dispatchers.IO) {
            _searchedAnime.value = AnimeRepository.getAnimeByTitle(input)
        }}

    }

    fun showAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _searchedAnime.value = AnimeRepository.getAllAnime()
        }
    }

    init {
        showAll()
    }

}