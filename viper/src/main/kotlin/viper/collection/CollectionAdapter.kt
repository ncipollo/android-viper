package viper.collection

import rx.Observable

/**
 * Represents an adapter which will be driven by a collection presenter.
 * Created by Nick Cipollo on 11/3/16.
 */
interface CollectionAdapter<P : CollectionPresenter<*,*>> : CollectionView {
    var presenter: P?
    val actionObserver : Observable<AdapterAction>
}

data class AdapterAction(val actionId: Int,val itemIndex: Int)