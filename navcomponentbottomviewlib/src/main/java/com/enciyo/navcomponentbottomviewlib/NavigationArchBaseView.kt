package com.enciyo.navigationarchlibrary.base

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.viewpager2.widget.ViewPager2
import com.enciyo.navigationarch.R
import kotlinx.android.synthetic.main.navigation_arch_base_view.view.*


class NavigationArchBaseView(context: Context, attributeSet: AttributeSet?) : RelativeLayout(context, attributeSet) {


    private val mView = View.inflate(context, R.layout.navigation_arch_base_view, this)

    val viewPager
        get() = mView.findViewById<ViewPager2>(R.id.viewPager2Fragments)


    var adapter: NavigationArchBaseAdapter? = null
        set(value) {
            field = value
            setupViewPager2Fragments(value)
        }

    val currentNavController: NavController?
        get() = adapter?.getNavController(viewPager?.currentItem ?: throw Throwable("Not find fragment "))


    init {
        with(mView.viewPager2Fragments){
            isUserInputEnabled = false
            setPageTransformer(FadeInOutPageTransformer())
        }
    }


    fun navigate(@IdRes idRes: Int) {
        try {
            currentNavController?.navigate(idRes)
        } catch (e: Exception) {
            Log.i("MyLogger", "Didn't navigate ${viewPager?.currentItem})")
        }
    }


    private fun setupViewPager2Fragments(value: NavigationArchBaseAdapter?) {
        viewPager.adapter = value
    }

    override fun getRootView(): View {
        return mView
    }

}