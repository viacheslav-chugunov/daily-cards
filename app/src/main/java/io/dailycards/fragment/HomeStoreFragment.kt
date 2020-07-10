package io.dailycards.fragment

import io.dailycards.R
import io.dailycards.fragment.abs.HomeFragment
import io.dailycards.tools.adapter.CardAdapter
import io.dailycards.tools.hasDailyStoreCards
import kotlinx.android.synthetic.main.no_daily_text.*

class HomeStoreFragment : HomeFragment() {

    override fun setupRecyclerView() {
        if (hasDailyStoreCards(context!!)) {
            val listener = this
            (childFragmentManager.findFragmentById(R.id.home_recycler_view) as RecyclerViewFragment)
                .setAdapter(CardAdapter(cursor, CardAdapter.Mode.STORE).apply { this.listener = listener })
        } else {
            setNoDailyCardsDescription()
        }
    }
}