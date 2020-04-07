package com.enciyo.bottomnavigationgprah

import androidx.navigation.NavController

interface BottomNavigationGraph {

    companion object{
        fun newBuilder() = Builder()
    }


    fun safeNavigateTryCatch(navController: (NavController) -> Unit)


    val isCanGoBack: Boolean
    val navController:NavController?
    var currentGraphItem:Int

    fun popToRoot()

    var onHostChangeCallback : BottomNavigationGraphOnHostChangeCallback?


}