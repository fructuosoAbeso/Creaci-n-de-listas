package es.ua.eps.filmoteca

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class AlternativaActivity : AppCompatActivity() {

    private val mode = Mode.Layouts // Cambia a Mode.Compose para usar Compose

    private val DarkColors = darkColorScheme(
        primary = Color(0xFFBB86FC),
        onPrimary = Color.White,
        background = Color(0xFF121212),
        onBackground = Color.White,
        surface = Color(0xFF1E1E1E),
        onSurface = Color.White
    )
    private val LightColors = lightColorScheme()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (mode) {
            Mode.Layouts -> initLayouts()
            Mode.Compose -> initCompose()
        }
    }

    // -------------------------
    // RecyclerView Layout
    // -------------------------
    private fun initLayouts() {
        setContentView(R.layout.activity_alternativa)

        val recyclerView = findViewById<RecyclerView>(R.id.rvPeliculas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FilmRecyclerAdapter(this, FilmDataSource.films)
    }

    // -------------------------
    // Compose
    // -------------------------
    private fun initCompose() {
        setContent {
            FilmotecaTheme {
                FilmListScreen()
            }
        }
    }

    @Composable
    fun FilmotecaTheme(
        darkTheme: Boolean = true,
        content: @Composable () -> Unit
    ) {
        val colors = if (darkTheme) DarkColors else LightColors
        MaterialTheme(colorScheme = colors, content = content)
    }

    @Composable
    fun FilmItem(film: Film, onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = if (film.imageResId != 0) film.imageResId else R.mipmap.ic_launcher),
                contentDescription = film.title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = film.title ?: "<Sin título>",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Director: ${film.director ?: "Desconocido"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
                Text(
                    text = "Año: ${film.year}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }
        }
    }

    @Composable
    fun FilmListScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            LazyColumn {
                itemsIndexed(FilmDataSource.films) { index, film ->
                    FilmItem(film = film) {
                        val intent = Intent(this@AlternativaActivity, FilmDataActivity::class.java)
                        intent.putExtra(FilmDataActivity.EXTRA_FILM_INDEX, index)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
