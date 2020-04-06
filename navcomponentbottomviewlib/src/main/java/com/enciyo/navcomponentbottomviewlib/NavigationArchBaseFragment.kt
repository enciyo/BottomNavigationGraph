package com.enciyo.navcomponentbottomviewlib

import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment


class NavigationArchBaseFragment : Fragment(R.layout.nav_navigation_arch_base) {


    companion object {
        fun newInstanceNavigationArchBaseFragment(@NavigationRes graphId: Int): NavigationArchBaseFragment {
            return NavigationArchBaseFragment().also { fragment ->
                Bundle().apply {
                    putInt("layoutRes", graphId)
                }.also {
                    fragment.arguments = it
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("layoutRes", 0).also { layoutRes ->
            if (layoutRes!=null && layoutRes!=0) {
                NavHostFragment.create(layoutRes).also {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerNavNavigationArchBase, it)
                        .setPrimaryNavigationFragment(it)
                        .commit()
                }
            }
        }
    }


}