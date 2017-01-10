package viper.sample.ui.fragments

import viper.view.fragments.CollectionView

/**
 * Represents a view which may display a collection of git items.
 * Created by Nick Cipollo on 1/10/17.
 */
interface GitCollectionView : CollectionView {
    fun finishRefresh()
    fun onError(error:Throwable)
}