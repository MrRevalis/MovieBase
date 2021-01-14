package com.example.moviebase.DataModels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HelperClass(
    val ID: Int,
    val type: String
) : Parcelable
