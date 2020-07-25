package com.enciyo.bottomnavigationgprah

import androidx.navigation.NavController

interface BottomNavigationGraph {

    companion object{
        fun newBuilder() = Builder()
    }


    fun safeNavigateTryCatch(navController: (NavController) -> Unit)


    /**
     * Tab içerisinde geri gidilebilir mi?
     */
    val isCanGoBack: Boolean

    /**
     * Android Framework'une ait bir sınıf
     * Fragmentlar arası geçişlerde kullanılır
     */
    val navController:NavController?


    /**
     * O anki tab'ın  0..n arası int karşılığı
     */
    var currentGraphItem:Int

    /**
     * Tab'lardan herhangi birine tıklandığında çağrılığan interface sınıfı
     */
    var onHostChangeCallback : BottomNavigationGraphOnHostChangeCallback?

    /**
     * Seçili tabın ilk sayfasını döner.
     */
    fun popToRoot()

}