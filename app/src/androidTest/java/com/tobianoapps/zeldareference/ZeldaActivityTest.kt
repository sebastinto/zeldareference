package com.tobianoapps.zeldareference

import androidx.test.core.app.ActivityScenario
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
@HiltAndroidTest
class ZeldaActivityTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun zeldaActivity_start_doesNotCrash() {
        ActivityScenario.launch(ZeldaActivity::class.java)
    }
}