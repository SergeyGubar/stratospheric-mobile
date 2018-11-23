package io.github.gubarsergey.stratosphericbaloon.ui.launches

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.db.launches.enitity.Launch
import kotlinx.android.synthetic.main.item_launch.view.*
import org.jetbrains.anko.layoutInflater

class LaunchesAdapter(val launches: List<Launch>) : RecyclerView.Adapter<LaunchesAdapter.LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): LaunchViewHolder =
        LaunchViewHolder(parent.context.layoutInflater.inflate(R.layout.item_launch, parent, false))

    override fun getItemCount(): Int = launches.size

    override fun onBindViewHolder(vh: LaunchViewHolder, position: Int) = vh.bind(launches[position])

    inner class LaunchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SetTextI18n")
        fun bind(launch: Launch) {
            // TODO :(
            // TODO: Add divider
            itemView.item_launch_time_text_view.text = launch.time.substring(0, 10)
            itemView.item_launch_name_text_view.text = launch.name
            itemView.item_launch_coords_text_view.text = "Longitude: ${launch.longitude}, Latitude: ${launch.latitude}"
        }
    }
}