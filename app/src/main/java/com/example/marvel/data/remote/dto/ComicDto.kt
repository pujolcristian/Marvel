package com.example.marvel.data.remote.dto


import com.squareup.moshi.Json

data class ComicDto(
    val characters: Characters = Characters(),
    val collectedIssues: List<CollectedIssue> = listOf(),
    val collections: List<Collection> = listOf(),
    val creators: Creators = Creators(),
    val dates: List<Date> = listOf(),
    val description: String? = null,
    val diamondCode: String = "",
    val digitalId: String = "",
    val ean: String = "",
    val events: Events = Events(),
    val format: String = "",
    val id: String = "",
    val images: List<Image> = listOf(),
    val isbn: String = "",
    val issn: String = "",
    val issueNumber: String = "",
    val modified: String = "",
    val pageCount: String = "",
    val prices: List<Price> = listOf(),
    val resourceURI: String = "",
    val series: Series = Series(),
    val stories: Stories = Stories(),
    val textObjects: List<TextObject> = listOf(),
    val thumbnail: Thumbnail = Thumbnail(),
    val title: String = "",
    val upc: String = "",
    val urls: List<Url> = listOf(),
    val variantDescription: String = "",
    val variants: List<Variant> = listOf()
) {
    data class Characters(
        val available: String = "",
        val collectionURI: String = "",
        val items: List<Item> = listOf(),
        val returned: String = ""
    ) {
        data class Item(
            val name: String = "",
            val resourceURI: String = "",
            val role: String = ""
        )
    }

    data class CollectedIssue(
        val name: String = "",
        val resourceURI: String = ""
    )

    data class Collection(
        val name: String = "",
        val resourceURI: String = ""
    )

    data class Creators(
        val available: String = "",
        val collectionURI: String = "",
        val items: List<Item> = listOf(),
        val returned: String = ""
    ) {
        data class Item(
            val name: String = "",
            val resourceURI: String = "",
            val role: String = ""
        )
    }

    data class Date(
        val date: String = "",
        val type: String = ""
    )

    data class Events(
        val available: String = "",
        val collectionURI: String = "",
        val items: List<Item> = listOf(),
        val returned: String = ""
    ) {
        data class Item(
            val name: String = "",
            val resourceURI: String = ""
        )
    }

    data class Image(
        val extension: String = "",
        val path: String = ""
    )

    data class Price(
        val price: String = "",
        val type: String = ""
    )

    data class Series(
        val name: String = "",
        val resourceURI: String = ""
    )

    data class Stories(
        val available: String = "",
        val collectionURI: String = "",
        val items: List<Item> = listOf(),
        val returned: String = ""
    ) {
        data class Item(
            val name: String = "",
            val resourceURI: String = "",
            val type: String = ""
        )
    }

    data class TextObject(
        val language: String = "",
        val text: String = "",
        val type: String = ""
    )

    data class Thumbnail(
        val extension: String = "",
        val path: String = ""
    )

    data class Url(
        val type: String = "",
        val url: String = ""
    )

    data class Variant(
        val name: String = "",
        val resourceURI: String = ""
    )
}