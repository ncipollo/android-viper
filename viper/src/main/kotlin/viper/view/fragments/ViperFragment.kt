package viper.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import nucleus.factory.PresenterFactory
import nucleus.factory.ReflectionPresenterFactory
import nucleus.presenter.RxPresenter
import nucleus.view.PresenterLifecycleDelegate
import nucleus.view.ViewWithPresenter
import viper.presenters.ActivityPresenter
import viper.view.activities.ViperActivity

/**
 * Base Viper fragment. Based upon a NucleusFragment.
 * Created by Nick Cipollo on 10/31/16.
 */
open class ViperFragment<P : RxPresenter<*>> : FragmentView, ViewWithPresenter<P>, Fragment() {
    val viperActivity: ViperActivity<*>?
        get() = activity as? ViperActivity<*>
    override val activityPresenter: ActivityPresenter<*>?
        get() = viperActivity?.activityPresenter

    private val PRESENTER_STATE_KEY = "presenter_state"
    private val presenterDelegate = PresenterLifecycleDelegate(ReflectionPresenterFactory.fromViewClass<P>(javaClass))

    override fun getPresenterFactory(): PresenterFactory<P>? = presenterDelegate.presenterFactory

    override fun setPresenterFactory(presenterFactory: PresenterFactory<P>) {
        presenterDelegate.presenterFactory = presenterFactory
    }

    override fun getPresenter(): P =  presenterDelegate.presenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        if (bundle != null)
            presenterDelegate.onRestoreInstanceState(bundle.getBundle(PRESENTER_STATE_KEY))
    }

    override fun onSaveInstanceState(bundle: Bundle?) {
        super.onSaveInstanceState(bundle)
        bundle?.putBundle(PRESENTER_STATE_KEY, presenterDelegate.onSaveInstanceState())
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

    /**
     * Check to see whether this activity is in the process of being
     * destroyed in order to be recreated with a new configuration.
     * @return
     */
    val isChangingConfigurations: Boolean
        get() = activity?.isChangingConfigurations ?: false

}