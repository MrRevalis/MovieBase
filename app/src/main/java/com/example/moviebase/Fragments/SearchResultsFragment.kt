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
import androidx.recyclerview.widget.RecyclerView
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

        //pobierz argument, jezeli nei jest ustawiony w viewmodelu
        if(searchViewModel.searchQuery.isNullOrEmpty()) searchViewModel.searchQuery = args.searchQuery

        searchViewModel.movies.observe(viewLifecycleOwner, Observer {
            if(!searchViewModel.isHidden("movie")) searchResultsAdapter.addResults(it.results)
            moviesLabel.text = "${getString(R.string.movies)}: ${it.total_results}"
        })
        searchViewModel.people.observe(viewLifecycleOwner, Observer {
            if(!searchViewModel.isHidden("people")) searchResultsAdapter.addResults(it.results)
            peopleLabel.text = "${getString(R.string.people)}: ${it.total_results}"
        })
        searchViewModel.shows.observe(viewLifecycleOwner, Observer {
            if(!searchViewModel.isHidden("show")) searchResultsAdapter.addResults(it.results)
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
            addOnScrollListener(object: RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = fragmentLayoutManager.itemCount
                    val visibleItemCount = fragmentLayoutManager.childCount
                    val lastVisibleItem = fragmentLayoutManager.findLastVisibleItemPosition()

                    if(lastVisibleItem + visibleItemCount > totalItemCount){
                        searchResultsAdapter.loadMore()
                    }
                }
            })
        }
        if(!searchViewModel.searched)
            searchViewModel.search()
        if(!searchViewModel.searchQuery.isNullOrEmpty())
            searchQueryInput.text = Editable.Factory.getInstance().newEditable(searchViewModel.searchQuery)

        //onclick
        searchBtn.setOnClickListener {
            searchViewModel.searchQuery = searchQueryInput.text.toString()
            searchResultsAdapter.clearList()
            searchViewModel.search()
        }

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
        //update badges and results
        if(searchViewModel.isHidden("movie")){
            moviesLabel.background = resources.getDrawable(R.drawable.badge_disabled, context?.theme!!)
            searchResultsAdapter.hideElements("movie")
        }
        if(searchViewModel.isHidden("people")){
            peopleLabel.background = resources.getDrawable(R.drawable.badge_disabled, context?.theme!!)
            searchResultsAdapter.hideElements("people")
        }
        if(searchViewModel.isHidden("show")){
            showsLabel.background = resources.getDrawable(R.drawable.badge_disabled, context?.theme!!)
            searchResultsAdapter.hideElements("show")
        }
    }

    companion object {
        fun newInstance() = SearchResultsFragment()
    }
}