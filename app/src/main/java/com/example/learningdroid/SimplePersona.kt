package com.example.learningdroid

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimplePersona(
    val name: String?,
    val age: Int?,
    val height: Int?,
    val weight: Int?
): Parcelable
