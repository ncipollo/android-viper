package viper.sample.ui.presenters

import viper.presenters.ActivityPresenter
import viper.routing.Flow
import viper.sample.ui.activities.MainActivity
import viper.sample.ui.flow.SampleFlow

/**
 * Activity presenter for the sample app. Creates the sample flow.
 * Created by Nick Cipollo on 1/1/17.
 */
class MainActivityPresenter : ActivityPresenter<MainActivity>() {
    override fun createFlow(): Flow = SampleFlow()
}