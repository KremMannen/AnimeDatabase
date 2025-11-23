package com.example.eksamensapp.screens.useridea.deleteidea

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eksamensapp.data.database.UserIdeaDbRepository
import com.example.eksamensapp.data.database.UserIdeaEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteIdeaViewModel : ViewModel() {

    fun handleDeleteIdea(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            UserIdeaDbRepository.deleteUserIdeaById(id)
        }
    }
}