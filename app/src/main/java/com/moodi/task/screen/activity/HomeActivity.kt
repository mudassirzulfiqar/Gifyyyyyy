package com.moodi.task.screen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moodi.task.databinding.ActivityHomeBinding
import com.moodi.task.screen.fragment.FragmentNavigator
import com.moodi.task.screen.fragment.SearchFragment
import com.moodi.task.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * This is the main activity of the app which is used to show the fragments in the container view
 * It uses the [FragmentNavigator] to navigate between the fragments.
 * It also uses the [SearchViewModel] to search the gif and show the results in the [SearchFragment]
 *
 */
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var fragmentNavigator: FragmentNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        fragmentNavigator = FragmentNavigator(supportFragmentManager)
//        fragmentNavigator.navigate(HomeFragmentItem)

    }

}