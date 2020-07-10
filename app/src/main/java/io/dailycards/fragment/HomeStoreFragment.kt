package io.dailycards.fragment

import io.dailycards.R
import io.dailycards.fragment.abs.HomeFragment
import io.dailycards.tools.adapter.CardAdapter

class HomeStoreFragment : HomeFragment() {

    override fun setupRecyclerView() {
        val listener = this
        (childFragmentManager.findFragmentById(R.id.home_recycler_view) as RecyclerViewFragment)
            .setAdapter(CardAdapter(cursor, CardAdapter.Mode.STORE).apply { this.listener = listener })
    }
}