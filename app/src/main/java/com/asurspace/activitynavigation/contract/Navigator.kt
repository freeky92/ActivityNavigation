package com.asurspace.activitynavigation.contract

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.asurspace.activitynavigation.model.Options

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator{

    fun showOpenBoxScreen(options: Options)

    fun showOptionsScreen(options: Options)

    fun showAboutScreen()

    fun showCongratulationScreen()

    fun goBack()

    fun goToMenu()

    fun <T: Parcelable> provideResult(result: T)

    fun <T: Parcelable> listenResults(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)

}