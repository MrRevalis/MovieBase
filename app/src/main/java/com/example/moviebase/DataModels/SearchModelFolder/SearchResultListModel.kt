package com.example.moviebase.DataModels.SearchModelFolder

class SearchResultListModel(item: Any) {
    val type: ResultType
    val poster: String
    val name: String
    val date: String
    val description: String

    init {
        when(item){
            is MovieSearchResult -> {
                type = ResultType.MOVIE
                poster = item.poster_path
                name = item.title
                date = item.release_date
                description = item.overview
            }
            is PeopleSearchResult -> {
                type = ResultType.PERSON
                poster = item.profile_path
                name = item.name
                date = ""
                description = item.known_for_department
            }
            is TvSearchResult -> {
                type = ResultType.SHOW
                poster = item.poster_path
                name = item.name
                date = item.first_air_date
                description = item.overview
            }
            else -> {
                type = ResultType.NODEFINED
                poster = ""
                name = ""
                date = ""
                description = ""
            }
        }
    }
    enum class ResultType { MOVIE, PERSON, SHOW, NODEFINED }
}