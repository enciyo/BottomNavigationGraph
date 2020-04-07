package com.enciyo.bottomnavigationgprah

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter


class BottomNavigationGraphAdapter(private val navGraphs: IntArray,
                                   fragmentManager: FragmentManager,
                                   lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    private var mSparseArray = SparseArray<Fragment>()

    override fun getItemCount(): Int = navGraphs.size

    override fun getItemViewType(position: Int): Int = POSITION_NONE


    fun getNavController(position: Int) = mSparseArray[position].requireActivity()
        .findNavController(R.id.fragmentContainerNavNavigationArchBase)

    fun getFragment(position: Int): Fragment = mSparseArray[position]


    override fun createFragment(position: Int): Fragment {
        return BottomNavigationGraphHostFragment.newInstanceNavigationArchBaseFragment(graphId = navGraphs[position])
            .also { fragment ->
                mSparseArray.put(position, fragment)
            }
    }

}