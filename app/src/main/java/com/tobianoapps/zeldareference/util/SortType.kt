package com.tobianoapps.zeldareference.util

import androidx.annotation.Keep

enum class SortField {
    DATE, NAME
}

/**
 * ASC = a to Z or 0 to 9
 * DESC = z to a or 9 to 0
 */
enum class SortDirection {
    ASC, DESC
}

@Keep
data class SortBy(
    val field: SortField,
    val direction: SortDirection? = null
)
