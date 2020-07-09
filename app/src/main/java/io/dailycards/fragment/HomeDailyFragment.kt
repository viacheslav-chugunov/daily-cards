package io.dailycards.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.dailycards.R
import io.dailycards.fragment.abs.HomeFragment
import io.dailycards.tools.adapter.CardAdapter
import kotlinx.android.synthetic.main.fragment_home_daily.*

class HomeDailyFragment : HomeFragment() {

    interface Listener {
        fun onClickCreationFAB()
    }
    private var listener: Listener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as Listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_home_daily, container, false)

    override fun onStart() {
        super.onStart()
        creation_fab.setOnClickListener { listener?.onClickCreationFAB() }
    }

    override fun setupRecyclerView() {
        val adapter = CardAdapter(cursor, CardAdapter.Mode.DAILY)
        adapter.listener = this
        (childFragmentManager.findFragmentById(R.id.home_recycler_view) as RecyclerViewFragment)
            .setAdapter(adapter)
    }
}