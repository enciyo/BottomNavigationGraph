package com.enciyo.bottomnavigationgprah

import android.os.Bundle
import android.view.View
import androidx.annotation.NavigationRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment


class BottomNavigationGraphHostFragment : Fragment(R.layout.nav_navigation_arch_base) {


    companion object {
        fun newInstanceNavigationArchBaseFragment(@NavigationRes graphId: Int): BottomNavigationGraphHostFragment {
            return BottomNavigationGraphHostFragment().also { fragment ->
                Bundle().apply {
                    putInt("graphResourceID", graphId)
                }.also {
                    fragment.arguments = it
                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null){
            arguments?.getInt("graphResourceID", 0).also { graphResourceID ->
                if (graphResourceID!=null && graphResourceID!=0) {
                    NavHostFragment.create(graphResourceID).also {
                        childFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerNavNavigationArchBase, it)
                            .setPrimaryNavigationFragment(it)
                            .commit()
                    }
                }
            }
        }
    }


}