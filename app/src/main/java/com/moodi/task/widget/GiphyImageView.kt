package com.moodi.task.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.moodi.task.R

/**
 * This class is and extended class from ImageView to load gif images.
 * @param context
 * @param attrs
 * @param defStyle
 *
 * loadImage() method is used to load gif image from url
 */
class GiphyImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    /**
     * This method is used to load gif image from url
     *
     * @param url is the url of gif image
     */
    fun loadImage(url: String) {
        Glide.with(context)
            .asGif()
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(this)
    }
}