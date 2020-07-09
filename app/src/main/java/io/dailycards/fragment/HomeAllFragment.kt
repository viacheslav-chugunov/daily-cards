package io.dailycards.fragment

import io.dailycards.R
import io.dailycards.fragment.abs.HomeFragment
import io.dailycards.tools.adapter.CardAdapter

class HomeAllFragment : HomeFragment() {

    override fun setupRecyclerView() {
        val adapter = CardAdapter(cursor, CardAdapter.Mode.ALL)
        adapter.listener = this
        (childFragmentManager.findFragmentById(R.id.home_recycler_view) as RecyclerViewFragment)
            .setAdapter(adapter)
    }
}