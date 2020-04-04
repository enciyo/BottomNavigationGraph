package com.enciyo.navigationarchlibrary.base

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference


class NavigationArcBaseBuilder(navigationArchBaseView: NavigationArchBaseView) {

    private var mLifecycle: Lifecycle? = null
    private var mNavGraphs: IntArray? = null
    private var mBottomNavigationView: WeakReference<BottomNavigationView>? = null
    private var mNavigationArchBaseView = WeakReference(navigationArchBaseView)


    fun setLifecycle(lifecycle: Lifecycle) = apply {
        this.mLifecycle = lifecycle
    }


    fun setNavGraphs(vararg navigationRes: Int) = apply {
        this.mNavGraphs = navigationRes
    }

    fun setBottomNavigationView(bottomNavigationView: BottomNavigationView) = apply {
        mBottomNavigationView = WeakReference(bottomNavigationView)
    }

    fun build(fragmentManager: FragmentManager): NavigationArchBaseManagementImp {
        val mNavigationArchBaseFragments = mutableListOf<NavigationArchBaseFragment>()
        (mNavGraphs ?: throw IllegalStateException("must be used setNavGraphs()")).forEach {
            mNavigationArchBaseFragments.add(NavigationArchBaseFragment.newInstanceNavigationArchBaseFragment(it))
        }

        return NavigationArchBaseManagementImp(
            WeakReference(NavigationArchBaseAdapter(
                mNavigationArchBaseFragments,
                fragmentManager,
                mLifecycle ?: throw IllegalStateException("must be used setLifecycle()")
            )),
            mNavigationArchBaseView,
            mLifecycle ?: throw IllegalStateException("must be used setLifecycle()"),
            mBottomNavigationView ?: throw IllegalStateException("must be used setBottomNavigationView()")
        )
    }


}

