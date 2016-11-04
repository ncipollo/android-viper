package viper.presenter

import android.os.Bundle
import nucleus.presenter.RxPresenter
import rx.Observable
import viper.Viper
import viper.routing.Router
import viper.routing.Screen
import viper.view.ActivityView
import viper.view.ViperActivity

/**
 * A Presenter which is associated with an activity. Generally this will manage high level
 * layout and navigation.
 * Created by Nick Cipollo on 10/31/16.
 */
open class ActivityPresenter<View : ActivityView> : RxPresenter<View>() {
    companion object {
        val SCREEN_SWITCH = 10001
    }

    private val router: Router?
        get() = Viper.router

    /**
     * Triggers a screen switch which may start a new activity and / or update the activity's
     * fragments.
     */
    fun switchScreen(newScreen: Screen) {
        restartableFirst(SCREEN_SWITCH,
                { Observable.just(newScreen) },
                { view, screen -> view?.switchScreen(screen) })
        start(SCREEN_SWITCH)
    }

    /**
     * Moves to the next screen in the router's flow.
     */
    fun moveToNextScreenInFlow(action: Int, arguments: Bundle) {
        val screen = router?.flow?.nextScreen(action, arguments)
        if (screen != null) {
            switchScreen(screen)
        }
    }
}