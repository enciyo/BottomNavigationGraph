package com.enciyo.bottomnavigationgprah

import android.util.SparseArray
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder


class BottomNavigationGraphAdapter(private val navGraphs: IntArray,
                                   fragmentManager: FragmentManager,
                                   lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    private var mSparseArray = SparseArray<Fragment>()

    override fun getItemCount(): Int = navGraphs.size

    override fun getItemViewType(position: Int): Int = POSITION_NONE


    fun getNavController(position: Int) = mSparseArray[position].requireActivity()
        .findNavController(R.id.fragmentContainerNavNavigationArchBase)

    fun getFragment(position: Int): Fragment = mSparseArray[position]



    override fun onBindViewHolder(holder: FragmentViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
    }



    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }



    override fun createFragment(position: Int): Fragment {
        return BottomNavigationGraphHostFragment.newInstanceNavigationArchBaseFragment(graphId = navGraphs[position])
            .also { fragment ->
                mSparseArray.put(position, fragment)
            }
    }

}