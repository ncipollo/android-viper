package viper.view.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import nucleus.factory.PresenterFactory
import nucleus.factory.ReflectionPresenterFactory
import nucleus.view.PresenterLifecycleDelegate
import nucleus.view.ViewWithPresenter
import viper.presenters.ActivityPresenter
import viper.routing.Flow
import viper.routing.TransitionOptions

/**
 * Base viper activity. This is a nucleus compatible activity which is based upon the
 * AppCompatActivity, as opposed to the standard Activity class.
 * Created by Nick Cipollo on 10/31/16.
 */
abstract class ViperActivity<P : ActivityPresenter<*>>
    : AppCompatActivity(), ViewWithPresenter<P>, ActivityView {
    private val PRESENTER_STATE_KEY = "presenter_state"
    private val presenterDelegate =
            PresenterLifecycleDelegate(ReflectionPresenterFactory.fromViewClass<P>(javaClass))
    lateinit var flow: Flow
        private set

    /**
     * Subclasses must override and create a flow for this activity.
     */
    abstract fun createFlow(): Flow

    /**
     * Sets the activity's fragments. By default this will add all fragments into a replace
     * fragment transaction, using each key as the resource Id. Subclasses may override to
     * customize this behavior.
     */
    open fun setFragments(fragments: Map<Int, Fragment>,
                          options: TransitionOptions,
                          initialFragments: Boolean) {
        val transaction = supportFragmentManager?.beginTransaction()
        if (!initialFragments && transaction != null) {
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
        if (savedInstanceState != null) {
            presenterDelegate.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY))
        }
        flow = createFlow()
    }

    override fun onContentChanged() {
        super.onContentChanged()
        setFragments(flow.initialFragments, TransitionOptions.default, true)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState())
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

    override fun moveToNextScreen(screenId: Int, arguments: Bundle, options: TransitionOptions?) {
        flow.intentForScreen(screenId, arguments, this)?.let {
            startActivity(it, arguments)
            return
        }
        setFragments(flow.fragmentsForScreen(screenId, Bundle(arguments)),
                options ?: flow.defaultTransitionOptions,
                false)
    }

    override fun setPresenterFactory(presenterFactory: PresenterFactory<P>?) {
        presenterDelegate.presenterFactory = presenterFactory
    }

    override fun getPresenter(): P = presenterDelegate.presenter

    override fun getPresenterFactory(): PresenterFactory<P>? = presenterDelegate.presenterFactory
}