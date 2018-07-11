package com.example.android.sliceviewer.ui.selectiondialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android.sliceviewer.R
import com.example.android.sliceviewer.ui.model.PackageSlice
import com.example.android.sliceviewer.ui.model.SystemPackage

class SystemPackagesAdapter
    : ListAdapter<SystemPackage, SystemPackagesAdapter.SystemPackageViewHolder>(SystemPackageDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemPackageViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_package, parent, false)
        return SystemPackageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SystemPackageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SystemPackageViewHolder(val view: View) : ViewHolder(view) {
        val packageName: TextView = view.findViewById(R.id.package_name)
        val packageSliceList: RecyclerView = view.findViewById(R.id.package_slices)
        fun bind(pkg: SystemPackage) {
            packageName.text = pkg.packageName
            packageSliceList.layoutManager = NestedRVLayoutManager(view.context)
            packageSliceList.adapter = PackageSlicesAdapter().apply { submitList(pkg.slices) }
        }
    }

    object SystemPackageDiff : DiffUtil.ItemCallback<SystemPackage>() {
        override fun areItemsTheSame(oldItem: SystemPackage, newItem: SystemPackage) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: SystemPackage, newItem: SystemPackage) =
            oldItem == newItem
    }

    class PackageSlicesAdapter
        : ListAdapter<PackageSlice, PackageSlicesAdapter.PackageSliceViewHolder>(PackageSliceDiff) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageSliceViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_slice, parent, false)
            return PackageSliceViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: PackageSliceViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        class PackageSliceViewHolder(val view: View) : ViewHolder(view) {
            private val uriLabel: TextView = view.findViewById(R.id.slice_uri)

            fun bind(packageSlice: PackageSlice) {
                uriLabel.text = packageSlice.uri.toString()
            }
        }

        object PackageSliceDiff : DiffUtil.ItemCallback<PackageSlice>() {
            override fun areItemsTheSame(oldItem: PackageSlice, newItem: PackageSlice): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: PackageSlice, newItem: PackageSlice): Boolean {
                return oldItem == newItem
            }
        }
    }
}