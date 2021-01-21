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
import com.example.moviebase.Adapter.bindImage
import com.example.moviebase.R
import com.example.moviebase.ViewModel.PersonViewModel
import kotlinx.android.synthetic.main.fragment_person_view.view.*

//const val imageSource: String = "https://image.tmdb.org/t/p/w500"

class PersonFragment : Fragment(){
    private val args by navArgs<PersonFragmentArgs>()
    private lateinit var personViewModel: PersonViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
       // return super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_person_view, container, false)
        personViewModel = ViewModelProvider(this).get(PersonViewModel::class.java)

        Log.d("test","jestem tu")
        personViewModel.getPersonDetails(args.id).observe(viewLifecycleOwner, Observer { item ->
            view.personName.text = item.name
            view.birthday.text = item.birthday
            view.deathday.text = item.deathday
            /*if (item.deathday != "2131755061"){ //|| item.deathday != null){
                view.deathday.text = item.deathday
                Log.d("test","nie zmarł")
            }
            else{
                view.deathday.text = R.string.isLiving.toString()
                Log.d("test"," zmarł")
            }*/
            view.birthPlace.text = item.place_of_birth
            view.biography.text = item.biography
            bindImage(view.imageView, imageSource + item.profile_path)
        })


        return view
    }

    companion object{
        fun newInstance() = PersonFragment()
    }
}
