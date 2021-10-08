package com.tobianoapps.zeldareference

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import com.google.common.truth.Truth.assertThat
import com.tobianoapps.zeldareference.home.HomeFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class NavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun clickGamesButton_launchGamesFragment() {

        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Create a FragmentScenario for the HomeFragment
        val homeFragmentScenario =
            launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_MaterialComponents)

        homeFragmentScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        onView(withId(R.id.games_button)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.games_fragment)
    }

    @Test
    fun clickBossesButton_launchBossesFragment() {

        // Create a TestNavHostController
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        // Create a FragmentScenario for the HomeFragment
        val homeFragmentScenario =
            launchFragmentInContainer<HomeFragment>(themeResId = R.style.Theme_MaterialComponents)

        homeFragmentScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        onView(withId(R.id.bosses_button)).perform(click())

        assertThat(navController.currentDestination?.id).isEqualTo(R.id.bosses_fragment)
    }

    @Test
    fun backNavigationAction_inGamesFragment_navigateBackToHomeFragment() {

        // Start home activity
        ActivityScenario.launch(ZeldaActivity::class.java)

        // Navigate to GamesFragment
        onView(withId(R.id.games_button)).perform(click())

        // Verify we're in the right place
        onView(withId(R.id.fragment_games_root_layout)).check(matches(isDisplayed()))

        // Navigate back to the HomeFragment
        pressBack()

        // Verify we're in the right place
        onView(withId(R.id.fragment_home_root_layout)).check(matches(isDisplayed()))
    }

    @Test
    fun backNavigationAction_inBossesFragment_navigateBackToHomeFragment() {

        // Start home activity
        ActivityScenario.launch(ZeldaActivity::class.java)

        // Navigate to GamesFragment
        onView(withId(R.id.bosses_button)).perform(click())

        // Verify we're in the right place
        onView(withId(R.id.fragment_bosses_root_layout)).check(matches(isDisplayed()))

        // Navigate back to the HomeFragment
        pressBack()

        // Verify we're in the right place
        onView(withId(R.id.fragment_home_root_layout)).check(matches(isDisplayed()))
    }
}