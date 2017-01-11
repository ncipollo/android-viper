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
abstract class ActivityPresenter<View : ActivityView, Interactors : Any> : RxPresenter<View>() {
    companion object {
        val SCREEN_SWITCH = 10001
        val POP_BACK = 10002
    }

    lateinit var flow: Flow
        private set
    lateinit var args: Bundle
        private set
    private var needsInteractors = true
    lateinit var interactors: Interactors
        private set
    private var backArgs: Bundle? = null
    /**
     * Subclasses must override and create a flow for this activity.
     */
    abstract fun createFlow(): Flow

    abstract fun createInteractors(view: View): Interactors

    override fun onCreate(savedState: Bundle?) {
        super.onCreate(savedState)
        flow = createFlow()
    }

    override fun onTakeView(view: View) {
        super.onTakeView(view)
        args = Bundle(view.args)
        if (needsInteractors) {
            interactors = createInteractors(view)
            needsInteractors = false
        }
        takeInteractors(interactors)
    }

    fun takeInteractors(interactors: Interactors) {
        onTakeInteractors(interactors)
    }

    /**
     * This method will be called when the interactors are passed into the presenter. This will
     * occur each time the view is associated with the presenter. Subclasses may utilize this
     * method to perform setup with the interactors.
     */
    open fun onTakeInteractors(interactors: Interactors) = Unit

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

    /**
     * Moves to the previous screen.
     */
    fun moveBack(arguments: Bundle? = null) {
        stop(POP_BACK)
        restartableFirst(POP_BACK,
                {
                    Observable.just(arguments)
                },
                { view, args ->
                    backArgs = args
                    view?.moveBack()
                })
        start(POP_BACK)
    }

    internal fun claimBackArgs(): Bundle? {
        val args = backArgs
        backArgs = null
        return args
    }

    data class ScreenSwitchParams(val screenId: Int,
                                  val arguments: Bundle)
}