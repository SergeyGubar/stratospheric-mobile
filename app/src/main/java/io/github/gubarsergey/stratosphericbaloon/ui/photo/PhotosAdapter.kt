package io.github.gubarsergey.stratosphericbaloon.ui.photo

import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.helper.GlideApp
import kotlinx.android.synthetic.main.item_photo.view.*
import org.jetbrains.anko.layoutInflater

class PhotosAdapter(val fragment: Fragment,
                    val urls: List<String>,
                    val ids: List<String>,
                    val deletePhotoCallback: (String) -> Unit): RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PhotoViewHolder {
        return PhotoViewHolder(parent.context.layoutInflater.inflate(R.layout.item_photo, parent, false))
            .apply { itemView.setOnLongClickListener { deletePhotoCallback(ids[adapterPosition]) ;false } }
    }

    override fun getItemCount(): Int = urls.size

    override fun onBindViewHolder(vh: PhotoViewHolder, position: Int) = vh.bind(urls[position])

    inner class PhotoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(url: String) {
            GlideApp.with(fragment)
                .load(url)
                .into(itemView.image)
        }
    }
}