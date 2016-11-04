package viper.view

import viper.routing.Screen

/**
 * A view which represents an activity.
 * Created by Nick Cipollo on 11/4/16.
 */
interface ActivityView {
    fun switchScreen(newScreen: Screen)
}