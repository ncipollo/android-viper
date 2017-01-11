package viper.presenters

import android.os.Bundle
import nucleus.presenter.RxPresenter
import viper.view.fragments.FragmentView

/**
 * A presenter which is associated with a fragment. This will typically manage screen interactions
 * specific to the current use case displayed in the app.
 * Created by Nick Cipollo on 10/31/16.
 */
open class FragmentPresenter<View : FragmentView, Interactors : Any> : RxPresenter<View>() {
    var activityPresenter: ActivityPresenter<*, *>? = null
    lateinit var interactors: Interactors
        private set
    lateinit var args: Bundle
        private set

    fun takeInteractors(interactors: Interactors) {
        this.interactors = interactors
        onTakeInteractors(interactors)
    }

    /**
     * This method will be called when the interactors are passed into the presenter. This will
     * occur each time the view is associated with the presenter. Subclasses may utilize this
     * method to perform setup with the interactors.
     */
    open fun onTakeInteractors(interactors: Interactors) = Unit

    /**
     * This method will be called when another presenter pops back to this one with arguments.
     */
    open fun onPresenterResult(arguments: Bundle) = Unit

    override fun onTakeView(view: View) {
        super.onTakeView(view)
        args = view.args
        activityPresenter = view.activityPresenter
        val interactors = activityPresenter?.interactors
        if (interactors != null) {
            @Suppress("UNCHECKED_CAST")
            takeInteractors(interactors as Interactors)
        }
        // Handle the case were we are returning back from a presenter with results
        val backArgs = activityPresenter?.claimBackArgs()
        backArgs?.let { onPresenterResult(it) }
    }

    override fun onDropView() {
        super.onDropView()
        activityPresenter = null
    }

    /**
     * Moves to the next screen in the flow.
     */
    fun moveToNextScreen(screenId: Int, arguments: Bundle) {
        activityPresenter?.moveToNextScreen(screenId, arguments)
    }

    /**
     * Moves back to the previous screen.
     */
    fun moveBack(arguments: Bundle? = null) {
        activityPresenter?.moveBack(arguments)
    }
}