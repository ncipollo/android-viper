package viper.sample.ui.presenters

import android.os.Bundle
import rx.Observable
import rx.Subscription
import viper.presenters.FragmentPresenter
import viper.sample.model.interactors.SampleInteractors
import viper.sample.ui.fragments.UserView
import viper.sample.ui.router.SampleFlow

/**
 * Handles the presentation of the user fragment.
 * Created by Nick Cipollo on 12/16/16.
 */

class UserPresenter : FragmentPresenter<UserView, SampleInteractors>() {
    val UPDATE_DONE_ENABLED = 1
    var userSub: Subscription? = null

    override fun onTakeView(view: UserView) {
        super.onTakeView(view)
        validateUser(view.user)
        userSub = view.onUserChanged
                .subscribe {
                    validateUser(it.toString())
                }
    }

    override fun onDropView() {
        super.onDropView()
        userSub?.unsubscribe()
        userSub = null
    }

    fun validateUser(user:String) {
        val valid = isUserValid(user)
        stop(UPDATE_DONE_ENABLED)
        restartableFirst(UPDATE_DONE_ENABLED,
                { Observable.just(valid) },
                { view, enabled -> view.doneEnabled = enabled })
        start(UPDATE_DONE_ENABLED)
    }

    fun isUserValid(user:String) = user.isNotEmpty()

    fun selectUser(user: String) {
        val args = Bundle()
        args.putString(SampleFlow.ARGS_USER, user)
        moveToNextScreen(SampleFlow.SCREEN_REPOS, args)
    }
}