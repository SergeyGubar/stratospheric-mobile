package io.github.gubarsergey.stratosphericbaloon.helper

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class ItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if (position != parent.adapter!!.itemCount - 1) {
            outRect.bottom = space
        } else {
            outRect.setEmpty()
        }
    }
}