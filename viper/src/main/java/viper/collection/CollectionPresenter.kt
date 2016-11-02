package viper.collection

import android.content.DialogInterface
import rx.Observable
import viper.presenter.FragmentPresenter

/**
 * A generic presenter which represents a collection of items on the screen.
 * Created by Nick Cipollo on 11/2/16.
 */
abstract class CollectionPresenter<View : CollectionFragment<*>, out ListItem> : FragmentPresenter<View>(), DialogInterface.OnClickListener {
    companion object {
        val COLLECTION_UPDATE = 10001
        val ITEM_UPDATE = 10002
    }

    abstract val count: Int
    abstract fun getListItem(index: Int): ListItem

    open fun onItemAction(actionId: Int, itemIndex: Int) = Unit

    protected fun notifyCollectionUpdated() {
        restartableFirst(COLLECTION_UPDATE,
                { Observable.just(true) },
                { view, noOp -> view?.onCollectionUpdated() })
        start(COLLECTION_UPDATE)
    }

    protected fun notifyItemUpdate(itemIndex: Int) {
        restartableFirst(ITEM_UPDATE,
                { Observable.just(true) },
                { view, noOp -> view?.onItemUpdated(itemIndex) })
        start(ITEM_UPDATE)
    }
}