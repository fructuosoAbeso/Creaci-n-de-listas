package es.ua.eps.filmoteca

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class FilmRecyclerAdapter(
    private val activity: AppCompatActivity,
    private val films: List<Film>
) : RecyclerView.Adapter<FilmRecyclerAdapter.FilmViewHolder>() {

    inner class FilmViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imgFilm = view.findViewById<ImageView>(R.id.imgFilm)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDirector = view.findViewById<TextView>(R.id.tvDirector)
        val tvYear = view.findViewById<TextView>(R.id.tvYear)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_film, parent, false)
        return FilmViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = films[position]
        holder.imgFilm.setImageResource(if (film.imageResId != 0) film.imageResId else R.mipmap.ic_launcher)
        holder.tvTitle.text = film.title
        holder.tvDirector.text = film.director
        holder.tvYear.text = film.year.toString()

        holder.view.setOnClickListener {
            val intent = Intent(activity, FilmDataActivity::class.java)
            intent.putExtra(FilmDataActivity.EXTRA_FILM_INDEX, position)
            activity.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = films.size
}
