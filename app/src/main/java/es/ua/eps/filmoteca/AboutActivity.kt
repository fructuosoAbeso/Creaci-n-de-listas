package es.ua.eps.filmoteca

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource

class AboutActivity : AppCompatActivity() {

    private val mode = Mode.Layouts

    // Colores para modo oscuro
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
        initUI()
    }

    private fun initUI() {
        when (mode) {
            Mode.Layouts -> initLayouts()
            Mode.Compose -> initCompose()
        }
    }

    private fun initLayouts() {
        setContentView(R.layout.activity_about)

        findViewById<Button>(R.id.btnWeb).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnSoporte).setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:soporte@example.com")
            }
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnVolver).setOnClickListener {
            finish()
        }
    }


    @Composable
    fun FilmotecaTheme(
        darkTheme: Boolean = true,
        content: @Composable () -> Unit
    ) {
        val colors = if (darkTheme) DarkColors else LightColors
        MaterialTheme(
            colorScheme = colors,
            content = content
        )
    }

    private fun initCompose() {
        setContent {
            FilmotecaTheme(darkTheme = true) {
                AboutScreen(
                    onWebClick = {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
                        startActivity(intent)
                    },
                    onSupportClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:soporte@example.com")
                        }
                        startActivity(intent)
                    },
                    onBackClick = { finish() }
                )
            }
        }
    }

    @Composable
    fun AboutScreen(
        onWebClick: () -> Unit,
        onSupportClick: () -> Unit,
        onBackClick: () -> Unit
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.creado_por),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Image(
                    painter = painterResource(id = R.drawable.mi_foto),
                    contentDescription = stringResource(id = R.string.foto_autor),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onWebClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(id = R.string.ir_al_sitio))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onSupportClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(id = R.string.obtener_soporte))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = stringResource(id = R.string.volver))
                }
            }
        }
    }
}
