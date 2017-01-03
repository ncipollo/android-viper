package viper.sample.dagger

import dagger.Component
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * The sample app's production dagger component. This is used when the app is running (as opposed
 * to during unit tests).
 * Created by Nick Cipollo on 1/3/17.
 */
@Component(modules = arrayOf(APIModule::class))
@Singleton
interface ProductionAppComponent {
    fun createInteractorComponent() : InteractorComponent
}