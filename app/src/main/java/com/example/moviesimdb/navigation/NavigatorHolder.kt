package com.example.moviesimdb.navigation

import androidx.fragment.app.Fragment

/**
 * Сущность для хранения ссылки на Navigator.
 */
interface NavigatorHolder {

    fun attachNavigator(navigator: Navigator)

    fun detachNavigator()

    fun openFragment(fragment: Fragment)

}