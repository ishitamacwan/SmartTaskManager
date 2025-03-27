package com.example.taskmanagerassignment.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.taskmanagerassignment.ui.fragments.CompletedTasksFragment
import com.example.taskmanagerassignment.ui.fragments.MyTasksFragment
import com.example.taskmanagerassignment.ui.fragments.PendingTasksFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragments: List<Fragment> = listOf(
        MyTasksFragment(),
        PendingTasksFragment(),
        CompletedTasksFragment()
    )

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}
