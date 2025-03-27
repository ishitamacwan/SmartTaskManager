package com.example.taskmanagerassignment.ui.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.example.taskmanagerassignment.MyApp
import com.example.taskmanagerassignment.R
import com.example.taskmanagerassignment.ui.adapters.ViewPagerAdapter
import com.example.taskmanagerassignment.common.SortType
import com.example.taskmanagerassignment.databinding.ActivityMainBinding
import com.example.taskmanagerassignment.ui.fragments.SortBottomSheetFragment
import com.example.taskmanagerassignment.viewmodel.TaskViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : BaseActivity(), SortBottomSheetFragment.SortSelectionListener {
    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels()
    private var currentSortType = SortType.DUEDATE // Default sorting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateFab()
        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            val customView =
                LayoutInflater.from(this).inflate(R.layout.custom_tab, null) as TextView
            when (position) {
                0 -> customView.text = "All Tasks"
                1 -> customView.text = "Pending"
                2 -> customView.text = "Completed"
            }
            tab.customView = customView
        }.attach()

        val defaultTab = binding.tabLayout.getTabAt(0)
        defaultTab?.customView?.findViewById<TextView>(R.id.tabText)?.typeface =
            ResourcesCompat.getFont(this, R.font.poppins_semibold)

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tabText)?.typeface =
                    ResourcesCompat.getFont(
                        this@MainActivity,
                        R.font.poppins_semibold
                    )
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<TextView>(R.id.tabText)?.typeface =
                    ResourcesCompat.getFont(
                        this@MainActivity,
                        R.font.poppins_regular
                    )
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        binding.fabAddTask.setOnClickListener {
            var intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }
        binding.ivSettings.setOnClickListener {
            var intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
        binding.ivSort.setOnClickListener {
            val sortBottomSheet = SortBottomSheetFragment.newInstance(currentSortType)
            sortBottomSheet.setSortSelectionListener(this)
            sortBottomSheet.show(supportFragmentManager, "SortBottomSheet")
        }

    }

    // Sorting Selection Callback
    override fun onSortSelected(sortType: SortType) {
        currentSortType = sortType
        taskViewModel.updateSortType(sortType) // Notify Fragment via ViewModel
    }

    private fun animateFab() {
        val scaleUp = ObjectAnimator.ofPropertyValuesHolder(
            binding.fabAddTask,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1.15f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.15f)
        )

        val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
            binding.fabAddTask,
            PropertyValuesHolder.ofFloat(View.SCALE_X, 1f),
            PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f)
        )

        scaleUp.duration = 500
        scaleDown.duration = 500

        val animatorSet = AnimatorSet()
        animatorSet.play(scaleUp).before(scaleDown)
        animatorSet.interpolator = AccelerateDecelerateInterpolator()

        // Loop animation infinitely
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                animatorSet.start() // Restart animation on end
            }
        })

        animatorSet.start()
    }

    override fun onResume() {
        super.onResume()
        binding.toolbarMain.setBackgroundDrawable(ColorDrawable(MyApp.primaryColor))
        binding.clMain.backgroundTintList = ColorStateList.valueOf(MyApp.primaryColor)
        binding.fabAddTask.backgroundTintList = ColorStateList.valueOf(MyApp.primaryColor)

    }
}