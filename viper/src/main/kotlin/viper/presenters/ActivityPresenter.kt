package viper.presenters

import android.os.Bundle
import nucleus.presenter.RxPresenter
import rx.Observable
import viper.routing.Flow
import viper.view.activities.ActivityView

/**
 * A Presenter which is associated with an activity. Generally this will manage high level
 * layout and navigation.
 * Created by Nick Cipollo on 10/31/16.
 */
abstract class ActivityPresenter<View : ActivityView> : RxPresenter<View>() {
    companion object {
        val SCREEN_SWITCH = 10001
    }
    lateinit var flow: Flow
        private set

    /**
     * Subclasses must override and create a flow for this activity.
     */
    abstract fun createFlow(): Flow

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        flow = createFlow()
    }

    /**
     * Moves to the next screen in the flow.
     */
    fun moveToNextScreen(screenId: Int, arguments: Bundle) {
        stop(SCREEN_SWITCH)
        restartableFirst(SCREEN_SWITCH,
                {
                    Observable.just(ScreenSwitchParams(screenId, arguments))
                },
                { view, params ->
                    view?.moveToNextScreen(params.screenId,
                            params.arguments)
                })
        start(SCREEN_SWITCH)
    }

    data class ScreenSwitchParams(val screenId: Int,
                                  val arguments: Bundle)
}