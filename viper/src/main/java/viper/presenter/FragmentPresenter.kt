package viper.presenter

import nucleus.presenter.Presenter
import viper.view.ViperFragment

/**
 * A presenter which is associated with a fragment. This will typically manage screen interactions
 * specific to the current use case displayed in the app.
 * Created by Nick Cipollo on 10/31/16.
 */
class FragmentPresenter<View:ViperFragment<*>> : Presenter<View>() {
    val activityPresenter: ActivityPresenter<*>?
        get() = view?.viperActivity?.activityPresenter
}