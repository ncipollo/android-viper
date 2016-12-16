package viper.presenters

import android.os.Bundle
import nucleus.presenter.RxPresenter
import rx.Observable
import viper.view.activities.ActivityView

/**
 * A Presenter which is associated with an activity. Generally this will manage high level
 * layout and navigation.
 * Created by Nick Cipollo on 10/31/16.
 */
open class ActivityPresenter<View : ActivityView> : RxPresenter<View>() {
    companion object {
        val SCREEN_SWITCH = 10001
    }
    /**
     * Moves to the next screen in the flow.
     */
    fun moveToNextScreen(screenId: Int, arguments: Bundle) {
        restartableFirst(SCREEN_SWITCH,
                { Observable.just(Pair(screenId, arguments)) },
                { view, params -> view?.moveToNextScreen(params.first, params.second) })
    }
}