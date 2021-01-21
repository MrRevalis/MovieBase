package com.example.moviebase.Adapter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.databinding.BindingAdapter
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}

/*fun setPictureWithBackground(imgView: ImageView, layout: ConstraintLayout, imgUrl: String){
    var bitmap = Glide.with(layout.context)
        .asBitmap()
        .load(imgUrl)
        .into(object : CustomTarget<Bitmap?>(){
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap?>?) {
                //Log.d("komunikat", "gotowe")
                var color = resource.getColor(10, 10)
                layout.setBackgroundColor(color.toArgb())
                imgView.setImageBitmap(resource)
            }
            override fun onLoadCleared(placeholder: Drawable?) {
                TODO("Not yet implemented")
            }
        })

    Log.d("komunikat", "TEST")
}*/