package viper.routing

import android.os.Bundle
import viper.utility.bundleFromStringMap
import viper.utility.toStringMap
import java.util.*

/**
 * Describes what should be rendered on the screen.
 * Created by Nick Cipollo on 10/31/16.
 */
data class Screen(val activity: String?,
                  val fragments: Map<String, String>?,
                  val args: Bundle?) {

    constructor(bundle: Bundle) : this(bundle.getString("activity"),
            bundle.getBundle("fragments")?.toStringMap(),
            bundle.getBundle("args"))

    fun toBundle(): Bundle {
        val bundle = Bundle()
        activity?.let {
            bundle.putString("activity", it)
        }
        fragments?.let {
            bundle.putBundle("fragments", bundleFromStringMap(fragments))
        }
        args?.let {
            bundle.putBundle("args", bundle)
        }
        return bundle
    }

    fun screenHasNewActivity(screen: Screen): Boolean {
        return screen.activity == activity
    }

    fun newFragmentsFromScreen(screen: Screen) : Map<String,String> {
        if (screen.fragments == null) {
            // No fragments, simply return an empty map.
            return mapOf<String,String>()
        }
        // Add only the fragments which are different from this screen.
        val frags = HashMap<String,String>()
        for ((key,fragment) in screen.fragments) {
            if (screen.fragments[key] != fragments?.get(key)) {
                frags[key] = fragment
            }
        }
        return frags
    }
}