package com.example.marvel.presentation.screen.comics.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvel.data.remote.mapper.toComicFavorite
import com.example.marvel.domain.use_case.ComicsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ComicsHomeViewModel @Inject constructor(
    private val comicsUseCase: ComicsUseCase
) : ViewModel() {

    var state by mutableStateOf(ComicsHomeState())
        private set

    init {
        onEvent(ComicsHomeEvent.GetComics)
    }


    fun onEvent(event: ComicsHomeEvent) {
        when (event) {
            is ComicsHomeEvent.SetComicFavorite -> {
                viewModelScope.launch(Dispatchers.IO) {
                    comicsUseCase.saveComicFavorite(
                        comicFavorite = event.comic
                            .toComicFavorite()
                            .copy(isFavorite = true)
                    )
                }
            }
            ComicsHomeEvent.GetComics -> getAllComics()
        }
    }

    fun getAllComics() {
        state = state.copy(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            comicsUseCase.getAllComics().collect { response ->
                state = state.copy(comics = response, isLoading = false)
            }
        }
    }
}

