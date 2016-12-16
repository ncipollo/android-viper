package viper.routing

import android.os.Build
import android.support.annotation.AnimRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

/**
 * Options which may be used during a screen transition.
 * Created by Nick Cipollo on 12/27/16.
 */
class TransitionOptions(@AnimRes val enterAnimation: Int = 0,
                        @AnimRes val exitAnimation: Int = 0,
                        @AnimRes val enterPopAnimation: Int = 0,
                        @AnimRes val exitPopAnimation: Int = 0,
                        val addToBackStack: Boolean = true,
                        val transactionCustomizer: ((FragmentTransaction) -> Unit)? = null,
                        val lollipopTransitioner: ((Fragment) -> Unit)? = null) {
    companion object {
        @JvmStatic
        val default = TransitionOptions()
    }

    val shouldUseLollipopTransitions: Boolean
        get() = lollipopTransitioner != null
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}