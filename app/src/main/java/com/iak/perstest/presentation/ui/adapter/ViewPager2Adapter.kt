package com.iak.perstest.presentation.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPager2Adapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragmentsList: MutableList<Fragment>
    private val titles: MutableList<String>

    fun addFragment(fragment: Fragment, title: String) {
        fragmentsList.add(fragment)
        titles.add(title)
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    fun clearFragments() {
        fragmentsList.clear()
        titles.clear()
    }

    private fun getTitles(): List<String> {
        return titles
    }

    fun attachTabs(viewPager2: ViewPager2, tabLayout: TabLayout) {
        val strategy =
            TabLayoutMediator.TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = getTitles()[position]
            }
        TabLayoutMediator(tabLayout, viewPager2, strategy).attach()
    }

    init {
        fragmentsList = ArrayList()
        titles = ArrayList()
    }
}