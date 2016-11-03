package viper.collection

/**
 * Represents a view which can receive collection updates.
 * Created by Nick Cipollo on 11/3/16.
 */
interface CollectionView {
    fun onCollectionUpdated()
    fun onItemUpdated(itemIndex: Int)
}