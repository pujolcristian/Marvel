package com.example.marvel.presentation.screen.comics.home

import com.example.marvel.domain.model.Comic

data class ComicsHomeState(
    val isLoading: Boolean? = true,
    val comics: List<Comic>? = emptyList()
)