package com.enciyo.bottomnavigationgprah

import android.view.MenuItem
import androidx.core.view.children
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.lang.ref.WeakReference
import java.util.*


internal class BottomNavigationGraphController(
    private val mFragmentManager: FragmentManager,
    private val mNavGraph: IntArray,
    private val mBottomNavigationGraphHostViewPager: WeakReference<BottomNavigationGraphHostViewPager>,
    lifecycle: Lifecycle,
    private val mBottomNavigationView: WeakReference<BottomNavigationView>
) : BottomNavigationGraph, LifecycleObserver, BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {

    constructor(builder: Builder) : this(
        mFragmentManager = builder.fragmentManager ?: throw IllegalStateException("must be used setFragmentManager() "),
        mNavGraph = builder.navGraphs ?: throw IllegalStateException("must be used setGraphs() "),
        mBottomNavigationGraphHostViewPager = builder.bottomNavigationGraphHostViewPager ?: throw IllegalStateException("must be used setBottomNavigationGraphHostViewPager("),
        lifecycle = builder.lifecycle ?: throw IllegalStateException("must be used setLifecycle()"),
        mBottomNavigationView = builder.bottomNavigationView ?: throw IllegalStateException("must be used setBottomNavigationView()")
    )


    private val mBackStack = Stack<Int>()



    private val mNavigationArchBaseAdapter by lazy {
        BottomNavigationGraphAdapter(
            mNavGraph,
            mFragmentManager,
            lifecycle
        )

    }


    init {
        if (mBackStack.empty()) mBackStack.push(0)
        lifecycle.addObserver(this)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreated() {
        mBottomNavigationGraphHostViewPager.get()?.let {
            it.adapterBottom = mNavigationArchBaseAdapter
        }
        mBottomNavigationView.get()?.let {
            it.setOnNavigationItemSelectedListener(this)
            it.setOnNavigationItemReselectedListener(this)
        }
    }

    private val mView
        get() = mBottomNavigationGraphHostViewPager.get()


    override fun safeNavigateTryCatch(navController: (NavController) -> Unit) {

    }

    override val isCanGoBack: Boolean
        get() {
            val hostedFragment = navController?.popBackStack()
            return if (hostedFragment==false) {
                if (mBackStack.size > 1) {
                    mBackStack.pop()
                    mView?.viewPager?.currentItem = mBackStack.peek()
                    return false
                } else true
            } else false
        }


    override val navController: NavController?
        get() = mView?.currentNavController


    override var currentGraphItem: Int
        get() = mView?.viewPager?.currentItem ?: throw java.lang.IllegalStateException("Not found currentItem. View not available")
        set(value) {
            setPageConfig(value)
            setBottomConfig(value)
        }

    override var onHostChangeCallback: BottomNavigationGraphOnHostChangeCallback? = null


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


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun removeViewReferences() {
        mBottomNavigationGraphHostViewPager.clear()
        mBottomNavigationView.clear()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        val position = mBottomNavigationView.get()?.menu?.children?.indexOf(p0)
        if (onHostChangeCallback!=null && !onHostChangeCallback!!.onNavigationItemSelected(position = position ?: 0)) return false
        setPageConfig(position ?: 0)
        return true
    }

    override fun onNavigationItemReselected(p0: MenuItem) {
        val position = mBottomNavigationView.get()?.menu?.children?.indexOf(p0)
        if (onHostChangeCallback!=null && !onHostChangeCallback!!.onNavigationItemReselected(position = position ?: 0)) return
        popToRoot()
    }


    override fun popToRoot() {
        navController?.let {
            it.popBackStack(it.graph.startDestination, false)
        }
    }

}