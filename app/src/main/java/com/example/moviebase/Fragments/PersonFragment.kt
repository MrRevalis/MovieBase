package com.example.moviebase.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebase.Adapter.MovieTVAdapter
import com.example.moviebase.Adapter.bindImage
import com.example.moviebase.DataModels.PersonMoviesTVsFolder.PersonMovieTVCredits
import com.example.moviebase.R
import com.example.moviebase.ViewModel.PersonViewModel
import kotlinx.android.synthetic.main.fragment_person_view.*
import kotlinx.android.synthetic.main.fragment_person_view.view.*

class PersonFragment : Fragment(){
    private val args by navArgs<PersonFragmentArgs>()
    private lateinit var personViewModel: PersonViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_person_view, container, false)
        personViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        personViewModel.getPersonDetails(args.id).observe(viewLifecycleOwner, Observer { item ->
            view.personName.text = item.name
            view.birthday.text = item.birthday
            if (!item.deathday.isNullOrEmpty()) view.deathday.text = item.deathday
            view.birthPlace.text = item.place_of_birth
            view.biography.text = item.biography
            bindImage(view.imageView, imageSource + item.profile_path)
        })

        CompleteMovieTVList()

        return view
    }

    companion object{
        fun newInstance() = PersonFragment()
    }

    private fun CompleteMovieTVList(){
        personViewModel.getPersonMoviesTVs(args.id).observe(viewLifecycleOwner, Observer { item ->
            val adapter = MovieTVAdapter()
            val recyclerView = knownFromRecyclerView
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter.setData(item.cast)
        })
    }
}
