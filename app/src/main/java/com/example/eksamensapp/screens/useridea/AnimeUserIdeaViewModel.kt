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

    fun populateDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            if (UserIdeaDbRepository.getUserIdeas().isEmpty()) {
                val sampleIdeas = listOf(
                    UserIdeaEntity(
                        title = "Space Pirates Adventure",
                        synopsis = "A group of space pirates travels across the galaxy in search of the legendary treasure of the ancient civilization. Along the way, they face rival crews, dangerous planets, and discover the true meaning of friendship.",
                        genres = "Action, Adventure, Sci-Fi"
                    ),
                    UserIdeaEntity(
                        title = "Magic Academy Chronicles",
                        synopsis = "A young student with no magical abilities enrolls in the most prestigious magic academy. Through determination and unique problem-solving skills, they prove that magic isn't the only path to greatness.",
                        genres = "Fantasy, Comedy, School"
                    ),
                    UserIdeaEntity(
                        title = "Cyber Detective 2099",
                        synopsis = "In a dystopian future where memories can be hacked, a detective with a mysterious past must solve crimes in the digital realm while uncovering a conspiracy that threatens both virtual and real worlds.",
                        genres = "Mystery, Cyberpunk, Thriller"
                    )
                )

                sampleIdeas.forEach { idea ->
                    UserIdeaDbRepository.insertUserIdea(idea)
                }
            }
        }
    }

    init {
        populateDatabase()
        showAll()
    }
}





