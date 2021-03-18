package com.example.moviebase.Fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebase.Adapter.FavouriteAdapter
import com.example.moviebase.R
import com.example.moviebase.ViewModel.ShowViewModel
import kotlinx.android.synthetic.main.fragment_favourite_list.*
import kotlinx.android.synthetic.main.fragment_favourite_list.view.*

class FavouriteList : Fragment() {
    private lateinit var mShowViewModel: ShowViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) //egzamin zad1
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

        //EGZAMIN zad2
       if(mShowViewModel.showArg.value.isNullOrEmpty())  mShowViewModel.showArg.value = "ev"

        mShowViewModel.fullList.observe(viewLifecycleOwner, Observer { items->
            adapter.setData(items)
        })

        return view
    }
    //EGZAMIN zad1
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.favlist_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.showFav -> {
                textView.text = getString(R.string.favouriteList)
                mShowViewModel.showArg.value = "favourite"
                true
            }
            R.id.showToWatch -> {
                textView.text = getString(R.string.toWatchList)
                mShowViewModel.showArg.value = "toWatch"
                true
            }
            R.id.showAll -> {
                textView.text = getString(R.string.myList)
                mShowViewModel.showArg.value = "ev"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        fun newInstance() = FavouriteList()
    }
}