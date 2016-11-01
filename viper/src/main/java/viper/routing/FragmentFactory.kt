package viper.routing

import viper.view.ViperFragment

/**
 * Represents an object which will generate fragments for the provided screen.
 * Created by Nick Cipollo on 11/1/16.
 */
interface FragmentFactory {
    fun createFragments(screen:Screen) : Map<String,ViperFragment<*>>?
}