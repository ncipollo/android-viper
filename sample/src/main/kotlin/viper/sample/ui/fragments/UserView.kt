package viper.sample.ui.fragments

import rx.Observable
import viper.view.fragments.FragmentView

/**
 * Represents a view which allows for the entry of the GitHub user name.
 * Created by Nick Cipollo on 1/8/17.
 */
interface UserView: FragmentView {
    var user: String
    val onUserChanged : Observable<CharSequence>
    var doneEnabled: Boolean
}