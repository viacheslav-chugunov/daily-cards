package io.dailycards.tools.adapter

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.dailycards.R
import io.dailycards.fragment.HomeAllFragment
import io.dailycards.fragment.HomeDailyFragment
import io.dailycards.fragment.HomeStoreFragment

class HomeSectionsAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val SECTIONS_COUNT = 3
    }

    override fun getItem(pos: Int) = when (pos) {
        0 -> HomeDailyFragment()
        1 -> HomeStoreFragment()
        else -> HomeAllFragment()
    }

    override fun getCount(): Int {
        return SECTIONS_COUNT
    }

    override fun getPageTitle(pos: Int) = when(pos) {
        0 ->  context.getString(R.string.my_cards_tab)
        1 -> context.getString(R.string.store_tab)
        2 -> context.getString(R.string.all_tab)
        else -> null
    }

}