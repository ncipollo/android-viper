package viper.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import nucleus.factory.PresenterFactory
import nucleus.factory.ReflectionPresenterFactory
import nucleus.presenter.RxPresenter
import nucleus.view.PresenterLifecycleDelegate
import nucleus.view.ViewWithPresenter
import viper.presenter.ActivityPresenter
import viper.routing.Flow

/**
 * Base viper activity. This is a nucleus compatible activity which is based upon the
 * AppCompatActivity, as opposed to the standard Activity class.
 * Created by Nick Cipollo on 10/31/16.
 */
abstract class ViperActivity<P : RxPresenter<*>>
    : AppCompatActivity(), ViewWithPresenter<P>, ActivityView {
    private val PRESENTER_STATE_KEY = "presenter_state"
    private val presenterDelegate =
            PresenterLifecycleDelegate(ReflectionPresenterFactory.fromViewClass<P>(javaClass))
    lateinit var flow: Flow
        private set
    /**
     * Returns an activity presenter if one exists and is assigned as this activity's presenter.
     */
    val activityPresenter: ActivityPresenter<*>?
        get() = presenter as? ActivityPresenter<*>

    /**
     * Subclasses must override and create a flow for this activity.
     */
    abstract fun createFlow(): Flow

    /**
     * Sets the activity's fragments. By default this will add all fragments into a replace
     * fragment transaction, using each key as the resource Id. Subclasses may override to
     * customize this behavior.
     */
    open fun setFragments(fragments: Map<Int, Fragment>, initialFragments: Boolean) {
        val transaction = supportFragmentManager?.beginTransaction()
        for ((id, fragment) in fragments) {
            transaction?.replace(id, fragment)
        }
        if (initialFragments) {
            transaction?.addToBackStack(null)
        }
        transaction?.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            presenterDelegate.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY))
        }
        flow = createFlow()
        setFragments(flow.initialFragments,false)
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

    override fun moveToNextScreen(screenId: Int, arguments: Bundle) {
        flow.intentForScreen(screenId,arguments,this)?.let {
            startActivity(it,arguments)
            return
        }
        setFragments(flow.fragmentsForScreen(screenId,arguments),true)
    }

    override fun setPresenterFactory(presenterFactory: PresenterFactory<P>?) {
        presenterDelegate.presenterFactory = presenterFactory
    }

    override fun getPresenter(): P = presenterDelegate.presenter

    override fun getPresenterFactory(): PresenterFactory<P>? = presenterDelegate.presenterFactory
}