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


    fun handleUpdateIdea(idea: UserIdeaEntity) {
            viewModelScope.launch(Dispatchers.IO) {
                UserIdeaDbRepository.updateUserIdea(idea)
            }
        }
    }
