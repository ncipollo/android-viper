package viper.routing

import android.os.Bundle
import java.util.*

/**
 * Describes what should be rendered on the screen.
 * Created by Nick Cipollo on 10/31/16.
 */
data class Screen(val activity: String?,
                  val fragments: List<String>?,
                  val args: Bundle?) {

    constructor(bundle: Bundle) : this(bundle.getString("activity"),
            bundle.getStringArrayList("fragments"),
            bundle.getBundle("args"))

    fun toBundle(): Bundle {
        val bundle = Bundle()
        activity?.let {
            bundle.putString("activity", it)
        }
        (fragments as? ArrayList<String>)?.let {
            bundle.putStringArrayList("fragments", it)
        }
        args?.let {
            bundle.putBundle("args",bundle)
        }
        return bundle
    }
}