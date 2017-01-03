package viper.rx

import nucleus.presenter.Presenter
import nucleus.presenter.RxPresenter
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Utility functions and extensions for RxJava.
 * Created by Nick Cipollo on 1/9/17.
 */

/**
 * Adds the subscription to a presenter. The subscription will be unsubscribed when the
 * presenter is destroyed.
 */
fun Subscription.addTo(presenter:RxPresenter<*>) {
    presenter.add(this)
}