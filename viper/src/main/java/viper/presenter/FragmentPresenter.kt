package viper.presenter

import android.os.Bundle
import nucleus.presenter.RxPresenter
import viper.routing.Screen
import viper.view.ViperFragment

/**
 * A presenter which is associated with a fragment. This will typically manage screen interactions
 * specific to the current use case displayed in the app.
 * Created by Nick Cipollo on 10/31/16.
 */
open class FragmentPresenter<View:ViperFragment<*>> : RxPresenter<View>() {
    var activityPresenter: ActivityPresenter<*>? = null

    override fun onTakeView(view: View) {
        super.onTakeView(view)
        activityPresenter = view.viperActivity?.activityPresenter
    }

    override fun onDropView() {
        super.onDropView()
        activityPresenter = null
    }

    /**
     * Triggers a screen switch which may start a new activity and / or update the activity's
     * fragments.
     */
    fun switchScreen(newScreen: Screen) {
        activityPresenter?.switchScreen(newScreen)
    }

    /**
     * Moves to the next screen in the router's flow.
     */
    fun moveToNextScreenInFlow(action: Int, arguments: Bundle) {
        activityPresenter?.moveToNextScreenInFlow(action,arguments)
    }
}