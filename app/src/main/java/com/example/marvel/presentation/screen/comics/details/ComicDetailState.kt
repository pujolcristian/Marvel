package com.example.marvel.presentation.screen.comics.details

import com.example.marvel.domain.model.ComicDetail

data class ComicDetailState(
    val comicDetail: ComicDetail = ComicDetail(),
    val isLoading: Boolean = false
)
