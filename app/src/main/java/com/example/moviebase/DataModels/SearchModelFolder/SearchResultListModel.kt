package com.example.moviebase.DataModels.SearchModelFolder

class SearchResultListModel(item: Any) {
    val poster: String
    val name: String
    val date: String
    val description: String

    init {
        when(item){
            is MovieSearchResult -> {
                poster = item.poster_path
                name = item.title
                date = item.release_date
                description = item.overview
            }
            is PeopleSearchResult -> {
                poster = item.profile_patch
                name = item.name
                date = ""
                description = item.known_for_department
            }
            is TvSearchResult -> {
                poster = item.poster_path
                name = item.name
                date = item.first_air_date
                description = item.overview
            }
            else -> {
                poster = ""
                name = ""
                date = ""
                description = ""
            }
        }
    }
}