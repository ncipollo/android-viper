package viper.view.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import nucleus.factory.PresenterFactory
import nucleus.factory.ReflectionPresenterFactory
import nucleus.view.PresenterLifecycleDelegate
import nucleus.view.ViewWithPresenter
import viper.presenters.ActivityPresenter
import viper.routing.TransitionOptions

/**
 * Base viper activity. This is a nucleus compatible activity which is based upon the
 * AppCompatActivity, as opposed to the standard Activity class.
 * Created by Nick Cipollo on 10/31/16.
 */
open class ViperActivity<P : ActivityPresenter<*,*>>
    : AppCompatActivity(), ViewWithPresenter<P>, ActivityView {
    private val PRESENTER_STATE_KEY = "presenter_state"
    private val presenterDelegate =
            PresenterLifecycleDelegate(ReflectionPresenterFactory.fromViewClass<P>(javaClass))
    private var needsInitialFragments = false
    override val args: Bundle
        get() = intent?.extras ?: Bundle()

    /**
     * Sets the activity's fragments. By default this will add all fragments into a replace
     * fragment transaction, using each key as the resource Id. Subclasses may override to
     * customize this behavior.
     */
    open fun setFragments(fragments: Map<Int, Fragment>,
                          options: TransitionOptions? = null) {
        val transaction = supportFragmentManager?.beginTransaction()
        if (options != null && transaction != null) {
            if (options.shouldUseLollipopTransitions) {
                fragments.values.forEach {
                    options.lollipopTransitioner?.invoke(it)
                }
            } else {
                transaction.setCustomAnimations(options.enterAnimation,
                        options.exitAnimation,
                        options.enterPopAnimation,
                        options.exitPopAnimation)
            }
            if (options.addToBackStack) {
                transaction.addToBackStack(null)
            }
            options.transactionCustomizer?.invoke(transaction)
        }
        for ((id, fragment) in fragments) {
            transaction?.replace(id, fragment)
        }
        transaction?.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        needsInitialFragments = savedInstanceState == null
        if (savedInstanceState != null) {
            presenterDelegate.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState())
    }

    override fun onStart() {
        super.onStart()
        if (needsInitialFragments) {
            setFragments(presenter.flow.initialFragments)
            needsInitialFragments = false
        }
    }

    override fun onResume() {
        super.onResume()
        presenterDelegate.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        presenterDelegate.onDropView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenterDelegate.onDestroy(!isChangingConfigurations)
    }

    override fun moveToNextScreen(screenId: Int, arguments: Bundle) {
        presenter.flow.intentForScreen(screenId, arguments, this)?.let {
            startActivity(it, arguments)
            return
        }
        val args = Bundle(arguments)
        setFragments(presenter.flow.fragmentsForScreen(screenId, args),
                presenter.flow.optionsForScreenTransition(screenId, args))
    }

    override fun moveBack() {
        supportFragmentManager?.popBackStack()
    }

    override fun setPresenterFactory(presenterFactory: PresenterFactory<P>?) {
        presenterDelegate.presenterFactory = presenterFactory
    }

    override fun getPresenter(): P = presenterDelegate.presenter

    override fun getPresenterFactory(): PresenterFactory<P>? = presenterDelegate.presenterFactory
}