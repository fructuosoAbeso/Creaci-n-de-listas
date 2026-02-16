package es.ua.eps.filmoteca

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import android.widget.ImageView
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FilmEditActivity : AppCompatActivity() {

    private val mode = Mode.Layouts // Cambia a Mode.Compose para probar Compose
    private lateinit var film: Film

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

        val index = intent.getIntExtra(FilmDataActivity.EXTRA_FILM_INDEX, -1)
        if (index !in FilmDataSource.films.indices) finish()
        film = FilmDataSource.films[index]

        initUI()
    }

    private fun initUI() {
        when (mode) {
            Mode.Layouts -> initLayouts()
            Mode.Compose -> initCompose()
        }
    }

    // -------------------------
    // XML Layout
    // -------------------------

    private fun initLayouts() {
        setContentView(R.layout.activity_film_edit)

        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val etDirector = findViewById<EditText>(R.id.etDirector)
        val etAnyo = findViewById<EditText>(R.id.etAnyo)
        val spinnerGenero = findViewById<Spinner>(R.id.spinnerGenero)
        val spinnerFormato = findViewById<Spinner>(R.id.spinnerFormato)
        val etImdb = findViewById<EditText>(R.id.etImdb)
        val etNotas = findViewById<EditText>(R.id.etNotas)
        val imgCartel = findViewById<ImageView>(R.id.imgCartel)

        // Cargar los detalles de la película
        etTitulo.setText(film.title)
        etDirector.setText(film.director)
        etAnyo.setText(film.year.toString())
        spinnerGenero.setSelection(film.genre)
        spinnerFormato.setSelection(film.format)
        etImdb.setText(film.imdbUrl)
        etNotas.setText(film.comments)

        // Mostrar la imagen de la película
        imgCartel.setImageResource(film.imageResId)

        findViewById<Button>(R.id.btnGuardar).setOnClickListener {
            film.title = etTitulo.text.toString()
            film.director = etDirector.text.toString()
            film.year = etAnyo.text.toString().toIntOrNull() ?: film.year
            film.genre = spinnerGenero.selectedItemPosition
            film.format = spinnerFormato.selectedItemPosition
            film.imdbUrl = etImdb.text.toString()
            film.comments = etNotas.text.toString()
            setResult(RESULT_OK)
            finish()
        }

        findViewById<Button>(R.id.btnCancelar).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    // -------------------------
    // Compose Theme
    // -------------------------
    @Composable
    fun FilmotecaTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
        val colors = if (darkTheme) DarkColors else LightColors
        MaterialTheme(colorScheme = colors, content = content)
    }

    // -------------------------
    // Compose Layout
    // -------------------------
    private fun initCompose() {
        setContent {
            FilmotecaTheme(darkTheme = true) {
                FilmEditScreen(
                    film = film,
                    onGuardar = { setResult(RESULT_OK); finish() },
                    onCancelar = { setResult(RESULT_CANCELED); finish() }
                )
            }
        }
    }

    @Composable
    fun FilmEditScreen(
        film: Film,
        onGuardar: () -> Unit,
        onCancelar: () -> Unit
    ) {
        val scrollState = rememberScrollState()

        var titulo by remember { mutableStateOf(film.title ?: "") }
        var director by remember { mutableStateOf(film.director ?: "") }
        var anyo by remember { mutableStateOf(film.year.toString()) }
        var imdb by remember { mutableStateOf(film.imdbUrl ?: "") }
        var notas by remember { mutableStateOf(film.comments ?: "") }

        val generos = stringArrayResource(id = R.array.array_genero).toList()
        val formatos = stringArrayResource(id = R.array.array_formato).toList()
        val generoPrompt = stringResource(id = R.string.genero_prompt)
        val formatoPrompt = stringResource(id = R.string.formato_prompt)

        var generoExpanded by remember { mutableStateOf(false) }
        var generoSeleccionado by remember { mutableStateOf(generos.getOrElse(film.genre) { generos.first() }) }

        var formatoExpanded by remember { mutableStateOf(false) }
        var formatoSeleccionado by remember { mutableStateOf(formatos.getOrElse(film.format) { formatos.first() }) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Editando película", fontSize = 24.sp, color = MaterialTheme.colorScheme.onBackground)
            Spacer(modifier = Modifier.height(24.dp))

            // Imagen + botones
            Row(verticalAlignment = Alignment.CenterVertically) {

                Image(
                    painter = painterResource(id = film.imageResId),
                    contentDescription = "Cartel de la película",
                    modifier = Modifier.size(width = 120.dp, height = 180.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Button(onClick = { /* Tomar foto */ }, modifier = Modifier.fillMaxWidth()) {
                        Text("Tomar foto")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { /* Seleccionar imagen */ }, modifier = Modifier.fillMaxWidth()) {
                        Text("Seleccionar imagen")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campos de texto
            OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título de la película") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = director, onValueChange = { director = it }, label = { Text("Director") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = anyo, onValueChange = { anyo = it }, label = { Text("Año de estreno") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown género
            Box {
                OutlinedTextField(
                    value = generoSeleccionado,
                    onValueChange = {},
                    label = { Text(generoPrompt) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = { Icon(painter = painterResource(id = android.R.drawable.arrow_down_float), contentDescription = "Dropdown", modifier = Modifier.clickable { generoExpanded = true }) }
                )
                DropdownMenu(expanded = generoExpanded, onDismissRequest = { generoExpanded = false }, modifier = Modifier.fillMaxWidth()) {
                    generos.forEach { g ->
                        DropdownMenuItem(text = { Text(g) }, onClick = { generoSeleccionado = g; generoExpanded = false })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown formato
            Box {
                OutlinedTextField(
                    value = formatoSeleccionado,
                    onValueChange = {},
                    label = { Text(formatoPrompt) },
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    trailingIcon = { Icon(painter = painterResource(id = android.R.drawable.arrow_down_float), contentDescription = "Dropdown", modifier = Modifier.clickable { formatoExpanded = true }) }
                )
                DropdownMenu(expanded = formatoExpanded, onDismissRequest = { formatoExpanded = false }, modifier = Modifier.fillMaxWidth()) {
                    formatos.forEach { f ->
                        DropdownMenuItem(text = { Text(f) }, onClick = { formatoSeleccionado = f; formatoExpanded = false })
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = imdb, onValueChange = { imdb = it }, label = { Text("Enlace IMDB") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = notas, onValueChange = { notas = it }, label = { Text("Notas") }, modifier = Modifier.fillMaxWidth().height(120.dp))
            Spacer(modifier = Modifier.height(16.dp))

            // Botones Guardar y Cancelar
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(
                    onClick = {
                        // Guardar cambios en FilmDataSource
                        film.title = titulo
                        film.director = director
                        film.year = anyo.toIntOrNull() ?: film.year
                        film.genre = generos.indexOf(generoSeleccionado)
                        film.format = formatos.indexOf(formatoSeleccionado)
                        film.imdbUrl = imdb
                        film.comments = notas
                        onGuardar()
                    },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) { Text("Guardar", color = Color.White) }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = onCancelar,
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                ) { Text("Cancelar", color = Color.White) }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
