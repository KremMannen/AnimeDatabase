package com.example.eksamensapp.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.AnimeEntity
import com.example.eksamensapp.data.database.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.sql.SQLException

class AnimeSearchViewModel : ViewModel() {

    private val _searchedAnime = MutableStateFlow<List<AnimeEntity>>(emptyList())
    val searchedAnime = _searchedAnime.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _sqlError = MutableStateFlow<String?>(null)
    val sqlError = _sqlError.asStateFlow()

    private val _apiError = MutableStateFlow<String?>(null)
    val apiError = _apiError.asStateFlow()

    fun setSearchedAnime(anime: List<AnimeEntity>) {
        _searchedAnime.value = anime
    }

    fun handleInput(input: String) {

        _sqlError.value = null
        _apiError.value = null

        if (input.isBlank()) {
            showAll()
            return
        }

        val id = input.toIntOrNull()
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.value = true
                try {
                    val anime = AnimeRepository.getAnimeById(id)
                    _searchedAnime.value = anime?.let { listOf(it) } ?: emptyList()
                } catch (e: SQLException) {
                    _sqlError.value = e.message ?: "Unknown error"
                    _searchedAnime.value = emptyList()
                } catch (e: Exception) {
                    _apiError.value = e.message ?: "Unknown error"
                    _searchedAnime.value = emptyList()
                }  finally {
                    _isLoading.value = false
                }
            }
        }
        // Ellers søk på title
        if (id == null && input.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.value = true
                try {
                    setSearchedAnime(AnimeRepository.getAnimeByTitle(input))
                }
                catch (e: SQLException) {
                    _sqlError.value = e.message ?: "Unknown error"
                    _searchedAnime.value = emptyList()
                } catch (e: Exception) {
                    _apiError.value = e.message ?: "Unknown error"
                    _searchedAnime.value = emptyList()
                }
                finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun showAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val searchedAnime = AnimeRepository.getAllAnime()
            setSearchedAnime(searchedAnime)
        }
    }

    init {
        showAll()
    }

}