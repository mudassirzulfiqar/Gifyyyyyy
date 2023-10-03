package com.moodi.task.screen.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.moodi.task.R
import com.moodi.task.databinding.FragmentRandomBinding
import com.moodi.task.databinding.GiphyDetailLayoutBinding
import com.moodi.task.ui.sate.RandomState
import com.moodi.task.ui.viewmodel.RandomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * A random fragment to show random gif.
 * This fragment is responsible for showing random gif. It uses [RandomViewModel] to get the data. and show in the UI.
 * It is using layout binding to bind the views.
 *
 * @property viewModelFactory
 * @property binding
 * @property giphyDetailLayoutBinding
 * @property viewModel
 *
 * it shows the loader when the data is loading. and after the success shows the data in the UI.
 * and if there is any error, it shows the error message in the toast.
 *
 * As Per requirement it is changing the random gif periodically.
 */
@AndroidEntryPoint
class RandomFragment : Fragment() {


    private lateinit var binding: FragmentRandomBinding
    private lateinit var giphyDetailLayoutBinding: GiphyDetailLayoutBinding
    private val viewModel: RandomViewModel by viewModels()

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
        binding = FragmentRandomBinding.inflate(inflater, container, false)
        giphyDetailLayoutBinding = GiphyDetailLayoutBinding.bind(binding.root)


        lifecycleScope.launch {
            viewModel.dataState.collect {
                when (it) {
                    is RandomState.Empty -> {
                        showLoader(false)
                    }

                    is RandomState.Loading -> showLoader(true)

                    is RandomState.NetworkError -> {
                        showLoader(false)
                        showMessage(getString(R.string.network_error))
                    }

                    is RandomState.Success -> {
                        showLoader(false)
                        giphyDetailLayoutBinding.apply {
                            it.data.apply {
                                giphySubTitleTextview.text = title
                                giphyRatingTextview.text = ageRate
                                giphyImageView.loadImage(animatedUrl)
                                giphyLinkTextview.text = (displayLink)
                            }
                        }
                    }
                }
            }
        }

        viewModel.generateRandomGif()

        return binding.root
    }


    companion object {
        const val TAG = "RandomFragment"
    }
}