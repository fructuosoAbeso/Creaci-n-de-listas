package es.ua.eps.filmoteca

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class FilmAdapter(private val context: Context, private val films: List<Film>) : BaseAdapter() {

    override fun getCount(): Int = films.size
    override fun getItem(position: Int): Any = films[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_film, parent, false)
        val film = films[position]

        val imgFilm = view.findViewById<ImageView>(R.id.imgFilm)
        val txtTitulo = view.findViewById<TextView>(R.id.tvTitle)
        val txtDirector = view.findViewById<TextView>(R.id.tvDirector)

        imgFilm.setImageResource(if (film.imageResId != 0) film.imageResId else R.mipmap.ic_launcher)
        txtTitulo.text = film.title ?: "<Sin título>"
        txtDirector.text = "Director: ${film.director ?: "Desconocido"}"

        return view
    }
}
