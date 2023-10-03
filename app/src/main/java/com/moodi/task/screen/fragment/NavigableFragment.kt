package com.moodi.task.screen.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.moodi.task.R
import timber.log.Timber

/**
 * This class provides an interface to make fragments navigable.
 */
sealed class NavigableFragment {
    abstract val tag: String
    abstract fun createFragment(): Fragment
}

/**
 * This class is used to navigate to [RandomFragment]
 */
object RandomFragmentItem : NavigableFragment() {
    override val tag = "random"
    override fun createFragment() = RandomFragment()
}

/**
 * This class is used to navigate to [SearchFragment]
 */
object SearchFragmentItem : NavigableFragment() {
    override val tag = "search"
    override fun createFragment() = SearchFragment()
}



/**
 * This class is used to control the navigation between the fragments.
 * @property fragmentManager
 */
class FragmentNavigator constructor(private val fragmentManager: FragmentManager) {
    /**
     * Holds the current active fragment.
     */
    private var activeFragment: Fragment? = null

    /**
     * This method is used to get the current active fragment.
     * @return [Fragment]
     */
    fun getCurrentFragment(): Fragment? {
        return activeFragment
    }

    /**
     * This is responsible for navigating to the fragment which implements [NavigableFragment]
     *
     * @param fragment
     */
    fun navigate(fragment: NavigableFragment) {
        val fragmentTag = fragment.tag

        val targetFragment = fragmentManager.findFragmentByTag(fragmentTag)
            ?: fragment.createFragment()

        fragmentManager.beginTransaction()
            .apply {
                activeFragment?.let { hide(it) }
                if (targetFragment.isAdded) {
                    show(targetFragment)
                } else {
                    add(R.id.fragment, targetFragment, fragmentTag)
                    Timber.d("$fragmentTag Fragment added")
                }
            }
            .commit()

        activeFragment = targetFragment
    }

}
