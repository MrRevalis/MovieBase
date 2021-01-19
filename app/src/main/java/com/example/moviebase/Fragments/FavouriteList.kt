package com.example.moviebase.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebase.Adapter.FavouriteAdapter
import com.example.moviebase.R
import com.example.moviebase.ViewModel.ShowViewModel
import kotlinx.android.synthetic.main.fragment_favourite_list.view.*

class FavouriteList : Fragment() {
    private lateinit var mShowViewModel: ShowViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite_list, container, false)

        mShowViewModel = ViewModelProvider(this).get(ShowViewModel::class.java)

        val adapter = FavouriteAdapter()
        val recyclerView = view.favouriteRecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mShowViewModel.getEverything.observe(viewLifecycleOwner, Observer { items->
            adapter.setData(items)
        })

        return view
    }

    companion object {
        fun newInstance() = FavouriteList()
    }
}