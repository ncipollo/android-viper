package viper.sample.dagger

import dagger.Component
import dagger.Subcomponent
import viper.sample.model.interactors.SampleInteractors
import javax.inject.Singleton

/**
 * Created by Nick Cipollo on 1/3/17.
 */
@Subcomponent
@Singleton
interface InteractorComponent {
    fun interactors() : SampleInteractors
}