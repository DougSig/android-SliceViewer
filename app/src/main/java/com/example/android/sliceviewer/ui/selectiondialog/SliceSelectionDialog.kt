package com.example.android.sliceviewer.ui.selectiondialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.sliceviewer.R
import com.example.android.sliceviewer.ui.ViewModelFactory
import com.example.android.sliceviewer.ui.list.SliceViewModel

class SliceSelectionDialog() : DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: SliceViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sliceselection_dialog, container)
        val act = activity ?: return view
        val viewModelFactory = ViewModelFactory.getInstance(act.application)
        viewModel = ViewModelProviders.of(act, viewModelFactory)
            .get(SliceViewModel::class.java)
        recyclerView = view.findViewById(R.id.slice_list)
        recyclerView.adapter = SystemPackagesAdapter().also { adapter ->
            viewModel.systemSlices.observe(
                act,
                Observer {
                    adapter.submitList(it)
                }
            )
        }
        recyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = LinearLayoutManager.VERTICAL
        }

        return view
    }
}