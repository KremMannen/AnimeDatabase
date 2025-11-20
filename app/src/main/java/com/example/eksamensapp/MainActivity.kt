package com.example.eksamensapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eksamensapp.data.database.UserIdeaDbRepository
import com.example.eksamensapp.navigation.AppNavigation
import com.example.eksamensapp.screens.animedetails.AnimeDetailsViewModel
import com.example.eksamensapp.screens.home.AnimeHomeViewModel
import com.example.eksamensapp.screens.search.AnimeSearchViewModel
import com.example.eksamensapp.screens.useridea.AnimeUserIdeaViewModel
import com.example.eksamensapp.screens.watched.AnimeWatchedViewModel
import com.example.eksamensapp.ui.theme.EksamensAppTheme

class MainActivity : ComponentActivity() {

    private val _animeDetailsViewModel : AnimeDetailsViewModel by viewModels()
    private val _animeHomeViewModel : AnimeHomeViewModel by viewModels()
    private val _animeSearchViewModel : AnimeSearchViewModel by viewModels()
    private val _animeUserIdeaViewModel: AnimeUserIdeaViewModel by viewModels()
    private val _animeWatchedViewModel : AnimeWatchedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        UserIdeaDbRepository.initializeDatabase(applicationContext)

        enableEdgeToEdge()
        setContent {
            EksamensAppTheme {
                AppNavigation(
                    _animeHomeViewModel,
                    _animeDetailsViewModel,
                    _animeSearchViewModel,
                    _animeUserIdeaViewModel,
                    _animeWatchedViewModel
                )
                }
            }
        }
    }
