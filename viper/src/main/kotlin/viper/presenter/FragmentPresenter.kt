package viper.presenter

import android.os.Bundle
import nucleus.presenter.RxPresenter
import viper.Viper
import viper.routing.Router
import viper.routing.Screen
import viper.view.FragmentView

/**
 * A presenter which is associated with a fragment. This will typically manage screen interactions
 * specific to the current use case displayed in the app.
 * Created by Nick Cipollo on 10/31/16.
 */
open class FragmentPresenter<View:FragmentView> : RxPresenter<View>() {
    var activityPresenter: ActivityPresenter<*>? = null

    private val router: Router?
        get() = Viper.router

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        router?.interactorInjector?.injectInteractor(this)
    }

    override fun onTakeView(view: View) {
        super.onTakeView(view)
        activityPresenter = view.activityPresenter
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