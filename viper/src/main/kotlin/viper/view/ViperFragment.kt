package viper.view

import nucleus.presenter.RxPresenter
import nucleus.view.NucleusFragment

/**
 * Base Viper fragment. Based upon a NucleusFragment.
 * Created by Nick Cipollo on 10/31/16.
 */
open class ViperFragment<P : RxPresenter<*>> : NucleusFragment<P>() {
    val viperActivity: ViperActivity<*>?
        get() = activity as? ViperActivity<*>
}