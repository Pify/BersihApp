package com.myapps.pickrubbish

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/*@Parcelize*/
class Trash (val alamat : String, val deskripsi : String )/*: Parcelable*/ {
    constructor() : this("", "")
}