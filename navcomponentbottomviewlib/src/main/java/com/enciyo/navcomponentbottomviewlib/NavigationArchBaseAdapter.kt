package com.enciyo.navcomponentbottomviewlib

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter


class NavigationArchBaseAdapter(
    private val mutableList: MutableList<NavigationArchBaseFragment>,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return mutableList.size
    }

    fun getNavController(position: Int) = mutableList[position].requireActivity().findNavController(R.id.fragmentContainerNavNavigationArchBase)

    fun getFragment(position: Int) = mutableList[position]

    override fun createFragment(position: Int): Fragment {
        return mutableList[position]
    }

}