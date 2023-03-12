package com.example.marvel.domain.use_case

import com.example.marvel.domain.model.Comic
import com.example.marvel.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow

class GetAllComics(private val repository: ComicsRepository) {
    suspend operator fun invoke(): Flow<List<Comic>> =
        repository.getAllComics()
}