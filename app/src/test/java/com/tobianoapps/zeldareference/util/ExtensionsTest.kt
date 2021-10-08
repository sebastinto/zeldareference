package com.tobianoapps.zeldareference.util

import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.tobianoapps.zeldareference.ZeldaActivity
import com.tobianoapps.zeldareference.api.models.Bosses
import com.tobianoapps.zeldareference.api.models.Games
import com.tobianoapps.zeldareference.util.Extensions.getPath
import com.tobianoapps.zeldareference.util.Extensions.unixTime
import org.junit.Test

@SmallTest
class ExtensionsTest {

    @Test
    fun `getPath() with valid class, return correct value`() {
        val results = listOf(Bosses::class.java, Games::class.java)

        results.forEach {
            assertThat(it.getPath()).isEqualTo(it.simpleName)
        }
    }

    @Test
    fun `getPath() with invalid class, returns empty string`() {
        val result = ZeldaActivity::class.java.getPath()
        assertThat(result).isEmpty()
    }

    @Test
    fun `unixTime() with valid format string, return correct unix time`() {
        val stringDate = "January 1, 2021"
        val result = stringDate.unixTime()
        assertThat(result).isEqualTo(1609459200)
    }

    @Test
    fun `unixTime() with invalid format string, return null`() {
        val stringDate = "Jan 1, 2021"
        val result = stringDate.unixTime()
        assertThat(result).isEqualTo(null)
    }
}
