package viper

import viper.routing.ActivityFactory
import viper.routing.Flow
import viper.routing.FragmentFactory
import viper.routing.Router

/**
 * Top level viper class. Used to configure shared objects like the router.
 * Created by Nick Cipollo on 11/1/16.
 */
object Viper {
    internal var router : Router? = null
    fun setupRouter(activityFactory: ActivityFactory,
                    fragmentFactory: FragmentFactory,
                    flow: Flow? = null) {
        router = Router(activityFactory,fragmentFactory,flow)
    }
}