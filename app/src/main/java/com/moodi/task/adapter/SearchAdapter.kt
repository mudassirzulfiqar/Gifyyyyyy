package com.moodi.task.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moodi.task.R
import com.moodi.task.data.local.GiphyAppModel
import com.moodi.task.widget.GiphyImageView

/**
 * This Adapter is being used to show the search result in the recycler view
 * it exposes following methods populateData, clearData, addClickListener
 * @see populateData
 * @see clearData
 * @see addClickListener
 *
 * @property itemList List<GiphyAppModel>
 * This is the list of [GiphyAppModel] to be populated in the recycler view
 */
class SearchAdapter :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var itemList: List<GiphyAppModel> = emptyList()
    private var clickListener: ((GiphyAppModel) -> Unit)? = null

    /**
     * This method is used to populate the data in the recycler view
     * @param data List<GiphyAppModel>
     *
     * Just for simplicity, I am not using DiffUtil here and calling notifyDataSetChanged() as Im clearing the data before populating.
     */
    fun populateData(data: List<GiphyAppModel>) {
        itemList = data
        notifyDataSetChanged()
    }

    fun clearData() {
        itemList = emptyList()
        notifyDataSetChanged()
    }

    /**
     * This method is used to add click listener to the recycler view from [SearchFragment]
     */
    fun addClickListener(clickListener: (GiphyAppModel) -> Unit) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.itemGiphyView.loadImage(item.stillUrl)
        holder.itemGiphyView.setOnClickListener {
            clickListener?.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemGiphyView: GiphyImageView = itemView.findViewById(R.id.gif_image_layout)
    }
}