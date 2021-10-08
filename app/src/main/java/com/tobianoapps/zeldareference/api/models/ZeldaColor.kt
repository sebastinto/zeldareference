package com.tobianoapps.zeldareference.api.models

import android.graphics.Color
import androidx.annotation.ColorInt

enum class ZeldaColor(@ColorInt val color: Int) {
    BRIGHT_GREEN(Color.parseColor("#00cc00")),
    DEEP_GREEN(Color.parseColor("#1bb04c")),
    CAMO_GREEN(Color.parseColor("#508372")),
    DARK_GREEN(Color.parseColor("#494b4b")),
    DARKER_GREEN(Color.parseColor("#0e5135")),
    BASE_GREEN(Color.parseColor("#0d9263")),
    YELLOW_GREEN(Color.parseColor("#70af23")),
    INTENSE_GREEN(Color.parseColor("#4aba91")),
    BLUE(Color.parseColor("#38b6f1"));

    companion object {
        val randomColor: Int
        get() = values().random().color
    }
}
