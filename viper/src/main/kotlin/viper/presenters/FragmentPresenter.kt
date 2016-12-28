package viper.presenters

import android.os.Bundle
import nucleus.presenter.RxPresenter
import viper.routing.TransitionOptions
import viper.view.fragments.FragmentView

/**
 * A presenter which is associated with a fragment. This will typically manage screen interactions
 * specific to the current use case displayed in the app.
 * Created by Nick Cipollo on 10/31/16.
 */
open class FragmentPresenter<View : FragmentView> : RxPresenter<View>() {
    var activityPresenter: ActivityPresenter<*>? = null

    override fun onTakeView(view: View) {
        super.onTakeView(view)
        activityPresenter = view.activityPresenter
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
}