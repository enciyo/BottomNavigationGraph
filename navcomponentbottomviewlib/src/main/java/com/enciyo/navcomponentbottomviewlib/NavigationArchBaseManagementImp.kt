package com.enciyo.navcomponentbottomviewlib

import android.view.MenuItem
import androidx.core.view.children
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.IllegalStateException
import java.lang.ref.WeakReference
import java.util.*


class NavigationArchBaseManagementImp(
    private val mFragmentManager: FragmentManager,
    private val mNavGraph: IntArray,
    private val mNavigationArchBaseView: WeakReference<NavigationArchBaseView>,
    lifecycle: Lifecycle,
    private val mBottomNavigationView: WeakReference<BottomNavigationView>
) :
    NavigationArchBaseManagement, LifecycleObserver, BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {


    private val mBackStack = Stack<Int>()

    private val mNavigationArchBaseAdapter by lazy {
        NavigationArchBaseAdapter(
            mNavGraph,
            mFragmentManager,
            lifecycle
        )
    }

    private val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            setBottomConfig(position)
        }
    }


    init {
        if (mBackStack.empty()) mBackStack.push(0)
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        mNavigationArchBaseView.get()?.let {
            it.adapter = mNavigationArchBaseAdapter
            it.viewPager.registerOnPageChangeCallback(onPageChangeListener)
        }
        mBottomNavigationView.get()?.let {
            it.setOnNavigationItemSelectedListener(this)
            it.setOnNavigationItemReselectedListener(this)
        }
    }

    private val mView
        get() = mNavigationArchBaseView.get()


    override fun safeNavigateTryCatch(navController: (NavController) -> Unit) {

    }

    private fun setPageConfig(position: Int) {
        if (mView?.viewPager?.currentItem!=position) {
            mView?.viewPager?.setCurrentItem(position, false)
            mBackStack.push(position)
        }


    }

    private fun setBottomConfig(position: Int) {
        mBottomNavigationView.get()?.selectedItemId = mBottomNavigationView.get()?.menu?.children?.toMutableList()?.get(position)?.itemId ?: throw
        IllegalStateException("")

    }


    override fun onBackPressed(): Boolean {
        val hostedFragment = mView?.currentNavController?.popBackStack()
        return if (hostedFragment==false) {
            if (mBackStack.size > 1) {
                mBackStack.pop()
                mView?.viewPager?.currentItem = mBackStack.peek()
                return false
            } else true
        } else false
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeViewReferences() {
        mNavigationArchBaseView.get()?.viewPager?.unregisterOnPageChangeCallback(onPageChangeListener)
        mNavigationArchBaseView.clear()
        mBottomNavigationView.clear()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val position = mBottomNavigationView.get()?.menu?.children?.indexOf(p0)
        setPageConfig(position ?: 0)
        return true
    }

    override fun onNavigationItemReselected(p0: MenuItem) {
        popToRoot()
    }


    override fun popToRoot() {
        val navController = mView?.currentNavController
        navController?.popBackStack(navController.graph.startDestination, false)
    }

}