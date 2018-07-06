package com.example.android.sliceviewer.ui.selectiondialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sliceviewer.R
import com.example.android.sliceviewer.ui.model.PackageSlice
import com.example.android.sliceviewer.ui.model.SystemPackage
import com.example.android.sliceviewer.ui.selectiondialog.SystemSlicesAdapter.PackageSliceViewHolder
import com.example.android.sliceviewer.ui.selectiondialog.SystemSlicesAdapter.PackageSliceViewHolder.Companion.VIEW_TYPE_INVALID
import com.example.android.sliceviewer.ui.selectiondialog.SystemSlicesAdapter.PackageSliceViewHolder.Companion.VIEW_TYPE_PACKAGE
import com.example.android.sliceviewer.ui.selectiondialog.SystemSlicesAdapter.PackageSliceViewHolder.Companion.VIEW_TYPE_SLICE
import com.example.android.sliceviewer.ui.selectiondialog.SystemSlicesAdapter.PackageSliceViewHolder.PackageViewHolder
import com.example.android.sliceviewer.ui.selectiondialog.SystemSlicesAdapter.PackageSliceViewHolder.SliceViewHolder

class SystemSlicesAdapter
    : ListAdapter<Any, PackageSliceViewHolder>(PackageDiff) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PackageSlice -> VIEW_TYPE_SLICE
            is SystemPackage -> VIEW_TYPE_PACKAGE
            else -> VIEW_TYPE_INVALID
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageSliceViewHolder {
        return when (viewType) {
            VIEW_TYPE_SLICE -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_slice, parent, false)
                SliceViewHolder(itemView)
            }
            VIEW_TYPE_PACKAGE -> {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_package, parent, false)
                PackageViewHolder(itemView)
            }
            else -> { throw IllegalArgumentException("View Type unknown: $viewType") }
        }
    }

    override fun onBindViewHolder(holder: PackageSliceViewHolder, position: Int) {
        when (holder) {
            is SliceViewHolder -> {
                holder.bind(getItem(position) as PackageSlice)
            }
            is PackageViewHolder -> {
                holder.bind(getItem(position) as SystemPackage)
            }
        }
    }

    object PackageDiff : DiffUtil.ItemCallback<Any>() {
        override fun areItemsTheSame(oldItem: Any, newItem: Any) = oldItem === newItem
        override fun areContentsTheSame(oldItem: Any, newItem: Any) = oldItem == newItem
    }

    sealed class PackageSliceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        class SliceViewHolder(val view: View) : PackageSliceViewHolder(view) {
            private val sliceUri = view.findViewById<TextView>(R.id.slice_uri)
            fun bind(packageSlice: PackageSlice) {
                sliceUri.text = view.context.getString(R.string.slice_uri, packageSlice.uri.toString())
            }
        }

        class PackageViewHolder(val view: View) : PackageSliceViewHolder(view) {
            private val packageName = view.findViewById<TextView>(R.id.package_name)
            fun bind(systemPackage: SystemPackage) {
                packageName.text = systemPackage.packageName
            }
        }

        companion object {
            const val VIEW_TYPE_SLICE = 0
            const val VIEW_TYPE_PACKAGE = 1
            const val VIEW_TYPE_INVALID = -1
        }
    }
}