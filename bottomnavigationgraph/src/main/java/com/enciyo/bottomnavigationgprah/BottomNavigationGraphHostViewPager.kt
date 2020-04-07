package com.enciyo.bottomnavigationgprah

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.navigation_arch_base_view.view.*


class BottomNavigationGraphHostViewPager(context: Context, attributeSet: AttributeSet?) : RelativeLayout(context, attributeSet) {



    private val mView = View.inflate(context, R.layout.navigation_arch_base_view, this)

    val viewPager
        get() = mView.findViewById<ViewPager2>(R.id.viewPager2Fragments)


    var adapterBottom: BottomNavigationGraphAdapter? = null
        set(value) {
            field = value
            setupViewPager2Fragments(value)
        }

    val currentNavController: NavController?
        get() = try {
            adapterBottom?.getNavController(viewPager?.currentItem ?: throw Throwable("Not find fragment "))
        } catch (e: Exception) {
            null
        }


    init {
        with(mView.viewPager2Fragments) {
            isUserInputEnabled = false
            setPageTransformer(BottomNavigationGraphPageAdapterTransformer())
        }
    }


    fun navigate(@IdRes idRes: Int) {
        try {
            currentNavController?.navigate(idRes)
        } catch (e: Exception) {
            Log.i("MyLogger", "Didn't navigate ${viewPager?.currentItem})")
        }
    }


    private fun setupViewPager2Fragments(value: BottomNavigationGraphAdapter?) {
        viewPager.adapter = value
    }

    override fun getRootView(): View {
        return mView
    }

}