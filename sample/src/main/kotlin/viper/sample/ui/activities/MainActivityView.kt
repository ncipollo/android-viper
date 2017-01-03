package viper.sample.ui.activities

import viper.sample.dagger.InteractorComponent
import viper.view.activities.ActivityView

/**
 * A view interface which represents the main activity in the sample app. Provides
 * a dagger component for injecting interactors.
 * Created by Nick Cipollo on 1/3/17.
 */
interface MainActivityView : ActivityView {
    val interactorComponent: InteractorComponent
}