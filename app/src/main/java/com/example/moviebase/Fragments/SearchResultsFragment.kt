package com.example.moviebase.Fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebase.Adapter.SearchResultsAdapter
import com.example.moviebase.R
import com.example.moviebase.ViewModel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search_results.*

class SearchResultsFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var fragmentLayoutManager: LinearLayoutManager
    private lateinit var searchResultsAdapter: SearchResultsAdapter

    private val args: SearchResultsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        fragmentLayoutManager = LinearLayoutManager(context)
        searchResultsAdapter = SearchResultsAdapter()

        searchViewModel.searchQuery = args.searchQuery

        searchViewModel.getMovies().observe(viewLifecycleOwner, Observer { movies ->
            searchResultsAdapter.addResults(movies)
        })
        searchViewModel.getPeople().observe(viewLifecycleOwner, Observer { people ->
            searchResultsAdapter.addResults(people)
        })
        searchViewModel.getTvShows().observe(viewLifecycleOwner, Observer { shows ->
            searchResultsAdapter.addResults(shows)
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchResultsList.apply {
            adapter = searchResultsAdapter
            layoutManager = fragmentLayoutManager
        }

        if(!searchViewModel.searchQuery.isNullOrEmpty())
            searchQueryInput.text = Editable.Factory.getInstance().newEditable(searchViewModel.searchQuery)
    }

    companion object {
        fun newInstance() = SearchResultsFragment()
    }
}