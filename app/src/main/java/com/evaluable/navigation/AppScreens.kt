package com.evaluable.navigation

sealed class AppScreens(val route: String) {
    object MainMenu: AppScreens("main_menu")
    object AddWaifu: AppScreens("add_waifu")
    object DeleteWaifu: AppScreens("delete_waifu")
    object ModifyWaifu: AppScreens("modify_waifu")
    object ListWaifus: AppScreens("list_waifus")
    object SearchWaifu: AppScreens("search_waifu")
}