package com.enciyo.bottomnavigationgprah

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference


class Builder {

    var lifecycle: Lifecycle? = null
        private set

    var navGraphs: IntArray? = null
        private set

    var bottomNavigationView: WeakReference<BottomNavigationView>? = null
        private set

    var fragmentManager: FragmentManager? = null
        private set

    var bottomNavigationGraphHostViewPager: WeakReference<BottomNavigationGraphHostViewPager>? = null
        private set


    fun setBottomNavigationGraphHostViewPager(bottomNavigationGraphHostViewPager: BottomNavigationGraphHostViewPager) = apply { this.bottomNavigationGraphHostViewPager = WeakReference(bottomNavigationGraphHostViewPager) }

    fun setLifecycle(lifecycle: Lifecycle) = apply { this.lifecycle = lifecycle }

    fun setGraphs(vararg navigationRes: Int) = apply { this.navGraphs = navigationRes }

    fun setBottomNavigationView(bottomNavigationView: BottomNavigationView) = apply { this.bottomNavigationView = WeakReference(bottomNavigationView) }

    fun setFragmentManager(fragmentManager: FragmentManager) = apply { this.fragmentManager = fragmentManager }

    fun build(): BottomNavigationGraph = BottomNavigationGraphController(this)


}
