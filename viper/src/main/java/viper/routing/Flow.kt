package viper.routing

import android.os.Parcelable

/**
 * A flow represents a route through an app. A flow will return the next screen the app
 * should navigate to for a given action.
 * Created by Nick Cipollo on 10/31/16.
 */
interface Flow {
    fun <T : Parcelable> nextScreen(action: Int, argument: T?): Screen
}