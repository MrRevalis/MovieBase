package com.example.moviebase.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviebase.DataModels.SearchModelFolder.SearchResultListModel
import com.example.moviebase.R
import com.example.moviebase.ViewModel.SearchViewModel
import kotlinx.android.synthetic.main.search_result_row.view.*
import java.text.SimpleDateFormat
import java.util.*

class SearchResultsAdapter(private val viewModel: SearchViewModel): RecyclerView.Adapter<SearchResultsAdapter.Holder>() {
    inner class Holder(view: View): RecyclerView.ViewHolder(view)

    private val list = mutableListOf<SearchResultListModel>()
    private val imageSource: String = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.search_result_row, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        bindImage(holder.itemView.itemImage, imageSource + list[position].poster)
        holder.itemView.itemName.text = list[position].name
        holder.itemView.itemOverview.text = list[position].description
        val dateVals = list[position].date.split('-')
        if(dateVals.size == 3){
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, dateVals[0].toInt())
            calendar.set(Calendar.MONTH, dateVals[1].toInt())
            calendar.set(Calendar.DAY_OF_MONTH, dateVals[2].toInt())
            holder.itemView.itemDate.text = SimpleDateFormat("dd MMMM YYYY", Locale.getDefault()).format(calendar.time)
        } else
            holder.itemView.itemDate.visibility = View.GONE
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

    fun changeGroup(group: String){
        if(viewModel.isHidden(group)){
            addResults(viewModel.getGroup(group))
            viewModel.hidden.remove(viewModel.getResultType(group))
        } else {
            val type = viewModel.getResultType(group)
            val remlist = list.filter { it.type == type }

            for(item in remlist)
                list.removeAll(remlist)

            viewModel.hidden.add(type)
        }
        notifyDataSetChanged()
    }

    fun loadMore(){
        val newItems = mutableListOf<SearchResultListModel>()
        viewModel.loadNextPages().observeForever {
            for(item in it)
                newItems.add(SearchResultListModel(item))
            list.addAll(newItems)
            notifyDataSetChanged()
        }
    }
}