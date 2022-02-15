package com.asurspace.activitynavigation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Options(
    val boxCount: Int,
    val isTimerEnable: Boolean
) : Parcelable {

    companion object {
        @JvmStatic
        val DEFAULT = Options(
            boxCount = 3,
            isTimerEnable = false
        )
    }
}