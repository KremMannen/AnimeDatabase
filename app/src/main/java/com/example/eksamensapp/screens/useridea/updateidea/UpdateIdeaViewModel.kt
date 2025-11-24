package com.example.eksamensapp.screens.useridea.updateidea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.eksamensapp.data.database.UserIdeaDbRepository
import com.example.eksamensapp.data.database.UserIdeaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpdateIdeaViewModel : ViewModel() {

    private val _searchedIdea = MutableStateFlow<List<UserIdeaEntity>>(emptyList())
    val searchedIdea = _searchedIdea.asStateFlow()


    fun setSearchedIdea(ideas: List<UserIdeaEntity>) {
        _searchedIdea.value = ideas
    }

    fun handleUpdateIdea(idea: UserIdeaEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            UserIdeaDbRepository.updateUserIdea(idea)
        }
    }

    suspend fun getUserById(id: Int): UserIdeaEntity? {
            return UserIdeaDbRepository.getUserIdeaById(id)
    }

    fun handleInput(input: String) {
        if (input.isBlank()) {
            showAll()
            return
        }

        val id = input.toIntOrNull()
        // Om input er et tall, søk på id
        if (id != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val userIdea = UserIdeaDbRepository.getUserIdeaById(id)
                // Lager en tom liste dersom ingenting blir funnet på id
                _searchedIdea.value = userIdea?.let { listOf(it) } ?: emptyList()
            }
        }
        // Ellers søk på title
        if (id == null && input.isNotBlank()) {
            viewModelScope.launch(Dispatchers.IO) {
                val userIdea = UserIdeaDbRepository.getUserIdeaByTitle(input)
                _searchedIdea.value = userIdea?.let { listOf(it) } ?: emptyList()
            }
        }
    }
    fun showAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val ideas = UserIdeaDbRepository.getUserIdeas()
            setSearchedIdea(ideas)
        }
    }

    init {
        showAll()
    }

}
