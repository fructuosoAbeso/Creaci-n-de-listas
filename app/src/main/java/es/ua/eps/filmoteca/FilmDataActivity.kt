package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FilmDataActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_FILM_INDEX = "EXTRA_FILM_INDEX"
    }

    private val mode = Mode.Layouts
    private val DarkColors = darkColorScheme(
        primary = Color(0xFFBB86FC),
        onPrimary = Color.White,
        background = Color(0xFF121212),
        onBackground = Color.White,
        surface = Color(0xFF1E1E1E),
        onSurface = Color.White
    )
    private val LightColors = lightColorScheme()

    private lateinit var film: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtenemos índice de la película
        val index = intent.getIntExtra(EXTRA_FILM_INDEX, -1)
        if (index in FilmDataSource.films.indices) {
            film = FilmDataSource.films[index]
        } else {
            finish() // Si índice inválido, cerramos la actividad
            return
        }

        initUI()
    }

    private fun initUI() {
        when (mode) {
            Mode.Layouts -> initLayouts()
            Mode.Compose -> initCompose()
        }
    }

    // ----------------------------
    // LAYOUTS — Usando XML
    // ----------------------------
    private fun initLayouts() {
        setContentView(R.layout.activity_film_data)

        // Referencias a los elementos
        val imgCartel = findViewById<ImageView>(R.id.imgCartel)
        val tvNombre = findViewById<TextView>(R.id.tvNombrePelicula)
        val tvDirector = findViewById<TextView>(R.id.tvDirector)
        val tvAnyo = findViewById<TextView>(R.id.tvAnyo)
        val tvGenero = findViewById<TextView>(R.id.tvGenero)
        val tvFormato = findViewById<TextView>(R.id.tvFormato)
        val btnVerImdb = findViewById<Button>(R.id.btnVerImdb)
        val btnVolver = findViewById<Button>(R.id.btnVolverPrincipal)
        val btnEditar = findViewById<Button>(R.id.btnEditarPelicula)

        // Seteamos los datos
        imgCartel.setImageResource(if (film.imageResId != 0) film.imageResId else R.mipmap.ic_launcher)
        tvNombre.text = film.title
        tvDirector.text = "Director: ${film.director ?: "Desconocido"}"
        tvAnyo.text = "Año: ${film.year}"
        tvGenero.text = "Género: ${genreToString(film.genre)}"
        tvFormato.text = "Formato: ${formatToString(film.format)}"

        // Botón IMDb
        btnVerImdb.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(film.imdbUrl))
            startActivity(intent)
        }

        // Botón Volver
        btnVolver.setOnClickListener { finish() }

        // Botón Editar
        btnEditar.setOnClickListener {
            val intent = Intent(this, FilmEditActivity::class.java)
            intent.putExtra(EXTRA_FILM_INDEX, FilmDataSource.films.indexOf(film))
            startActivity(intent)
        }
    }

    // ----------------------------
    // COMPOSE VERSION
    // ----------------------------
    private fun initCompose() {
        setContent {
            FilmotecaTheme {
                FilmDataScreen(
                    filmTitle = film.title ?: "<Sin título>",
                    filmDirector = film.director ?: "Desconocido",
                    filmYear = film.year.toString(),
                    filmGenre = genreToString(film.genre),
                    filmFormat = formatToString(film.format),
                    imageResId = film.imageResId,
                    onVerImdb = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(film.imdbUrl))
                        startActivity(intent)
                    },
                    onVolverPrincipal = { finish() }
                )
            }
        }
    }

    @Composable
    fun FilmotecaTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
        val colors = if (darkTheme) DarkColors else LightColors
        MaterialTheme(colorScheme = colors, content = content)
    }

    @Composable
    fun FilmDataScreen(
        filmTitle: String,
        filmDirector: String,
        filmYear: String,
        filmGenre: String,
        filmFormat: String,
        imageResId: Int,
        onVerImdb: () -> Unit,
        onVolverPrincipal: () -> Unit
    ) {
        Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {

                Row(verticalAlignment = Alignment.Top) {
                    Image(
                        painter = painterResource(id = if (imageResId != 0) imageResId else R.mipmap.ic_launcher),
                        contentDescription = "Cartel película",
                        modifier = Modifier.size(width = 120.dp, height = 180.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(text = filmTitle, fontSize = 20.sp, color = MaterialTheme.colorScheme.onBackground)
                        Text(text = "Director: $filmDirector", fontSize = 16.sp)
                        Text(text = "Año: $filmYear", fontSize = 16.sp)
                        Text(text = "Género: $filmGenre", fontSize = 16.sp)
                        Text(text = "Formato: $filmFormat", fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onVerImdb) { Text("Ver en IMDb") }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = onVolverPrincipal) { Text("Volver") }
                    }
                }
            }
        }
    }

    // ----------------------------
    // Funciones auxiliares
    // ----------------------------
    private fun genreToString(genre: Int) = when (genre) {
        Film.GENRE_ACTION -> "Acción"
        Film.GENRE_COMEDY -> "Comedia"
        Film.GENRE_DRAMA -> "Drama"
        Film.GENRE_SCIFI -> "Ciencia Ficción"
        Film.GENRE_HORROR -> "Terror"
        else -> "Desconocido"
    }

    private fun formatToString(format: Int) = when (format) {
        Film.FORMAT_DVD -> "DVD"
        Film.FORMAT_BLURAY -> "BluRay"
        Film.FORMAT_DIGITAL -> "Digital"
        else -> "Desconocido"
    }
}
