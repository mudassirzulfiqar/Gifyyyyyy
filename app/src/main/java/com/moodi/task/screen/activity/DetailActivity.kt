package com.moodi.task.screen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moodi.task.databinding.ActivityDetailBinding
import com.moodi.task.databinding.GiphyDetailLayoutBinding

/**
 * This Activity is used to show the details of the gif
 * to simplify I choose not to use ViewModel here.
 *
 * Activity binding is used here to bind the views
 * It fetches intent from previous activity and populate the data in the views
 */
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent?.getStringExtra(TITLE)
        val giphyUrl = intent?.getStringExtra(GIPHY_URL)
        val ageRate = intent?.getStringExtra(RATING)
        val imageShortUrl = intent?.getStringExtra(IMAGE_SHORT_URL)

        binding = ActivityDetailBinding.inflate(layoutInflater).apply {
            setContentView(root)

            titleTextview.text = title

            backImageButton.setOnClickListener {
                onBackPressed()
            }

            GiphyDetailLayoutBinding.bind(root).apply {
                giphyLinkTextview.text = imageShortUrl
                giphySubTitleTextview.text = title
                giphyRatingTextview.text = ageRate
                giphyImageView.loadImage(giphyUrl!!)
            }

        }

    }

    companion object {
        const val TITLE = "title"
        const val GIPHY_URL = "image_url"
        const val RATING = "rating"
        const val IMAGE_SHORT_URL = "image_short_url"
    }
}