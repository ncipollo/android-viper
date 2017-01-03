package viper.sample.ui.presenters

import viper.presenters.ActivityPresenter
import viper.routing.Flow
import viper.sample.model.interactors.SampleInteractors
import viper.sample.ui.activities.MainActivityView
import viper.sample.ui.router.SampleFlow

/**
 * Activity presenter for the sample app. Creates the sample flow.
 * Created by Nick Cipollo on 1/1/17.
 */
class MainActivityPresenter : ActivityPresenter<MainActivityView,SampleInteractors>() {
    override fun createInteractors(view: MainActivityView): SampleInteractors {
        return view.interactorComponent.interactors()
    }

    override fun createFlow(): Flow = SampleFlow()
}