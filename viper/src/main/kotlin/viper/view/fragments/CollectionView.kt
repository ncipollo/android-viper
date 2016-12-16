package viper.view.fragments

/**
 * Represents a view which can receive collection updates.
 * Created by Nick Cipollo on 11/3/16.
 */
interface CollectionView : FragmentView {
    fun onCollectionUpdated()
    fun onItemUpdated(itemIndex: Int)
}