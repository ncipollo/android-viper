package viper.collection

import viper.presenter.FragmentPresenter
import viper.view.ViperFragment

/**
 * A generic collection fragment is driven by a collection presenter.
 * Created by Nick Cipollo on 11/2/16.
 */
abstract class CollectionFragment<P : FragmentPresenter<*>> : ViperFragment<P>() {
    abstract fun onCollectionUpdated()
    abstract fun onItemUpdated(itemIndex: Int)

    protected fun sendItemAction(actionId: Int, itemIndex: Int) {
        (presenter as? CollectionPresenter<*, *>)?.onItemAction(actionId, itemIndex)
    }
}