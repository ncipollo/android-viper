package viper.view.fragments

import viper.presenters.CollectionPresenter
import viper.view.adapters.ViperRecyclerAdapter

/**
 * A CollectionFragment which may be used with a recycler view. It's CollectionAdapter is
 * expected to be a ViperRecyclerAdapter.
 * Created by Nick Cipollo on 12/21/16.
 */
abstract class ViperRecyclerFragment<P : CollectionPresenter<*, *, *>>
    : CollectionFragment<P, ViperRecyclerAdapter<*, *, P>>()