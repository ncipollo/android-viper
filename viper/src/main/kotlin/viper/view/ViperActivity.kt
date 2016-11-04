package viper.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import nucleus.factory.PresenterFactory
import nucleus.factory.ReflectionPresenterFactory
import nucleus.presenter.RxPresenter
import nucleus.view.PresenterLifecycleDelegate
import nucleus.view.ViewWithPresenter
import viper.Viper
import viper.presenter.ActivityPresenter
import viper.routing.Router
import viper.routing.Screen

/**
 * Base viper activity. This is a nucleus compatible activity which is based upon the
 * AppCompatActivity, as opposed to the standard Activity class.
 * Created by Nick Cipollo on 10/31/16.
 */
open class ViperActivity<P : RxPresenter<*>> : AppCompatActivity(), ViewWithPresenter<P> {
    private val PRESENTER_STATE_KEY = "presenter_state"
    private val presenterDelegate =
            PresenterLifecycleDelegate(ReflectionPresenterFactory.fromViewClass<P>(javaClass))
    protected var screen: Screen? = null
    private val router: Router?
        get() = Viper.router
    /**
     * Returns an activity presenter if one exists and is assigned as this activity's presenter.
     */
    val activityPresenter: ActivityPresenter<*>?
        get() = presenter as? ActivityPresenter<*>

    /**
     * Triggers a screen switch which may start a new activity and / or update the activities
     * fragments.
     */
    fun switchScreen(newScreen: Screen) {
        if (screen?.activity != newScreen.activity && newScreen.activity != null) {
            router?.switchActivity(this,newScreen)
            return
        }
        val fragments = router?.createFragments(newScreen)
        if (fragments != null) {
            updateFragments(fragments)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    protected fun updateFragments(fragments: Map<String,ViperFragment<*>>) {
        // Subclasses will handle
    }

    override fun setPresenterFactory(presenterFactory: PresenterFactory<P>?) {
        presenterDelegate.presenterFactory = presenterFactory
    }

    override fun getPresenter(): P = presenterDelegate.presenter

    override fun getPresenterFactory(): PresenterFactory<P>? = presenterDelegate.presenterFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Load the screen if it exists
        intent?.extras?.getBundle("screen")?.let {
            screen = Screen(it)
        }
        if (savedInstanceState != null) {
            presenterDelegate.onRestoreInstanceState(savedInstanceState.getBundle(PRESENTER_STATE_KEY))
        }
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
}