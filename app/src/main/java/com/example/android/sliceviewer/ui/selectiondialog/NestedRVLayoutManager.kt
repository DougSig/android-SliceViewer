package com.example.android.sliceviewer.ui.selectiondialog

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class NestedRVLayoutManager : LinearLayoutManager {

    private val measuredDimension = IntArray(2)

    constructor(context: Context) : super(context)

    constructor(context: Context, orientation: Int, reverseLayout: Boolean)
            : super(context, orientation, reverseLayout)

    override fun onMeasure(
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State,
        widthSpec: Int,
        heightSpec: Int
    ) {

        val widthMode = View.MeasureSpec.getMode(widthSpec)
        val heightMode = View.MeasureSpec.getMode(heightSpec)
        val widthSize = View.MeasureSpec.getSize(widthSpec)
        val heightSize = View.MeasureSpec.getSize(heightSpec)

        var width = 0
        var height = 0
        for (i in 0 until itemCount) {
            measureScrapChild(
                recycler, i, View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(i, View.MeasureSpec.UNSPECIFIED),
                measuredDimension
            )


            if (orientation == LinearLayoutManager.HORIZONTAL) {
                width += measuredDimension[0]
                if (i == 0) {
                    height = measuredDimension[1]
                }
            } else {
                height += measuredDimension[1]
                if (i == 0) {
                    width = measuredDimension[0]
                }
            }
        }
        if (widthMode == View.MeasureSpec.EXACTLY) {
            width = widthSize
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            height = heightSize
        }

        setMeasuredDimension(width, height)
    }

    private fun measureScrapChild(
        recycler: RecyclerView.Recycler, position: Int, widthSpec: Int,
        heightSpec: Int, measuredDimension: IntArray
    ) {
        try {
            val view = recycler.getViewForPosition(0) //fix IndexOutOfBoundsException

            val p = view.layoutParams as RecyclerView.LayoutParams

            val childWidthSpec = ViewGroup.getChildMeasureSpec(
                widthSpec,
                paddingLeft + paddingRight, p.width
            )

            val childHeightSpec = ViewGroup.getChildMeasureSpec(
                heightSpec, paddingTop + paddingBottom, p.height
            )

            view.measure(childWidthSpec, childHeightSpec)
            measuredDimension[0] = view.measuredWidth + p.leftMargin + p.rightMargin
            measuredDimension[1] = view.measuredHeight + p.bottomMargin + p.topMargin
            recycler.recycleView(view)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}