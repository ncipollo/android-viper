package viper.routing

import android.content.Context
import android.content.Intent

/**
 * Represents an object which will generate an activity intent for the provided screen.
 * Created by Nick Cipollo on 11/1/16.
 */
interface ActivityFactory {
    fun createActivityIntent(context: Context,screen: Screen): Intent?
}