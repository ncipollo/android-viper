package viper.presenter

import nucleus.presenter.Presenter
import viper.view.ViperActivity

/**
 * A Presenter which is associated with an activity. Generally this will manage high level
 * layout and navigation.
 * Created by Nick Cipollo on 10/31/16.
 */
class ActivityPresenter<View: ViperActivity<*>> : Presenter<View>()  {
}