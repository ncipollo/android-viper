package viper.sample.ui.fragments

import viper.view.fragments.CollectionView

/**
 * View which represents a collection view in the sample app.
 * Created by Nick Cipollo on 1/10/17.
 */
interface SampleCollectionView: CollectionView {
    fun finishRefresh()
}