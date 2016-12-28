package viper.routing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * A Flow represents a route through an activity. The flow defines the initial fragment set and
 * manages fragment and activity updates based on user interaction. An activity will typically
 * have a single flow which is created when the activity is.
 *
 * Created by Nick Cipollo on 10/31/16.
 */
interface Flow {
    /**
     * Returns the initial fragments for the activity. The returned map will contain each fragment
     * mapped to an integer identifier. The identifier may map to the resource ID of the fragment's
     * container layout (depending upon the activity).
     */
    val initialFragments: Map<Int, Fragment>

    /**
     * Returns an intent which will start a new activity or null if the action does not represent
     * an activity switch.
     */
    fun intentForScreen(screenId: Int, args: Bundle, context: Context): Intent?

    /**
     * Returns the fragment map for the action. The returned map will contain each fragment
     * mapped to an integer identifier. The identifier may map to the resource ID of the fragment's
     * container layout (depending upon the activity).
     */
    fun fragmentsForScreen(screen: Int, args: Bundle): Map<Int, Fragment>

    /**
     * Returns the the options to be used with the current screen transition. Implementing
     * classes may override to customize transition behavior. By default this will return
     * TransitionOptions.default
     */
    fun optionsForScreenTransition(screen: Int, args: Bundle): TransitionOptions {
        return TransitionOptions.default
    }
}

