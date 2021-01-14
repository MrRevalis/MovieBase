package com.example.moviebase.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.SearchModelFolder.SearchResultListModel
import com.example.moviebase.R
import kotlinx.android.synthetic.main.search_result_row.view.*

class SearchResultsAdapter(): RecyclerView.Adapter<SearchResultsAdapter.Holder>() {
    inner class Holder(view: View): RecyclerView.ViewHolder(view)

    private val list = mutableListOf<SearchResultListModel>()
    private val hidden = mutableListOf<SearchResultListModel.ResultType>()
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.search_result_row, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        bindImage(holder.itemView.itemImage, imageSource + list[position].poster)
        Log.d("[ITEM]", "${list[position].name} ${list[position].poster}")
        holder.itemView.itemName.text = list[position].name
        holder.itemView.itemOverview.text = list[position].description
    }

    override fun getItemCount() = list.size

    fun clearList(){
        list.clear()
        notifyDataSetChanged()
    }

    fun addResults(results: List<Any>){
        val newList = mutableListOf<SearchResultListModel>()
        for(item in results){
            newList.add(SearchResultListModel(item))
        }
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun changeGroupVisibility(group: String){
        when(group){
            "movie" -> if(!hidden.contains(SearchResultListModel.ResultType.MOVIE)) hidden.add(SearchResultListModel.ResultType.MOVIE)
            "person" -> if(!hidden.contains(SearchResultListModel.ResultType.PERSON)) hidden.add(SearchResultListModel.ResultType.PERSON)
            "show" -> if(!hidden.contains(SearchResultListModel.ResultType.SHOW)) hidden.add(SearchResultListModel.ResultType.SHOW)
        }
        notifyDataSetChanged()
    }
}