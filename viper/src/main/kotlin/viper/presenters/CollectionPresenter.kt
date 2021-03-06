package viper.presenters

import rx.Observable
import viper.view.fragments.CollectionView

/**
 * A generic presenter which represents a collection of items on the screen.
 * Created by Nick Cipollo on 11/2/16.
 */
abstract class CollectionPresenter<View : CollectionView, ListItem, Interactors : Any>
    : FragmentPresenter<View, Interactors>() {
    companion object {
        val COLLECTION_UPDATE = 10001
        val ITEM_UPDATE = 10002
        val ACTION_ITEM_SELECTED = 10003
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

    internal fun positionMovedOnScreen(index: Int) {
        onItemMovedOnScreen(getListItem(index), index)
    }

    internal fun positionMovedOffScreen(index: Int) {
        onItemMovedOffScreen(getListItem(index), index)
    }

    open fun onItemMovedOnScreen(item: ListItem, index: Int) = Unit
    open fun onItemMovedOffScreen(item: ListItem, index: Int) = Unit
    fun refresh() = onRefresh()
    open protected fun onRefresh() = Unit
}