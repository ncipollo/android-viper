package viper.sample.ui.presenters

import android.os.Bundle
import viper.presenters.FragmentPresenter
import viper.sample.ui.flow.SampleFlow
import viper.sample.ui.fragments.UserFragment

/**
 * Handles the presentation of the user fragment.
 * Created by Nick Cipollo on 12/16/16.
 */

class UserPresenter : FragmentPresenter<UserFragment>() {
    fun selectUser(user: String) {
        val args = Bundle()
        args.putString(SampleFlow.ARGS_USER,user)
        moveToNextScreen(SampleFlow.SCREEN_REPOS, args)
    }
}