package com.example.moviebase.DataModels.SearchModelFolder

//uniwersalna klasa do recyclerview w searchresultsfragment
class SearchResultListModel(item: Any) {
    val id: Int
    val type: ResultType
    val poster: String
    val name: String
    val date: String
    val description: String

    init {
        when(item){
            is MovieSearchResult -> {
                id = item.id
                type = ResultType.MOVIE
                poster = item.poster_path
                name = item.title
                date = item.release_date
                description = item.overview
            }
            is PeopleSearchResult -> {
                id = item.id
                type = ResultType.PERSON
                poster = item.profile_path
                name = item.name
                date = ""
                var descFactory = item.known_for_department
                for(movie in item.known_for)
                    descFactory += ", ${if(movie.media_type == "movie") movie.title else movie.name}"
                description = descFactory
            }
            is TvSearchResult -> {
                id = item.id
                type = ResultType.SHOW
                poster = item.poster_path
                name = item.name
                date = item.first_air_date
                description = item.overview
            }
            else -> {
                id = -1
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