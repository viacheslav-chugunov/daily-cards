package io.dailycards.fragment

import io.dailycards.R
import io.dailycards.fragment.abs.HomeFragment
import io.dailycards.tools.adapter.CardAdapter
import kotlinx.android.synthetic.main.no_daily_text.*

class HomeAllFragment : HomeFragment() {

    override fun setupRecyclerView() {
        val listener = this
        (childFragmentManager.findFragmentById(R.id.home_recycler_view) as RecyclerViewFragment)
            .setAdapter(CardAdapter(cursor, CardAdapter.Mode.ALL).apply { this.listener = listener })
    }
}