package com.example.moviebase.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebase.Adapter.TrendingAdapter
import com.example.moviebase.R
import com.example.moviebase.ViewModel.MovieViewModel
import com.example.moviebase.ViewModel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment() {

    private lateinit var mMovieViewModel: MovieViewModel
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        mMovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)

        val adapter = TrendingAdapter()
        val recyclerView = view.recyclerViewTrending
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        mMovieViewModel.getTrending("movie", "day").observe(viewLifecycleOwner, Observer { items ->
            adapter.setData(items.results)
        })

        //do testów
        //searchViewModel.searchQuery = "carrie"
        searchViewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
            for(movie in movies)
                Log.d("[FMOV]", movie.title)
        })
        searchViewModel.getPeople().observe(viewLifecycleOwner, Observer { people ->
            for(person in people)
                Log.d("[FPER]", person.name)
        })
        searchViewModel.getTvShows().observe(viewLifecycleOwner, Observer { shows ->
            for(show in shows)
                Log.d("[FSHOW]", show.name)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchBtn.setOnClickListener {
            searchViewModel.searchQuery = searchQueryInput.text.toString()
            searchQueryInput.text = null
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}