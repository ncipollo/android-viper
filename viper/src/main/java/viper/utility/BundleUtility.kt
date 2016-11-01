package viper.utility

import android.os.Bundle

/**
 * Provides useful extensions to the bundle class.
 * Created by Nick Cipollo on 11/1/16.
 */

/**
 * Converts the bundle to a string map.
 */
fun Bundle.toStringMap(): Map<String, String> {
    val map = mutableMapOf<String, String>()
    for(key in this.keySet()) {
        val value = this.getString(key)
        value?.let { map[key] = it }
    }
    return map
}

/**
 * Creates a bundle from a string map.
 */
fun bundleFromStringMap(map: Map<String,String>) : Bundle {
    val bundle = Bundle()
    for ((key, value) in map) {
        bundle.putString(key, value)
    }
    return bundle
}
