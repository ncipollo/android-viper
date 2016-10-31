package viper.view

import nucleus.presenter.Presenter
import nucleus.view.NucleusFragment

/**
 * Base Viper fragment. Based upon a NucleusFragment.
 * Created by Nick Cipollo on 10/31/16.
 */
class ViperFragment<P : Presenter<*>> : NucleusFragment<P>() {
    val viperActivity: ViperActivity<*>?
        get() = activity as? ViperActivity<*>
}