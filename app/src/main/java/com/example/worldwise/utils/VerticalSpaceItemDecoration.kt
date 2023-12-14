package com.example.worldwise.utils

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int, val bottomBackground: Drawable, val topBackground: Drawable) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = verticalSpaceHeight
        if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount?.minus(1)) {
            view.background = bottomBackground
        } else if (parent.getChildAdapterPosition(view) == 0) {
            view.background = topBackground
        }
    }
}