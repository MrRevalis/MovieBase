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
        searchResultsAdapter = SearchResultsAdapter(searchViewModel)

        searchViewModel.searchQuery = args.searchQuery

        searchViewModel.movies.observe(viewLifecycleOwner, Observer {
            searchResultsAdapter.addResults(it.results)
            moviesLabel.text = "${getString(R.string.movies)}: ${it.total_results}"
        })
        searchViewModel.people.observe(viewLifecycleOwner, Observer {
            searchResultsAdapter.addResults(it.results)
            peopleLabel.text = "${getString(R.string.people)}: ${it.total_results}"
        })
        searchViewModel.shows.observe(viewLifecycleOwner, Observer {
            searchResultsAdapter.addResults(it.results)
            showsLabel.text = "${getString(R.string.tvshows)}: ${it.total_results}"
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
        searchBtn.setOnClickListener {
            searchViewModel.searchQuery = searchQueryInput.text.toString()
            searchResultsAdapter.clearList()
            searchViewModel.search()
        }
        searchViewModel.search()
        if(!searchViewModel.searchQuery.isNullOrEmpty())
            searchQueryInput.text = Editable.Factory.getInstance().newEditable(searchViewModel.searchQuery)

        moviesLabel.setOnClickListener {
            searchResultsAdapter.changeGroup("movie")
            it.background = if(searchViewModel.isHidden("movie")) resources.getDrawable(R.drawable.badge_disabled, context?.theme!!)
            else resources.getDrawable(R.drawable.badge, context?.theme!!)
        }
        peopleLabel.setOnClickListener {
            searchResultsAdapter.changeGroup("people")
            it.background = if(searchViewModel.isHidden("people")) resources.getDrawable(R.drawable.badge_disabled, context?.theme!!)
            else resources.getDrawable(R.drawable.badge, context?.theme!!)
        }
        showsLabel.setOnClickListener {
            searchResultsAdapter.changeGroup("show")
            it.background = if(searchViewModel.isHidden("show")) resources.getDrawable(R.drawable.badge_disabled, context?.theme!!)
            else resources.getDrawable(R.drawable.badge, context?.theme!!)
        }
    }

    companion object {
        fun newInstance() = SearchResultsFragment()
    }
}