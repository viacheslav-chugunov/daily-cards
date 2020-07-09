package io.dailycards.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.dailycards.R
import kotlinx.android.synthetic.main.fragment_recycler_view.*

class RecyclerViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    override fun onStart() {
        super.onStart()
        recycler_view.layoutManager = LinearLayoutManager(context)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recycler_view.adapter = adapter
    }

    fun setLayoutManager(layoutManager: RecyclerView.LayoutManager) {
        recycler_view.layoutManager = layoutManager
    }
}