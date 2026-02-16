package es.ua.eps.filmoteca

object FilmDataSource {
    val films: MutableList<Film> = mutableListOf()

    init {
        films.add(Film().apply {
            title = "Regreso al futuro"
            director = "Robert Zemeckis"
            imageResId = R.drawable.regreso
            comments = ""
            format = Film.FORMAT_DIGITAL
            genre = Film.GENRE_SCIFI
            imdbUrl = "http://www.imdb.com/title/tt0088763"
            year = 1985
        })

        films.add(Film().apply {
            title = "Titanic"
            director = "James Cameron"
            imageResId = R.drawable.titanic
            comments = ""
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_DRAMA
            imdbUrl = "https://www.imdb.com/title/tt0120338/"
            year = 1997
        })

        films.add(Film().apply {
            title = "The Dark Knight"
            director = "Christopher Nolan"
            imageResId = R.drawable.dark_knight
            comments = ""
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_ACTION
            imdbUrl = "https://www.imdb.com/title/tt0468569/"
            year = 2008
        })

        films.add(Film().apply {
            title = "Pulp Fiction"
            director = "Quentin Tarantino"
            imageResId = R.drawable.pulp_fiction
            comments = ""
            format = Film.FORMAT_DVD
            genre = Film.GENRE_COMEDY
            imdbUrl = "https://www.imdb.com/title/tt0110912/"
            year = 1994
        })

        films.add(Film().apply {
            title = "Inception"
            director = "Christopher Nolan"
            imageResId = R.drawable.inception
            comments = ""
            format = Film.FORMAT_DIGITAL
            genre = Film.GENRE_SCIFI
            imdbUrl = "https://www.imdb.com/title/tt1375666/"
            year = 2010
        })

        films.add(Film().apply {
            title = "Forrest Gump"
            director = "Robert Zemeckis"
            imageResId = R.drawable.forrest_gump
            comments = ""
            format = Film.FORMAT_DVD
            genre = Film.GENRE_DRAMA
            imdbUrl = "https://www.imdb.com/title/tt0109830/"
            year = 1994
        })

        films.add(Film().apply {
            title = "Gladiator"
            director = "Ridley Scott"
            imageResId = R.drawable.gladiator_banner
            comments = ""
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_ACTION
            imdbUrl = "https://www.imdb.com/title/tt0172495/"
            year = 2000
        })

        films.add(Film().apply {
            title = "The Matrix"
            director = "The Wachowskis"
            imageResId = R.drawable.matrix
            comments = ""
            format = Film.FORMAT_DIGITAL
            genre = Film.GENRE_SCIFI
            imdbUrl = "https://www.imdb.com/title/tt0133093/"
            year = 1999
        })

        films.add(Film().apply {
            title = "The Godfather"
            director = "Francis Ford Coppola"
            imageResId = R.drawable.gladiator_banner
            comments = ""
            format = Film.FORMAT_DVD
            genre = Film.GENRE_DRAMA
            imdbUrl = "https://www.imdb.com/title/tt0068646/"
            year = 1972
        })

        films.add(Film().apply {
            title = "Jurassic Park"
            director = "Steven Spielberg"
            imageResId = R.drawable.parque_jurasico
            comments = ""
            format = Film.FORMAT_BLURAY
            genre = Film.GENRE_SCIFI
            imdbUrl = "https://www.imdb.com/title/tt0107290/"
            year = 1993
        })
    }
}
