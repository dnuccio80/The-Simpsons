package com.example.thesimpsons.ui.core.extensions

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

fun NavBackStack<NavKey>.navigateTo(screen: NavKey) {
    if(screen != last()) {
        add(screen)
    }
}

fun NavBackStack<NavKey>.back() {
    if (isEmpty()) return
    removeLastOrNull()
}

fun NavBackStack<NavKey>.backTo(targetScreen: NavKey) {
    if (isEmpty()) return
    if (targetScreen !in this) navigateTo(targetScreen)
    while (isNotEmpty() && last() != targetScreen) {
        removeLastOrNull()
    }
}

fun NavBackStack<NavKey>.clearAndNavigateTo(targetScreen: NavKey) {
    clear()
    navigateTo(targetScreen)
}
