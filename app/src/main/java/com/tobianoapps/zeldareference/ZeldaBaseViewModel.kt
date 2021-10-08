package com.tobianoapps.zeldareference

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class ZeldaBaseViewModel(
    application: Application
) : AndroidViewModel(application) {

    abstract fun callApi(path: String)
}
