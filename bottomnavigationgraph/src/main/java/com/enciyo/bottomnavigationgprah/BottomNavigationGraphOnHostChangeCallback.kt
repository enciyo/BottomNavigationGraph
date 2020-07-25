package com.enciyo.bottomnavigationgprah


interface BottomNavigationGraphOnHostChangeCallback {

    fun onNavigationItemSelected(position:Int) : Boolean

    fun onNavigationItemReselected(position: Int) : Boolean

}


