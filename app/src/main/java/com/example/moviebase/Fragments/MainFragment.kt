package com.example.moviebase.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebase.Adapter.TrendingAdapter
import com.example.moviebase.R
import com.example.moviebase.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private lateinit var mMovieViewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        mMovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        val adapter = TrendingAdapter()
        val recyclerView = view.recyclerViewTrending
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mMovieViewModel.getTrending("all", "day").observe(viewLifecycleOwner, Observer { items ->
            adapter.setData(items.results)
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