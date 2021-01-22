package com.example.moviebase.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebase.Adapter.PopularAdapter
import com.example.moviebase.Adapter.TrendingAdapter
import com.example.moviebase.DataModels.PopularMovieTVFolder.WhatContent
import com.example.moviebase.R
import com.example.moviebase.ViewModel.MovieViewModel
import com.example.moviebase.ViewModel.TVViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var tTVViewModel: TVViewModel
    private var timeWindow = "day"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        mMovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        tTVViewModel = ViewModelProvider(this).get(TVViewModel::class.java)

        //TRENDING
        view.trendingToday.isSelected = true

        val adapter = TrendingAdapter()
        val recyclerView = view.recyclerViewTrending
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mMovieViewModel.getTrending("all", timeWindow)
            .observe(viewLifecycleOwner, Observer { items ->
                adapter.setData(items.results)
            })

        view.trendingToday.setOnClickListener {
            view.trendingToday.isSelected = true
            view.trendingWeek.isSelected = false
            if (timeWindow != "day") {
                timeWindow = "day"
                mMovieViewModel.getTrending("all", timeWindow)
                    .observe(viewLifecycleOwner, Observer { items ->
                        adapter.setData(items.results)
                    })
            }
        }

        view.trendingWeek.setOnClickListener {
            view.trendingWeek.isSelected = true
            view.trendingToday.isSelected = false
            if (timeWindow != "week") {
                timeWindow = "week"
                mMovieViewModel.getTrending("all", timeWindow)
                    .observe(viewLifecycleOwner, Observer { items ->
                        adapter.setData(items.results)
                    })
            }
        }

        //POPULAR
        val popularAdapter = PopularAdapter()
        val popularRecyclerView = view.recyclerViewPopular
        popularRecyclerView.adapter = popularAdapter
        popularRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mMovieViewModel.getPopularMovie().observe(viewLifecycleOwner, Observer { item ->
            tTVViewModel.getPopularTV().observe(viewLifecycleOwner, Observer {  item2 ->
                popularAdapter.setData(WhatContent(item.results, item2.results).results)
            })
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBtn.setOnClickListener {
            val query = searchQueryInput.text.toString()
            searchQueryInput.text = null
            val action = MainFragmentDirections.actionMainFragmentToSearchResultsFragment(query)
            view.findNavController().navigate(action)
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}