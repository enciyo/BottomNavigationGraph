package com.enciyo.navcomponentbottomviewlib

import android.view.MenuItem
import androidx.core.view.children
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.IllegalStateException
import java.lang.ref.WeakReference
import java.util.*


class NavigationArchBaseManagementImp(
    private val mNavigationArchBaseAdapter: WeakReference<NavigationArchBaseAdapter>,
    private val mNavigationArchBaseView: WeakReference<NavigationArchBaseView>,
    lifecycle: Lifecycle,
    private val mBottomNavigationView: WeakReference<BottomNavigationView>
) :
    NavigationArchBaseManagement, LifecycleObserver, BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {


    private val mBackStack = Stack<Int>()

    private val onPageChangeListener = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            setBottomConfig(position)
        }
    }


    init {

        if (mBackStack.empty()) mBackStack.push(0)
        lifecycle.addObserver(this)
        mNavigationArchBaseView.get()?.let {
            it.adapter = mNavigationArchBaseAdapter.get()
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
            mView?.viewPager?.currentItem = position
            mBackStack.push(position)
        }


    }

    private fun setBottomConfig(position: Int) {
        mBottomNavigationView.get()?.selectedItemId = mBottomNavigationView.get()?.menu?.children?.toMutableList()?.get(position)?.itemId ?: throw
        IllegalStateException("")

    }


    override fun onBackPressed(): Boolean {
        val position = mView?.viewPager?.currentItem
        val hostedFragment = mNavigationArchBaseAdapter.get()?.getNavController(position ?: throw
        IllegalStateException("onBackPressed() -> not find current item $position"))?.popBackStack()
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
        mNavigationArchBaseAdapter.clear()
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
        val position = mView?.viewPager?.currentItem
        val navController = mNavigationArchBaseAdapter.get()?.getNavController(position ?: throw
        IllegalStateException("onBackPressed() -> not find current item $position"))
        navController?.popBackStack(navController.graph.startDestination, false)
    }

}