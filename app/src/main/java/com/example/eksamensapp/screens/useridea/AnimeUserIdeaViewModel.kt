package com.example.eksamensapp.screens.useridea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.UserIdeaDbRepository
import com.example.eksamensapp.data.database.UserIdeaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnimeUserIdeaViewModel : ViewModel() {
    private val _userIdeas = MutableStateFlow<List<UserIdeaEntity>>(emptyList())
    val userIdeas = _userIdeas.asStateFlow()

    fun setUserIdeas(userIdeas: List<UserIdeaEntity>) {
            _userIdeas.value = userIdeas
    }
    fun showAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val userIdeas = UserIdeaDbRepository.getUserIdeas()
            setUserIdeas(userIdeas)
        }
    }

    init {
        showAll()
    }
}





