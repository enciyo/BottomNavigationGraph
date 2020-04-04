package com.enciyo.navigationarchlibrary.base

import androidx.navigation.NavController

interface NavigationArchBaseManagement {


    fun safeNavigateTryCatch(navController: (NavController) -> Unit)

    fun onBackPressed() : Boolean

    fun popToRoot()


}