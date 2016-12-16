package viper.view.fragments

import android.os.Bundle
import rx.Subscription
import viper.presenters.CollectionPresenter
import viper.view.adapters.CollectionAdapter

/**
 * A generic collection fragment which is driven by a collection presenter.
 * Created by Nick Cipollo on 11/2/16.
 */
abstract class CollectionFragment<P : CollectionPresenter<*, *>, A : CollectionAdapter<P>>
    : CollectionView, ViperFragment<P>() {
    private var actionSub: Subscription? = null
    lateinit var adapter: A

    abstract fun createAdapter(): A

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        adapter = createAdapter()
        adapter.presenter = presenter
        actionSub = adapter.actionObserver.subscribe { sendItemAction(it.actionId, it.itemIndex) }
    }

    override fun onDestroy() {
        super.onDestroy()
        actionSub?.unsubscribe()
        actionSub = null
        adapter.presenter = null
    }

    override fun onCollectionUpdated() {
        adapter.onCollectionUpdated()
    }

    override fun onItemUpdated(itemIndex: Int) {
        adapter.onCollectionUpdated()
    }

    protected fun sendItemAction(actionId: Int, itemIndex: Int) {
        (presenter as? CollectionPresenter<*, *>)?.onItemAction(actionId, itemIndex)
    }
}