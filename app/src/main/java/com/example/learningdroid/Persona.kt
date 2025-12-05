package com.example.learningdroid

import android.os.Parcel
import android.os.Parcelable

data class Persona(
    val name: String?,
    val age: Int?,
    val hobby: String?,
    val email: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(age)
        parcel.writeString(hobby)
        parcel.writeString(email)
    }

    companion object CREATOR : Parcelable.Creator<Persona> {
        override fun createFromParcel(parcel: Parcel): Persona {
            return Persona(parcel)
        }
        override fun newArray(size: Int): Array<Persona?> {
            return arrayOfNulls(size)
        }
    }
}
