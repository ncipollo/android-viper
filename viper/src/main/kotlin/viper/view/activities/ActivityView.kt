package viper.view.activities

import android.os.Bundle

/**
 * A view which represents an activity.
 * Created by Nick Cipollo on 11/4/16.
 */
interface ActivityView {
    fun moveToNextScreen(screenId: Int, arguments: Bundle)
    fun moveBack()
    /**
     * Returns the argument bundle which was provided to the view.
     */
    val args: Bundle
}