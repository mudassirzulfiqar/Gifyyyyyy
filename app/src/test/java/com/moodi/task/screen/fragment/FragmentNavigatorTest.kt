package com.moodi.task.screen.fragment

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

/**
 * Test class for [FragmentNavigator]
 * @see FragmentNavigator
 *
 * I choose to use Mockk to mock the FragmentManager.
 */
class FragmentNavigatorTest {

    private var fragmentManager = mockk<FragmentManager>(relaxed = true)

    private var mockTransaction: FragmentTransaction = mockk()

    private lateinit var fragmentNavigator: FragmentNavigator

    @Before
    fun before() {
        fragmentNavigator = FragmentNavigator(fragmentManager)
    }

    @Test
    fun `verify Random fragment when pass it in navigate `() {

        every { fragmentManager.findFragmentByTag(RandomFragment.TAG) } returns null
        every { fragmentManager.beginTransaction() } returns mockk(relaxed = true)

        fragmentNavigator.navigate(RandomFragmentItem)

        verify(exactly = 0) { mockTransaction.hide(any()) }
        verify(exactly = 0) { mockTransaction.show(any()) }

        assert(fragmentNavigator.getCurrentFragment() != null)

    }

    @Test
    fun `verify Search fragment when pass it in navigate `() {
        every { fragmentManager.findFragmentByTag(SearchFragment.TAG) } returns null
        every { fragmentManager.beginTransaction() } returns mockk(relaxed = true)

        fragmentNavigator.navigate(SearchFragmentItem)
        verify(exactly = 0) { mockTransaction.hide(any()) }
        verify(exactly = 0) { mockTransaction.show(any()) }

        assert(fragmentNavigator.getCurrentFragment() != null)
    }

}