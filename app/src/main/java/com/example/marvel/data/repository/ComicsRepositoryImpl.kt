package com.example.marvel.data.repository

import com.example.marvel.data.local.dao.ComicsDao
import com.example.marvel.data.local.dao.ComicsFavoritesDao
import com.example.marvel.data.local.model.Character
import com.example.marvel.data.local.model.Creator
import com.example.marvel.data.local.model.Story
import com.example.marvel.data.remote.api.ComicsApi
import com.example.marvel.data.remote.mapper.*
import com.example.marvel.domain.model.Comic
import com.example.marvel.domain.model.ComicDetail
import com.example.marvel.domain.model.ComicFavorite
import com.example.marvel.domain.repository.ComicsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ComicsRepositoryImpl(
    private val comicsDao: ComicsDao,
    private val comicsFavoritesDao: ComicsFavoritesDao,
    private val comicsApi: ComicsApi
) : ComicsRepository {

    override suspend fun getAllComics(): Flow<List<Comic>> {
        return try {
            withContext(Dispatchers.IO) {
                val comics = async {
                    comicsApi.getAllComics(30, 10)
                }
                val response = comics.await().data.results.map { it.toComicsEntity() }
                comicsDao.insertAllComics(response)
                comicsDao.getAllComics().map { list -> list.map { it.toComics() } }
            }
        } catch (e: HttpException) {
            comicsDao.getAllComics().map { list -> list.map { it.toComics() } }
        } catch (e: Exception) {
            comicsDao.getAllComics().map { list -> list.map { it.toComics() } }
        }
    }

    override suspend fun getComicById(comicId: String): ComicDetail {
        withContext(Dispatchers.IO) {
            val responses = listOf(
                async { getCharacterComicById(comicId) },
                async { getCreatorComicById(comicId) },
                async { getStoriesComicById(comicId) }
            )

            val comicsEntity = async { comicsDao.getComicById(comicId) }
            val result = comicsEntity.await()

            comicsDao.updateComic(
                result.copy(
                    creators = responses.awaitAll()[0] as List<Creator>,
                    characters = responses.awaitAll()[1] as List<Character>,
                    stories = responses.awaitAll()[2] as List<Story>
                )
            )
        }

        return comicsDao.getComicById(comicId).toComicDetail()
    }

    private suspend fun getCharacterComicById(comicId: String): List<Character> {
        return try {
            val responseCharacters = comicsApi.getCharactersByComicId(comicId)
            responseCharacters.data.results.map { it.toCharacterComicEntity() }
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun getCreatorComicById(comicId: String): List<Creator> {
        return try {
            val responseCreators = comicsApi.getCreatorsByComicId(comicId)
            responseCreators.data.results.map { it.toCreatorComicEntity() }
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    private suspend fun getStoriesComicById(comicId: String): List<Story> {
        return try {
            val responseStories = comicsApi.getStoriesByComicId(comicId)
            responseStories.data.results.map { it.toStoryComicEntity() }
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getAllComicsFavorites(): Flow<List<ComicFavorite>> {
        return comicsFavoritesDao.getAllComicsFavorites()
            .map { entities ->
                entities.map { it.toComicFavorite() }
            }
    }

    override suspend fun updateComicFavoriteById(isFavorite: Boolean, id: String) {
        comicsFavoritesDao.updateComicFavoriteById(
            isFavorite = isFavorite,
            id = id
        )
    }

    override suspend fun saveComicFavorite(comicFavorite: ComicFavorite) {
        comicsFavoritesDao.insertComicFavorite(comicFavorite.toComicsFavoritesEntity())
    }
}