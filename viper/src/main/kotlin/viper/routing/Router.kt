package viper.routing

import viper.interactor.InteractorInjector
import viper.view.ViperActivity
import viper.view.ViperFragment

/**
 * Manages navigation routing through an app.
 * Created by Nick Cipollo on 11/1/16.
 */
class Router(val activityFactory: ActivityFactory,
             val fragmentFactory: FragmentFactory,
             val interactorInjector: InteractorInjector,
             var flow : Flow?) {

    fun switchActivity(currentActivity: ViperActivity<*>,screen: Screen) {
        val intent =  activityFactory.createActivityIntent(currentActivity,screen)
        if (intent != null) {
            intent.putExtra("screen",screen.toBundle())
            currentActivity.startActivity(intent)
        }
    }

    fun createFragments(screen: Screen) : Map<String,ViperFragment<*>> {
        return fragmentFactory.createFragments(screen) ?: mapOf()
    }
}