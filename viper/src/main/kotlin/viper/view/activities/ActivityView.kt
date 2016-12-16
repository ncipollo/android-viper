package viper.view.activities

import android.os.Bundle
import viper.routing.TransitionOptions

/**
 * A view which represents an activity.
 * Created by Nick Cipollo on 11/4/16.
 */
interface ActivityView {
    fun moveToNextScreen(screenId: Int, arguments: Bundle,options: TransitionOptions?)
}