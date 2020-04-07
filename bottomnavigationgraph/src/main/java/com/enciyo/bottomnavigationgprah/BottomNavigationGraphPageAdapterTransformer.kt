package com.enciyo.bottomnavigationgprah

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class BottomNavigationGraphPageAdapterTransformer : ViewPager2.PageTransformer{
    override fun transformPage(page: View, position: Float) {
        with(page){
            translationX = width * -position
            alpha = if (position <= -1.0F || position >= 1.0F) {
                0.0F
            } else if (position==0.0F) {
                1.0F
            } else {
                1.0F - abs(position)
            }
        }
    }
}