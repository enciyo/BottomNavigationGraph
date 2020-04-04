package com.enciyo.navcomponentbottomviewlib

import androidx.navigation.NavController

interface NavigationArchBaseManagement {


    fun safeNavigateTryCatch(navController: (NavController) -> Unit)

    fun onBackPressed() : Boolean

    fun popToRoot()


}