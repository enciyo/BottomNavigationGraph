package com.enciyo.navcomponentbottomviewlib

import android.util.SparseArray
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter.POSITION_NONE
import androidx.viewpager2.adapter.FragmentStateAdapter


class NavigationArchBaseAdapter(
    private val intArray: IntArray,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    private var mSparseArray = SparseArray<Fragment>()


    override fun getItemCount(): Int {
        return intArray.size
    }

    override fun getItemViewType(position: Int): Int {
        return POSITION_NONE
    }


    fun getNavController(position: Int) = mSparseArray[position].requireActivity().findNavController(R.id.fragmentContainerNavNavigationArchBase)

    fun getFragment(position: Int) = mSparseArray[position]





    override fun createFragment(position: Int): Fragment {
        val fragment = NavigationArchBaseFragment.newInstanceNavigationArchBaseFragment(intArray[position])
        mSparseArray.put(fragment.hashCode(), fragment)
        return fragment
    }

}