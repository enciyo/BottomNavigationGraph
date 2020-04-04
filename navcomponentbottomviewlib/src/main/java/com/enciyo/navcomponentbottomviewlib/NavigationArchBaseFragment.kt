package com.enciyo.navigationarchlibrary.base

import android.os.Bundle
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.enciyo.navigationarch.R
import kotlinx.android.synthetic.main.nav_navigation_arch_base.*


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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