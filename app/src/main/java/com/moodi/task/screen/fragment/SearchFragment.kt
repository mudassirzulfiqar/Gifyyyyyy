package com.moodi.task.screen.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.moodi.task.R
import com.moodi.task.adapter.SearchAdapter
import com.moodi.task.databinding.FragmentSearchLayoutBinding
import com.moodi.task.screen.activity.DetailActivity
import com.moodi.task.ui.sate.SearchState
import com.moodi.task.ui.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * A search fragment to show search results. It uses [SearchViewModel] to get the data. and show in the UI.
 * It is using layout binding to bind the views.
 * It uses [SearchAdapter] to show the search results in the recyclerview.
 *
 * @property viewModelFactory
 * @property binding
 * @property viewModel
 * @property adapter
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {


    private lateinit var binding: FragmentSearchLayoutBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapter: SearchAdapter

    /**
     * This method is used to show the loader based on the status.
     */
    private fun showLoader(status: Boolean) {
        binding.progressBar.visibility = if (status) View.VISIBLE else View.GONE
    }

    /**
     * This method is used to show the message in the toast.
     */
    private fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchLayoutBinding.inflate(inflater, container, false)

        adapter = SearchAdapter()
        adapter.addClickListener {
            /**
             * This method is used to navigate to the detail screen.
             */
            Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra(DetailActivity.TITLE, it.title)
                putExtra(DetailActivity.GIPHY_URL, it.animatedUrl)
                putExtra(DetailActivity.RATING, it.ageRate)
                putExtra(DetailActivity.IMAGE_SHORT_URL, it.displayLink)
            }.apply {
                startActivity(this)
            }
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.recyclerView.adapter = adapter

        lifecycleScope.launch {

            viewModel.dataState.collect {
                when (it) {
                    SearchState.Empty -> {
                        showLoader(false)
                        adapter.clearData()
                    }

                    SearchState.Loading -> {
                        showLoader(true)
                    }

                    is SearchState.NetworkError -> {
                        showLoader(false)
                        showMessage(getString(R.string.network_error))
                    }

                    SearchState.NoSearchResults -> {
                        binding.progressBar.visibility = View.GONE
                        showMessage(getString(R.string.no_results_found))
                    }

                    is SearchState.Success -> {
                        showLoader(false)
                        Timber.tag(TAG).d("search Results: ${it.data}")
                        adapter.populateData(it.data)
                    }
                }
            }
        }
        return binding.root
    }


    companion object {
        const val TAG = "SearchFragment"
    }
}