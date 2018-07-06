package com.example.android.sliceviewer.ui.selectiondialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sliceviewer.R

class SliceSelectionDialog(val systemSlices: List<Any>) : DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sliceselection_dialog, container)
        recyclerView = view.findViewById(R.id.slice_list)
        val slices = arguments[]
        recyclerView.adapter = SystemSlicesAdapter(systemSlices)

        return view
    }
}