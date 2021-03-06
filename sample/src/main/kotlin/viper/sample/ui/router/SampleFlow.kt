package viper.sample.ui.router

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import viper.routing.Flow
import viper.routing.TransitionOptions
import viper.sample.R
import viper.sample.ui.fragments.BranchesFragment
import viper.sample.ui.fragments.CommitsFragment
import viper.sample.ui.fragments.ReposFragment
import viper.sample.ui.fragments.UserFragment

/**
 * The UX flow for the sample app.
 * Created by Nick Cipollo on 12/16/16.
 */
class SampleFlow : Flow {
    companion object {
        val SCREEN_REPOS = 1
        val SCREEN_COMMITS = 2
        val SCREEN_BRANCHES = 3
        val ARGS_USER = "user"
        val ARGS_REPO = "repo"
        val ARGS_BRANCH = "branch"
    }

    override val initialFragments: Map<Int, Fragment>
        get() = mapOf(R.id.content to UserFragment())

    override fun optionsForScreenTransition(screen: Int, args: Bundle): TransitionOptions {
        return TransitionOptions(enterAnimation = R.anim.slide_in_right,
                exitAnimation = R.anim.slide_out_left,
                enterPopAnimation = R.anim.slide_in_left,
                exitPopAnimation = R.anim.slide_out_right)
    }

    override fun intentForScreen(screenId: Int, args: Bundle, context: Context): Intent? {
        return null // We only have a single activity.
    }

    override fun fragmentsForScreen(screen: Int, args: Bundle): Map<Int, Fragment> {
        val fragment = when (screen) {
            SCREEN_REPOS -> ReposFragment()
            SCREEN_COMMITS -> CommitsFragment()
            SCREEN_BRANCHES -> BranchesFragment()
            else -> null
        }
        if (fragment != null) {
            fragment.arguments = args
            return mapOf(R.id.content to fragment)
        }
        return mapOf()
    }
}