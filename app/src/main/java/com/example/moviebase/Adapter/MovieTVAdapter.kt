package com.example.moviebase.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.R

class MovieTVAdapter : RecyclerView.Adapter<MovieTVAdapter.MyViewHolder>() {
    private var movieTVList = emptyList<Int>() //zmienić Int na filmy seriale liste
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.trending_item, parent, false)

        view.setOnClickListener {
            //Toast.makeText(parent.context, view.trendingTitle.text, Toast.LENGTH_SHORT).show()
        }

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movieTVList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

/*   private var castList = emptyList<Cast>()
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.actor_row, parent, false)
        )
        return view
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem = castList[position]
        bindImage(holder.itemView.actorPicture, imageSource + currentItem.profile_path)
        holder.itemView.actorName.text = currentItem.original_name
        holder.itemView.actorRole.text = currentItem.character

        holder.itemView.setOnClickListener {
            Log.d("komunikat", "Przejdz do widoku aktora")
            val action = MovieViewDirections.actionMovieViewToPersonFragment(currentItem.id)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(data: List<Cast>) {
        this.castList = data
        notifyDataSetChanged()
    }

*/