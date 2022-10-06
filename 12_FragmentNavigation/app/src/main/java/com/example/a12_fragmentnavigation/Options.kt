package com.example.a12_fragmentnavigation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Options(
    val boxCount: Int,
    val isTimerEnable: Boolean
) : Parcelable {

    companion object {
        @JvmStatic
        val DEFAULT = Options(3, false)
    }
}