@file:Suppress("WildcardImport")

package com.tobianoapps.zeldareference.util

import android.util.Log
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.tobianoapps.zeldareference.api.models.*
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object Extensions {

    fun Class<*>.getPath(): String {
        return when (this) {
            Bosses::class.java, Games::class.java -> this.simpleName
            else -> ""
        }
    }

    fun String.unixTime(): Long? {
        return try {
            (LocalDate
                .parse(this, DateTimeFormatter.ofPattern("MMMM d, yyyy"))
                .atStartOfDay(ZoneId.of("UTC")))
                .toInstant()
                .epochSecond
        } catch (e: DateTimeParseException) {
            Log.e("unixTime()", "Format need to be 'MMMM d, yyyy'")
            null
        }
    }


    fun ViewDataBinding.toggleLoadingView(show: Boolean) =
        when (show) {
            true -> this.root.visibility = View.VISIBLE
            else -> this.root.visibility = View.GONE
        }

    /**
     * A lazy property that gets cleaned up when the fragment's view is destroyed.
     *
     * Accessing this variable while the fragment's view is destroyed will throw NPE.
     *
     * From: [https://github.com/android/architecture-components-samples/blob/
     * 7466ae2015bb9bd9cf2c435a350070b29d71fd2b/
     * GithubBrowserSample/app/src/main/java/com/android/example/github/util/AutoClearedValue.kt]
     *
     * See also: [https://github.com/android/architecture-components-samples/pull/951/commits/
     * 5c8fb401a4c907089d201ab905366bb2546acb3e]
     */
    class AutoClearedValue<T : Any>(val fragment: Fragment) : ReadWriteProperty<Fragment, T> {
        private var _value: T? = null

        init {
            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                val viewLifecycleOwner = it ?: return@Observer

                viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        _value = null
                    }
                })
            }

            fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onCreate(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.observeForever(
                        viewLifecycleOwnerLiveDataObserver
                    )
                }

                override fun onDestroy(owner: LifecycleOwner) {
                    fragment.viewLifecycleOwnerLiveData.removeObserver(
                        viewLifecycleOwnerLiveDataObserver
                    )
                }
            })
        }

        override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
            return _value ?: throw IllegalStateException(
                "should never call auto-cleared-value get when it might not be available"
            )
        }

        override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
            _value = value
        }
    }

    /**
     * Creates an [AutoClearedValue] associated with this fragment.
     */
    fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)

}
